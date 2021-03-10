package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.VariableDeclarationVisitor
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyVariables : StaticQuestion<IProcedure, Int>() {

//    override fun question(target: IProcedure) = "How many variables does the function ${target.signature()} have?"
    override fun question(target: IProcedure) = "Quantas variáveis tem a função ${target.signature()}?"
    override fun applicableTo(target: IProcedure) = true
    override fun answer(target: IProcedure): Int {
        val v = VariableDeclarationVisitor()
        target.accept(v)
        return v.variables.size + target.parameters.size
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}