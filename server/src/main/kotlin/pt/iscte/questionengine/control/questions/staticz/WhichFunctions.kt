package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.ProcedureCallVisitor

class WhichFunctions : StaticQuestion<IProcedure, Collection<String>>() {

//    override fun question(target: IProcedure) = "Which functions does ${target.signature()} depend on?"
    override fun question(target: IProcedure) = "De que funções depende a função ${target.signature()}?"
    override fun applicableTo(target: IProcedure) = CallsOtherFunctions().answer(target)
    override fun answer(target: IProcedure) : Collection<String> {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.map { it.split(' ')[1] }
    }
}
