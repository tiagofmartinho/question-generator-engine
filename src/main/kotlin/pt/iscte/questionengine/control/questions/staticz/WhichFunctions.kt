package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.control.visitors.ProcedureCallVisitor
import pt.iscte.questionengine.entity.ProficiencyLevel

/**
 * TODO chamadas não são contadas se ocorrem no return ou num assignment
 */
class WhichFunctions : StaticQuestion() {

//    override fun question(target: IProcedure) = "Which functions does ${target.signature()} depend on?"
    override fun question(target: IProcedure) = "De que funções depende a função ${target.signature()}? " +
        "Usa apenas os nomes das funções para responderes, por exemplo \"func\". Ignora a chamada a outras funções caso estas ocorram no \"return\"."
    override fun applicableTo(target: IProcedure) = CallsOtherFunctions().answer(target)
    override fun answer(target: IProcedure) : Collection<String> {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls.map { it.split(' ')[1].split('(')[0] }.toSet()
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
