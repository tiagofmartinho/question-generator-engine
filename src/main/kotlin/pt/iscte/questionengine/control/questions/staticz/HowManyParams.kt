package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyParams : ProcedureQuestion {
//    override fun question(target: IProcedure) = "How many parameters does procedure ${target.id} have?"
    override fun question(target: IProcedure) = "Quantos parâmetros tem a função <b>${target.id}</b>?"
    override fun applicableTo(target: IProcedure) = this.answer(target) > 0
    override fun answer(target: IProcedure): Int {
        return target.parameters.filter { it.id != "this" }.size
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
