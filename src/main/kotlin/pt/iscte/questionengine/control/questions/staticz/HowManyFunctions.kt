package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.ProcedureCallVisitor
import pt.iscte.questionengine.entity.ProficiencyLevel

/**
 * TODO chamadas não são contadas se ocorrem no return ou num assignment
 */
class HowManyFunctions : StaticQuestion<IProcedure, Int>() {

//    override fun question(target: IProcedure) = "How many functions does ${target.signature()} depend on?"
    override fun question(target: IProcedure) = "De quantas funções a função ${target.signature()} depende?" +
        "Ignora a chamada a outras funções caso estas ocorram no \"return\"."
    override fun applicableTo(target: IProcedure) = CallsOtherFunctions().answer(target)
    override fun answer(target: IProcedure) : Int {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.size
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
