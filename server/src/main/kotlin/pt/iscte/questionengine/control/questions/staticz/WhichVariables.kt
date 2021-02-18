package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.VariableDeclarationVisitor

class WhichVariables : StaticQuestion<IProcedure, Set<String>>() {

    override fun question(target: IProcedure): String = "Which variables does the function ${target.signature()} use?"

    override fun applicableTo(target: IProcedure): Boolean = HowManyVariables().answer(target) > 0

    override fun answer(target: IProcedure): Set<String> {
        val v = VariableDeclarationVisitor()
        target.accept(v)
        val variables = target.parameters.union(v.variables)
        return variables.map { it.id }.toSet()
    }
}