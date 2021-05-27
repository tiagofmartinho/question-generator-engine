package pt.iscte.questionengine.visitors

import pt.iscte.paddle.model.IBlock
import pt.iscte.paddle.model.IProcedureCallExpression

class ProcedureCallVisitor : IBlock.IVisitor {

    val procedureCalls = mutableSetOf<String>()

    override fun visit(exp: IProcedureCallExpression): Boolean {
        procedureCalls.add(exp.procedure.id)
        return true
    }

}
