package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class HowManyParams : StaticQuestion<IProcedure, Int>() {
    override fun question(target: IProcedure) = "How many parameters does procedure ${target.signature()} have?"
    override fun applicableTo(target: IProcedure) = this.answer(target) > 0
    override fun answer(target: IProcedure): Int = target.parameters.size
}
