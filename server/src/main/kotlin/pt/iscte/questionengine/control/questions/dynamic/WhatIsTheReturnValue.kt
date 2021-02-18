package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature


//TODO doesn't work with chars or Strings as return types
class WhatIsTheReturnValue: DynamicQuestion<IProcedure, IProgramState, String>() {

    private lateinit var argValues: Array<Any>

    override fun question(target: IProcedure): String {
        return if (argValues.isNotEmpty()) "What is the return value of ${target.signature()} " +
                "with arguments ${argValues.contentToString()}?"
        else "What is the return value of ${target.signature()}?"
    }
    override fun applicableTo(target: IProcedure): Boolean {
        argValues = QuestionUtils.generateValuesForParams(target.parameters)
        return target.returnType.isNumber || target.returnType.isBoolean
    }
    override fun answer(target: IProcedure, state: IProgramState): String {
        val result = state.execute(target, *argValues)
        return result.returnValue.toString()
    }
}
