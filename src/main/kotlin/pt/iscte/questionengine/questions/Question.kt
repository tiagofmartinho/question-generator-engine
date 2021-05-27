package pt.iscte.questionengine.questions

import pt.iscte.questionengine.entities.ProficiencyLevel

interface Question<T> {
    fun question(target: T): String
    fun applicableTo(target: T): Boolean
    fun answer(target: T): Any
    fun proficiencyLevel() : ProficiencyLevel
}
