package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.services.computation.ElementType
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyVariableAssignments: DynamicQuestion() {

    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        val entry = target.elements.first { it.type == ElementType.VARIABLE_ASSIGNMENTS }.element as Map.Entry<*, *>
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
            "Quantas vezes é atribuído um valor à variável ${entry.key} na função ${target.procedure.signature()} " +
                    "com argumentos ${args}?"
        }
        else "Quantas vezes é atribuído um valor à variável ${entry.key} na função ${target.procedure.signature()}?"
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
