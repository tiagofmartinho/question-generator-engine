package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProgramElement


/**
    Represents an abstract dynamic question.
    @param TARGET type of element that the question addresses
    @param ANSWER type of answer to the question
 **/

abstract class DynamicQuestion<in TARGET: IProgramElement, STATE: IProgramState, ANSWER>  {
    abstract fun question(target: TARGET): String
    abstract fun applicableTo(target: TARGET): Boolean
    abstract fun answer(target: TARGET, state: STATE): ANSWER
}