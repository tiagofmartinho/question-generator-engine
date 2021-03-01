package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyTimesDoesLoopExecute(): DynamicQuestion<IProcedure, IProgramState, String>() {

    private lateinit var procSignature: String

    override fun question(): String {
        return "How many times does the loop of $procSignature execute?"
    }

    override fun applicableTo(): Boolean {
        TODO("Not yet implemented")
    }

    override fun answer(): String {
        TODO("Not yet implemented")
    }

    override fun loadState(target: IProcedure, state: IProgramState) {
        procSignature = target.signature()
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}
