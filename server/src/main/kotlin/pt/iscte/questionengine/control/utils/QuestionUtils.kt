package pt.iscte.questionengine.control.utils

import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IType
import pt.iscte.paddle.model.IVariableDeclaration
import pt.iscte.questionengine.control.questions.staticz.*
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.memberFunctions

class QuestionUtils {

    companion object {
        fun IProcedure.signature() = id + "(" + parameters.joinToString (separator = ", ") { it.type.id } + ")"

        private fun generateValueForType(type: IType): Any {
            when (type) {
                IType.INT -> return Random.nextInt(0, 100)
                IType.BOOLEAN -> return Random.nextBoolean()
                IType.DOUBLE -> return Random.nextDouble(0.0, 100.0)
                IType.CHAR -> return 'a'..'z'.toString().random()
            }
            throw IllegalArgumentException("Requires a type")
        }

        fun generateValuesForParams(params: List<IVariableDeclaration>): Array<Any> {
            val values = mutableListOf<Any>()
            params.forEach { values.add(generateValueForType(it.type)) }
            return values.toTypedArray()
        }

        fun getReturnTypeOfAnswer(kClass: KClass<out StaticQuestion<IProcedure, out Any>>): String {
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
