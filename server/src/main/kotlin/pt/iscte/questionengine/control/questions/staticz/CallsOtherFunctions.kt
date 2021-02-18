package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.ProcedureCallVisitor

class CallsOtherFunctions : StaticQuestion<IProcedure, Boolean>() {

    override fun question(target: IProcedure) = "Does the function ${target.signature()} depend on other functions?"
    override fun applicableTo(target: IProcedure) = true
    override fun answer(target: IProcedure) : Boolean {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.size > 0
    }
}