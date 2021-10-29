package pt.iscte.questionengine.questions.dynamic

import pt.iscte.paddle.interpreter.IReference
import pt.iscte.paddle.model.IArrayType
import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.services.computation.ElementType
import pt.iscte.questionengine.services.computation.FactType
import pt.iscte.questionengine.services.computation.ProcedureData
import pt.iscte.questionengine.utils.QuestionUtils.Companion.signature

class WhatIsTheReturnValue(): DynamicQuestion {

    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
            "Qual é o valor de retorno da invocação <b>${target.procedure.signature(args)}</b>?"
//            "What is the return value from the following invocation: <b>${target.procedure.signature(args)}</b>?"
        } else
            "Qual é o valor de retorno da função <b>${target.procedure.id}</b>?"
//            "What is the return value from executing function <b>${target.procedure.id}</b>?"
    }

    override fun answer(target: ProcedureData): String {
        val fact =  target.facts.first { it.factType == FactType.RETURN_VALUE }.fact
        if (fact is IReference && fact.type is IArrayType) return fact.toString().substring(2)
        return fact.toString()
    }

    override fun applicableTo(target: ProcedureData): Boolean {
        val returnValue = target.facts.find { it.factType == FactType.RETURN_VALUE }?.fact
        val returnValueString = returnValue?.toString()
        return returnValueString != null && returnValueString.isNotBlank() && returnValueString != "null"
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.A
}
