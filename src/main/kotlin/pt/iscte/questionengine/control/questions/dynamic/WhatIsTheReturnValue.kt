package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.services.computation.ElementType
import pt.iscte.questionengine.control.services.computation.FactType
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

//TODO doesn't work with chars
class WhatIsTheReturnValue(): DynamicQuestion() {

    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
            "Qual é o valor de retorno da função ${target.procedure.signature()} com argumentos ${args}?"
        } else "Qual é o valor de retorno da função ${target.procedure.signature()}?"
    }

    override fun answer(target: ProcedureData): Any {
        return target.facts.first { it.factType == FactType.RETURN_VALUE }.fact.toString()
    }

    override fun applicableTo(target: ProcedureData): Boolean {
        val returnValue = target.facts.find { it.factType == FactType.RETURN_VALUE }?.fact
        return returnValue != null
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.A
}
