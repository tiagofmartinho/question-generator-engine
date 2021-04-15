package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.VariableDeclarationVisitor
import pt.iscte.questionengine.entity.ProficiencyLevel

class WhichVariables : ProcedureQuestion {

//    override fun question(target: IProcedure): String = "Which variables does the function ${target.signature()} use?"

    override fun question(target: IProcedure): String = "Quais são as variáveis (não incluindo parâmetros) " +
            "da função <b>${target.signature()}</b>?"

    override fun applicableTo(target: IProcedure): Boolean = HowManyVariables().answer(target) > 0

    override fun answer(target: IProcedure): Collection<String> {
        val v = VariableDeclarationVisitor()
        target.accept(v)
        return v.variables.filter { it.id != "this" }.map { it.id }.toSet()
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}
