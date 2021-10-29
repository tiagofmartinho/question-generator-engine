package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.visitors.ProcedureCallVisitor

class CallsOtherFunctions : ProcedureQuestion {

//    override fun question(target: IProcedure) = "Does function <b>${target.id}</b> depend on other functions?"
    override fun question(target: IProcedure) = "A função <b>${target.id}</b> depende de outras funções?"
    override fun applicableTo(target: IProcedure) = !target.isRecursive
    override fun answer(target: IProcedure) : Boolean {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.size > 0
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
