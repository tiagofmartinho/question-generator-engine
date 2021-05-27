package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.visitors.ProcedureCallVisitor

class HowManyFunctions : ProcedureQuestion {

    override fun question(target: IProcedure) = "How many functions does function <b>${target.id}</b> depend on?"
//    override fun question(target: IProcedure) = "De quantas funções a função <b>${target.id}</b> depende?"
    override fun applicableTo(target: IProcedure) = CallsOtherFunctions().applicableTo(target) && CallsOtherFunctions().answer(target)
    override fun answer(target: IProcedure) : Int {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.size
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
