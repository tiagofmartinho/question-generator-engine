package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class HowManyParams : StaticQuestion<IProcedure, Int>() {
//    override fun question(target: IProcedure) = "How many parameters does procedure ${target.signature()} have?"
    override fun question(target: IProcedure) = "Quantos parâmetros tem a função ${target.signature()}?"
    override fun applicableTo(target: IProcedure) = this.answer(target) > 0
    //TODO adapt answer to instance methods (-1 param, don't count "this")
    override fun answer(target: IProcedure): Int = target.parameters.size
}
