package pt.iscte.questionengine.questions.dynamic

import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.services.computation.ElementType
import pt.iscte.questionengine.services.computation.FactType
import pt.iscte.questionengine.services.computation.ProcedureData
import pt.iscte.questionengine.utils.QuestionUtils.Companion.signature

class HowManyFunctionCalls(): DynamicQuestion {

    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
//            "Quantas chamadas de funções são feitas com a invocação <b>${target.procedure.signature(args)}</b>?"
            "How many function calls are made with the following invocation: <b>${target.procedure.signature(args)}</b>?"
        }
        else
//            "Quantas chamadas de funções são feitas ao executar a função <b>${target.procedure.id}</b>?"
            "How many function calls are made from executing function <b>${target.procedure.id}</b>?"
    }

    override fun answer(target: ProcedureData): Int {
        return target.facts.first { it.factType == FactType.METHOD_CALLS }.fact as Int
    }

    override fun applicableTo(target: ProcedureData): Boolean {
        val methodCalls = target.facts.find { it.factType == FactType.METHOD_CALLS }?.fact
        return methodCalls != null && methodCalls as Int > 1
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}

