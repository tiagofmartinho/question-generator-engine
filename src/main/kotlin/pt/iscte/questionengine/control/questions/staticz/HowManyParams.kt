package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyParams : StaticQuestion() {
//    override fun question(target: IProcedure) = "How many parameters does procedure ${target.signature()} have?"
    override fun question(target: IProcedure) = "Quantos parâmetros tem a função ${target.signature()}?"
    override fun applicableTo(target: IProcedure) = this.answer(target) > 0
    override fun answer(target: IProcedure): Int {
        return target.parameters.filter { it.id != "this" }.size
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
