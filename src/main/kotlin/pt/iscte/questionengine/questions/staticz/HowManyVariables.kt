package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.visitors.VariableDeclarationVisitor

class HowManyVariables : ProcedureQuestion {

//    override fun question(target: IProcedure) = "How many variables (not including parameters) does function <b>${target.id}</b> have?"
    override fun question(target: IProcedure) = "Quantas variáveis (não incluindo parâmetros) tem a função <b>${target.id}</b>?"
    override fun applicableTo(target: IProcedure) = this.answer(target) > 0
    override fun answer(target: IProcedure): Int {
        val v = VariableDeclarationVisitor()
        target.accept(v)
        return v.variables.size
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
