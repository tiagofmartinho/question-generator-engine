package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class HowManyMethodsCalled: DynamicQuestion<IProcedure, IProgramState, String>() {

    private lateinit var argValues: Array<Any>

    override fun question(target: IProcedure): String {
        return if (argValues.isNotEmpty()) "How many methods are called from executing ${target.signature()} " +
                "with arguments ${argValues.contentToString()}?"
        else "how many methods are called from executing ${target.signature()}?"
    }

    override fun applicableTo(target: IProcedure): Boolean {
        argValues = QuestionUtils.generateValuesForParams(target.parameters)
        return true
    }

    override fun answer(target: IProcedure, state: IProgramState): String {
        val methodsCalled = state.execute(target, *argValues).totalProcedureCalls - 1
        return methodsCalled.toString()
    }
}