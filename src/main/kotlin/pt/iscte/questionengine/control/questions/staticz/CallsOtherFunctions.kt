package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.ProcedureCallVisitor
import pt.iscte.questionengine.entity.ProficiencyLevel

class CallsOtherFunctions : ProcedureQuestion {

//    override fun question(target: IProcedure) = "Does the function ${target.id} depend on other functions?"
    override fun question(target: IProcedure) = "A função <b>${target.id}</b> depende de outras funções?"
    override fun applicableTo(target: IProcedure) = !target.isRecursive
    override fun answer(target: IProcedure) : Boolean {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.size > 0
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
