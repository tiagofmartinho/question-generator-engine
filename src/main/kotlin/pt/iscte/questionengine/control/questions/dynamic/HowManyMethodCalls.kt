package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.services.computation.ElementType
import pt.iscte.questionengine.control.services.computation.FactType
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyMethodCalls(): DynamicQuestion() {

    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
            "Quantas chamadas de funções são feitas ao executar a função ${target.procedure.signature()} " +
                    "com os argumentos ${args}?"
        }
        else "Quantas chamadas de funções são feitas ao executar a função ${target.procedure.signature()} ?"
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

