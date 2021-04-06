package pt.iscte.questionengine.control.utils

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IType
import pt.iscte.paddle.model.IVariableDeclaration
import pt.iscte.questionengine.control.questions.staticz.*
import pt.iscte.paddle.interpreter.IReference
import pt.iscte.paddle.interpreter.IValue
import pt.iscte.paddle.model.*
import pt.iscte.questionengine.control.questions.dynamic.*
import java.lang.UnsupportedOperationException
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.memberFunctions

class QuestionUtils {

    companion object {
        fun IProcedure.signature(): String {
            return id + "(" + getParameterListWithoutThis(parameters).joinToString (separator = ", ") { it.type.id + " " + it.toString() } + ")"
        }

        fun getStaticQuestions(): Set<StaticQuestion> {
            return setOf(CallsOtherFunctions(), HowManyFunctions(), HowManyLoops(), HowManyParams(), HowManyVariables(), IsRecursive(),
                WhichFunctions(), WhichParams(), WhichVariableHoldsReturn(), WhichFixedValueVariables(), WhichVariables())
        }

        fun getDynamicQuestions(): Set<DynamicQuestion> {
            return setOf(HowDeepCallStack(), HowManyMethodCalls(), WhatIsTheReturnValue(), HowManyVariableAssignments(), WhichVariableValues())
        }

        private fun getParameterListWithoutThis(parameters: List<IVariableDeclaration>): List<IVariableDeclaration> {
            val params = parameters.toMutableList()
            if (params.isNotEmpty()) {
                val firstArgument = params[0]
                if (firstArgument.toString() == "this") {
                    params.removeAt(0)
                }
            }
            return params
        }

         fun formatArgumentList(args: Array<Any>): List<Any> {
            val argumentList = args.toMutableList()
            if (args.isNotEmpty()) {
                val firstArgument = args[0]
                if (firstArgument is IReference && firstArgument.type is IRecordType) {
                    argumentList.removeAt(0)
                }
            }
             //format array arguments (remove "->")
             for ((index, element) in argumentList.withIndex())
             {
                 if (element is IReference && element.type is IArrayType) {
                     argumentList[index] = element.toString().substring(2)
                 }
             }
             return argumentList
        }

        private fun generateValueForType(type: IType, state: IProgramState): Any {
            return when (type) {
                is IValueType -> when(type) {
                    IType.INT ->  state.getValue(Random.nextInt(0, 10))
                    IType.BOOLEAN -> state.getValue(Random.nextBoolean())
                    IType.DOUBLE -> state.getValue(Random.nextDouble(0.0, 100.0))
                    IType.CHAR -> state.getValue(('a'..'z').random())
                    else -> throw UnsupportedOperationException()
                }
                else -> return when {
                    type is IReferenceType && type.target is IArrayType -> {
                        val len = Random.nextInt(5, 11)
                        val array = state.allocateArray((type.target as IArrayType).componentType, len )
                        for(i in 0 until array.length)
                            array.setElement(i, generateValueForType((type.target as IArrayType).componentType, state) as IValue)

                        IReference.create(array)
                    }
                    type is IReferenceType && type.target is IRecordType -> {
                        val record = state.allocateRecord(type.target as IRecordType)
                        IReference.create(record)
                    }
                    else -> throw UnsupportedOperationException()
                }
            }
        }

        fun generateValuesForParams(params: List<IVariableDeclaration>, state: IProgramState): Array<Any> {
            val values = mutableListOf<Any>()
            params.forEach { values.add(generateValueForType(it.type, state)) }
            return values.toTypedArray()
        }

        fun getReturnTypeOfAnswer(kClass: KClass<out Any>): String {
            for (func in kClass.memberFunctions) {
                if ("answer" == func.name) {
                    return getSimpleNameOfReturnType(func.returnType)
                }
            }
            return ""
        }

        // KClassifier has the following format: class package.package.[...].className
        private fun getSimpleNameOfReturnType(returnType: KType): String {
            val returnTypeWithPackages = returnType.classifier.toString()
                .split(' ')[1]
                .split('.')
            return returnTypeWithPackages[returnTypeWithPackages.size -1]
        }
    }
}
