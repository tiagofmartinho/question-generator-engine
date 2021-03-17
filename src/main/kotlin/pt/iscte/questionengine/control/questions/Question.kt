package pt.iscte.questionengine.control.questions

import pt.iscte.questionengine.entity.ProficiencyLevel

interface Question {

    /*
        The level of proficiency that the question requires from the users in order to be asked to them
    */
    fun proficiencyLevel() : ProficiencyLevel
}
