package pt.iscte.questionengine.control.utils

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IType
import pt.iscte.paddle.model.IVariableDeclaration
import pt.iscte.questionengine.control.questions.dynamic.DynamicQuestion
import pt.iscte.questionengine.control.questions.dynamic.HowDeepCallStack
import pt.iscte.questionengine.control.questions.dynamic.HowManyMethodsCalled
import pt.iscte.questionengine.control.questions.dynamic.WhatIsTheReturnValue
import pt.iscte.questionengine.control.questions.staticz.*
import pt.iscte.paddle.interpreter.IReference
import pt.iscte.paddle.interpreter.IValue
import pt.iscte.paddle.model.*
import java.lang.UnsupportedOperationException
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.memberFunctions

class QuestionUtils {

    companion object {
        fun IProcedure.signature() = id + "(" + parameters.joinToString (separator = ", ") { it.type.id } + ")"

        //WhichFixedValueVariables(), WhichVariables()
        fun getStaticQuestions(): Set<StaticQuestion<IProcedure, out Any>> {
            return setOf(CallsOtherFunctions(), HowManyFunctions(), HowManyLoops(), HowManyParams(), HowManyVariables(), IsRecursive(),
                WhichFunctions(), WhichParams(), WhichVariableHoldsReturn())
        }

        fun getDynamicQuestions(): Set<DynamicQuestion<IProcedure, IProgramState,out Any>> {
            return setOf(HowDeepCallStack(), HowManyMethodsCalled(), WhatIsTheReturnValue())
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
                        val len = Random.nextInt(0, 10)
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

        fun randomString(length: Int): String {
            val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            return (1..length)
                    .map { Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")
        }
    }
}
