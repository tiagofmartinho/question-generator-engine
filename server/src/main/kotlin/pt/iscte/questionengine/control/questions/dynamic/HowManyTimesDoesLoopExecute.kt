package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class HowManyTimesDoesLoopExecute: DynamicQuestion<IProcedure, IProgramState, String>() {

    override fun question(target: IProcedure): String {
        return "How many times does the loop of ${target.signature()} execute?"
    }

    override fun applicableTo(target: IProcedure): Boolean {
        TODO("Not yet implemented")
    }

    override fun answer(target: IProcedure, state: IProgramState): String {
        TODO("Not yet implemented")
    }
}