package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.services.computation.ElementType
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class WhichVariableValues : DynamicQuestion() {
    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        val entry = target.elements.first { it.type == ElementType.VARIABLE_ASSIGNMENTS }.element as Map.Entry<*, *>
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
            "Que valores toma a variável ${entry.key} na função ${target.procedure.signature()} " +
                    "com argumentos ${args}? Não coloques valores repetidos caso existam atribuições com o mesmo valor."
        } else "Que valores toma a variável ${entry.key} na função ${target.procedure.signature()}? " +
                "Não coloques valores repetidos caso existam atribuições com o mesmo valor."
    }

    override fun answer(target: ProcedureData): Collection<Any> {
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
