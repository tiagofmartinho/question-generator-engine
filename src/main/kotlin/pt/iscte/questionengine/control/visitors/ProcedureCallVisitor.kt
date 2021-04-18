package pt.iscte.questionengine.control.visitors

import pt.iscte.paddle.model.IBlock
import pt.iscte.paddle.model.IProcedureCall
import pt.iscte.paddle.model.IProcedureCallExpression

class ProcedureCallVisitor : IBlock.IVisitor {

    var procedureCalls = mutableSetOf<String>()

    override fun visit(p: IProcedureCall): Boolean {
        procedureCalls.add(p.procedure.longSignature())
        return true
    }

    override fun visit(exp: IProcedureCallExpression): Boolean {
        procedureCalls.add(exp.procedure.longSignature())
        return true
    }
}