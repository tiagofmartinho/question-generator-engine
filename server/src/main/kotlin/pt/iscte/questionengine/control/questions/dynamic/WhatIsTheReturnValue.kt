package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IType
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.formatArgumentList
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

//TODO doesn't work with chars or Strings as return types
class WhatIsTheReturnValue(): DynamicQuestion<IProcedure, IProgramState, String>() {

    override fun question(target: IProcedure, args: Array<Any>): String {
        val argumentList = formatArgumentList(args)
        return if (argumentList.isNotEmpty()) {
            "Qual é o valor de retorno da função ${target.signature()} " +
                    "com argumentos ${argumentList}?"
        } else "Qual é o valor de retorno da função ${target.signature()}?"
    }

    override fun applicableTo(target: IProcedure, answer: Any): Boolean {
        return target.returnType.isValueType && target.returnType != IType.CHAR
    }

    override fun answer(target: IProcedure, state: IProgramState, args: Array<Any>): String {
        val result = state.execute(target, *args).returnValue
        return result?.toString() ?: ""
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.A

}

//    override fun question(): String {
//        return if (argValues.isNotEmpty()) "What is the return value of $procSignature " +
//                "with arguments ${argValues.contentToString()}?"
//        else "What is the return value of $procSignature?"
//    }
