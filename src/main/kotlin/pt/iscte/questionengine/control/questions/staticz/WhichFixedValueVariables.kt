package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.*
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class WhichFixedValueVariables : ProcedureQuestion {

    // override fun question(target: IProcedure) = "What are the fixed value variables of function ${target.signature()}?"
    override fun question(target: IProcedure) = "Quais são as variáveis de valor fixo da função <b>${target.signature()}</b>?"
    override fun applicableTo(target: IProcedure) = HowManyVariables().answer(target) > 0 && answer(target).isNotEmpty()
    override fun answer(target: IProcedure): Set<String> {
        val v = FindVariables()
        target.accept(v)
        val group = v.assignedVariables.groupingBy { it.target.id }.eachCount().filter { it.value == 1 }.toMutableMap()
        return getFixedValueVariables(group, target.parameters)
    }

    class FindVariables : IBlock.IVisitor {
        var assignedVariables = mutableListOf<IVariableAssignment>()

        override fun visit(v: IVariableAssignment): Boolean {
            assignedVariables.add(v)
            return true
        }
    }

    /**
     * Parameters can be reassigned one or more times so they can't be part of the result in this case
     * If they aren't present in the map it means they are a fixed variable, in which case they have to be added to the result
     */
    private fun getFixedValueVariables(map: Map<String, Int>, parameters: MutableList<IVariableDeclaration>): Set<String> {
        val result = map.keys.toMutableSet()
        for (param in parameters) {
            if (!map.contains(param.id) && param.id != "this") {
                result.add(param.id)
            } else {
                result.remove(param.id)
            }
        }
        return result
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}
