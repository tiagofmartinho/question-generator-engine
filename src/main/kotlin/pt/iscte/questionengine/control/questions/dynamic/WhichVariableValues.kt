package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.services.computation.ElementType
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class WhichVariableValues : DynamicQuestion {
    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        val entry = target.elements.first { it.type == ElementType.VARIABLE_ASSIGNMENTS }.element as Map.Entry<*, *>
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
            "Qual a sequência de valores (ordenada) tomada pela variável <b>${entry.key}</b> com a invocação <b>${target.procedure.signature(args)}</b>? " +
                    "Inclui valores repetidos se existirem."
        } else "Qual a sequência de valores (ordenada) tomada pela variável <b>${entry.key}</b> na função <b>${target.procedure.id}</b>? " +
                "Inclui valores repetidos se existirem."
    }

    override fun answer(target: ProcedureData): List<Any> {
        val entry = target.elements.first { it.type == ElementType.VARIABLE_ASSIGNMENTS }.element as Map.Entry<*, *>
        return (entry.value as List<*>).filterIsInstance<Any>()
    }

    override fun applicableTo(target: ProcedureData): Boolean {
        val variableAssignments = target.elements.find { it.type == ElementType.VARIABLE_ASSIGNMENTS }?.element
        if (variableAssignments != null) {
            val valuesSet = ((variableAssignments as Map.Entry<*, *>).value as List<*>).toSet()
            return valuesSet.size > 1
        }
        return false
    }

    override fun proficiencyLevel(): ProficiencyLevel {
        return ProficiencyLevel.B
    }
}
