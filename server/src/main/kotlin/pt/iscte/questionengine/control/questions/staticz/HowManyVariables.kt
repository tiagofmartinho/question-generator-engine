package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.VariableDeclarationVisitor

class HowManyVariables : StaticQuestion<IProcedure, Int>() {

    override fun question(target: IProcedure) = "How many variables does the function ${target.signature()} use?"
    override fun applicableTo(target: IProcedure) = true
    override fun answer(target: IProcedure): Int {
        val v = VariableDeclarationVisitor()
        target.accept(v)
        return v.variables.size + target.parameters.size
    }
}