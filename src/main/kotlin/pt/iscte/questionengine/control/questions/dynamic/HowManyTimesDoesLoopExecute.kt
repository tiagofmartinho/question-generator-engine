package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyTimesDoesLoopExecute(): DynamicQuestion<IProcedure, IProgramState, Int>() {


    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
    override fun question(target: IProcedure, args: Array<Any>): String {
        TODO("Not yet implemented")
    }

    override fun applicableTo(target: IProcedure, answer: Any): Boolean {
        TODO("Not yet implemented")
    }

    override fun answer(target: IProcedure, state: IProgramState, args: Array<Any>): Int {
        TODO("Not yet implemented")
    }
}
