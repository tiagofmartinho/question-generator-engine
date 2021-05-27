package pt.iscte.questionengine.questions.dynamic

import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.services.computation.ElementType
import pt.iscte.questionengine.services.computation.ProcedureData
import pt.iscte.questionengine.utils.QuestionUtils.Companion.signature

class WhichVariableValues : DynamicQuestion {
    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        val entry = target.elements.first { it.type == ElementType.VARIABLE_ASSIGNMENTS }.element as Map.Entry<*, *>
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
//            "Qual a sequência de valores tomada pela variável <b>${entry.key}</b> com a invocação <b>${target.procedure.signature(args)}</b>? " +
//                    "Inclui valores repetidos se existirem."
            "What is the value sequence taken by variable <b>${entry.key}</b> with the following invocation: <b>${target.procedure.signature(args)}</b>? " +
                    "Include repeated values if they exist."
        } else
//            "Qual a sequência de valores (ordenada) tomada pela variável <b>${entry.key}</b> na função <b>${target.procedure.id}</b>? " +
//                "Inclui valores repetidos se existirem."
            "What is the value sequence taken by variable <b>${entry.key}</b> from executing function <b>${target.procedure.id}</b>? " +
                    "Include repeated values if they exist."
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
