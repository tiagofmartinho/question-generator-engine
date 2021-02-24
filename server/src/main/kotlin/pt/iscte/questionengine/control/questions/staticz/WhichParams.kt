package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class WhichParams : StaticQuestion<IProcedure, Collection<String>>() {
    override fun question(target: IProcedure) = "Which are the parameters of procedure ${target.signature()}?"
    override fun applicableTo(target: IProcedure) = target.parameters.size > 0
    override fun answer(target: IProcedure): Collection<String> = target.parameters.map { it.id }.toSet()
}
