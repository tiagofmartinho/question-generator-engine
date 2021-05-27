package pt.iscte.questionengine.questions.dynamic

import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.services.computation.ElementType
import pt.iscte.questionengine.services.computation.ProcedureData
import pt.iscte.questionengine.utils.QuestionUtils.Companion.signature

class HowManyVariableAssignments: DynamicQuestion {

    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        val entry = target.elements.first { it.type == ElementType.VARIABLE_ASSIGNMENTS }.element as Map.Entry<*, *>
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
//            "Quantas vezes é atribuído um valor à variável <b>${entry.key}</b> com a invocação <b>${target.procedure.signature(args)}</b>?"
            "How many times a value is assigned to variable <b>${entry.key}</b> from the following invocation: <b>${target.procedure.signature(args)}</b>?"
        }
        else
//            "Quantas vezes é atribuído um valor à variável <b>${entry.key}</b> na função <b>${target.procedure.id}</b>?"
            "How many times a value is assigned to variable <b>${entry.key}</b> from executing function <b>${target.procedure.id}</b>?"
    }

    override fun answer(target: ProcedureData): Int {
        val entry = target.elements.first { it.type == ElementType.VARIABLE_ASSIGNMENTS }.element as Map.Entry<*, *>
        return (entry.value as List<*>).size
    }

    override fun applicableTo(target: ProcedureData): Boolean {
        val variableAssignments = target.elements.find { it.type == ElementType.VARIABLE_ASSIGNMENTS }?.element
        if (variableAssignments != null) {
            val valuesList = (variableAssignments as Map.Entry<*, *>).value as List<*>
            return valuesList.size > 1
        }
        return false
    }

    override fun proficiencyLevel(): ProficiencyLevel {
        return ProficiencyLevel.C
    }

}
