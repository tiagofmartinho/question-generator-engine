package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.services.computation.ElementType
import pt.iscte.questionengine.control.services.computation.FactType
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowDeepCallStack(): DynamicQuestion {

    override fun question(target: ProcedureData): String {
        val paramElements = target.elements.filter { it.type == ElementType.PARAMETER }
        return if (paramElements.isNotEmpty()) {
            val args = paramElements.map { it.element }
            "Qual o nível de profundidade máximo da pilha de chamadas com a invocação <b>${target.procedure.signature(args)}</b>?"
        }
        else "Qual o nível de profundidade máximo da pilha de chamadas ao executar a função <b>${target.procedure.id}</b>?"
    }

    override fun answer(target: ProcedureData): Int {
        return target.facts.first { it.factType == FactType.CALL_STACK_DEPTH }.fact as Int
    }

    override fun applicableTo(target: ProcedureData): Boolean {
        val callStackDepth = target.facts.find { it.factType == FactType.CALL_STACK_DEPTH }?.fact
        return callStackDepth != null && callStackDepth as Int > 1
    }

    override fun proficiencyLevel() = ProficiencyLevel.A
}
