package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProgramElement
import pt.iscte.questionengine.entity.ProficiencyLevel

/**
    Represents an abstract static question.
    @param TARGET type of element that the question addresses
    @param ANSWER type of answer to the question
 **/

abstract class StaticQuestion<in TARGET: IProgramElement, ANSWER> {

    /*
    question text.
     */
    abstract fun question(target: TARGET): String

    /*
    is the question applicable to the given argument?
     */
    abstract fun applicableTo(target: TARGET): Boolean

    /*
    question answer to the given argument.
    pre-condition: isApplicable(p) = true
     */
    abstract fun answer(target: TARGET): ANSWER

    /*
    The level of proficiency that the question requires from the users in order to be asked to them
     */
    abstract fun proficiencyLevel() : ProficiencyLevel
}
