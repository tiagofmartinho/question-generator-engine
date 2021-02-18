package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class IsRecursive : StaticQuestion<IProcedure, Boolean>()  {
    override fun question(target: IProcedure) = "Is function ${target.signature()} recursive?"
    override fun applicableTo(target: IProcedure) = true
    override fun answer(target: IProcedure) = target.isRecursive
}