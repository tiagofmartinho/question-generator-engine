package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.ProcedureCallVisitor
import pt.iscte.questionengine.entity.ProficiencyLevel

/**
 * TODO chamadas não são contadas se ocorrem no return ou num assignment
 */
class HowManyFunctions : ProcedureQuestion {

//    override fun question(target: IProcedure) = "How many functions does ${target.id} depend on?"
    override fun question(target: IProcedure) = "De quantas funções a função <b>${target.id}</b> depende?"
    override fun applicableTo(target: IProcedure) = CallsOtherFunctions().answer(target)
    override fun answer(target: IProcedure) : Int {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.size
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
