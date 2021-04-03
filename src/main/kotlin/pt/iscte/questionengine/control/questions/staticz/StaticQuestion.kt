package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.questions.Question

abstract class StaticQuestion : Question<IProcedure> {
    abstract override fun question(target: IProcedure): String
    abstract override fun applicableTo(target: IProcedure): Boolean
    abstract override fun answer(target: IProcedure): Any
}
