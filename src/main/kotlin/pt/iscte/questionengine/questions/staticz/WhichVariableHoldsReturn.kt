package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.*
import pt.iscte.questionengine.entities.ProficiencyLevel

class WhichVariableHoldsReturn : ProcedureQuestion {

//   override fun question(target: IProcedure) = "Which variable will hold the return value of function ${target.id}?"
     override fun question(target: IProcedure) = "Que variável terá o valor de retorno da função <b>${target.id}</b>?"

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
