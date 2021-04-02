package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class WhichParams : StaticQuestion() {
//    override fun question(target: IProcedure) = "Which are the parameters of procedure ${target.signature()}?"
    override fun question(target: IProcedure) = "Quais são os parâmetros da função ${target.signature()}?"
    override fun applicableTo(target: IProcedure) = HowManyParams().applicableTo(target)
    override fun answer(target: IProcedure): Collection<String> = target.parameters.map { it.id }.toSet()
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
