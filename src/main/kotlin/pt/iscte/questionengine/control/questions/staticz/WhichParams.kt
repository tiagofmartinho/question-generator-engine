package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class WhichParams : ProcedureQuestion {
//    override fun question(target: IProcedure) = "Which are the parameters of procedure ${target.id}?"
    override fun question(target: IProcedure) = "Quais são os parâmetros da função <b>${target.id}</b>?"
    override fun applicableTo(target: IProcedure) = HowManyParams().applicableTo(target)
    override fun answer(target: IProcedure): Set<String> = target.parameters.map { it.id }.toSet()
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
