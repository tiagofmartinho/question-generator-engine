package pt.iscte.questionengine.control.visitors

import pt.iscte.paddle.model.*

class ProcedureCallVisitor : IBlock.IVisitor {

    val procedureCalls = mutableSetOf<String>()

    override fun visit(exp: IProcedureCallExpression): Boolean {
        procedureCalls.add(exp.procedure.id)
        return true
    }

}
