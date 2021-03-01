package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.questionengine.entity.ProficiencyLevel

/**
    Represents an abstract dynamic question.
    @param TARGET type of element that the question addresses
    @param ANSWER type of answer to the question
    @param STATE virtual machine state to run the code
 **/

abstract class DynamicQuestion<in TARGET: IProcedure, STATE: IProgramState, ANSWER>  {
    abstract fun question(): String
    abstract fun applicableTo(): Boolean
    abstract fun answer(): ANSWER
    abstract fun loadState(target: TARGET, state: STATE)
    abstract fun proficiencyLevel() : ProficiencyLevel
}
