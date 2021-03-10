package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IBlock
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IReturn
import pt.iscte.paddle.model.IVariableDeclaration
import pt.iscte.paddle.model.IVariableExpression
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class WhichVariableHoldsReturn : StaticQuestion<IProcedure, String>() {

//    override fun question(target: IProcedure) = "Which variable holds the return value of function ${target.signature()}?"
    override fun question(target: IProcedure) = "Que variável tem o valor de retorno da função ${target.signature()}?"

    override fun applicableTo(target: IProcedure): Boolean {
        if(target.returnType.isVoid)
            return false
        val v = FindVarsReturn()
        target.accept(v)
        return v.areAllReturnsToSameVar()
    }

    override fun answer(target: IProcedure) : String {
        val v = FindVarsReturn()
        target.accept(v)
        return v.getVar().toString()
    }

    class FindVarsReturn : IBlock.IVisitor {
        private var returns = 0
        private var returnsOfVar = 0
        private var varsOnReturn = mutableListOf<IVariableDeclaration>()

        override fun visit(r: IReturn): Boolean {
            returns++
            if(r.expression is IVariableExpression) {
                varsOnReturn.add((r.expression as IVariableExpression).variable)
                returnsOfVar++
            }
            return true
        }
        fun areAllReturnsToSameVar() = returns == returnsOfVar && varsOnReturn.size == 1
        fun getVar() = varsOnReturn[0]
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}
