package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.questionengine.control.questions.Question
import pt.iscte.questionengine.entity.ProficiencyLevel

/**
    Represents an abstract dynamic question.
    @param TARGET type of element that the question addresses
    @param ANSWER type of answer to the question
    @param STATE virtual machine state to run the code
 **/

abstract class DynamicQuestion<in TARGET: IProcedure, STATE: IProgramState, out ANSWER> : Question  {

    abstract fun question(target: TARGET, args: Array<Any>): String
    abstract fun applicableTo(target: TARGET, answer: Any): Boolean
    abstract fun answer(target: TARGET, state: STATE, args: Array<Any>): ANSWER
}
