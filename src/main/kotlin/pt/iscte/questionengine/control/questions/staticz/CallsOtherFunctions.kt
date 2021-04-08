package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.ProcedureCallVisitor
import pt.iscte.questionengine.entity.ProficiencyLevel

/**
 * TODO chamadas não são contadas se ocorrem no return ou num assignment
 */
class CallsOtherFunctions : StaticQuestion() {

//    override fun question(target: IProcedure) = "Does the function ${target.signature()} depend on other functions?"
    override fun question(target: IProcedure) = "A função <b>${target.signature()}</b> depende de outras funções? " +
        "Ignora a chamada a outras funções caso estas ocorram no \"return\"."
    override fun applicableTo(target: IProcedure) = true
    override fun answer(target: IProcedure) : Boolean {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.size > 0
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
