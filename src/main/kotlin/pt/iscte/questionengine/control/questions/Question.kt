package pt.iscte.questionengine.control.questions

import pt.iscte.questionengine.entity.ProficiencyLevel

interface Question<T> {
    fun question(target: T): String
    fun applicableTo(target: T): Boolean
    fun answer(target: T): Any
    fun proficiencyLevel() : ProficiencyLevel
}
