package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class HowDeepCallStack: DynamicQuestion<IProcedure, IProgramState, String>() {
    override fun question(target: IProcedure): String {
        return "How deep does the call stack grow of executing ${target.signature()}?"
    }

    override fun applicableTo(target: IProcedure): Boolean {
        TODO("Not yet implemented")
    }

    override fun answer(target: IProcedure, state: IProgramState): String {
        TODO("Not yet implemented")
    }
}