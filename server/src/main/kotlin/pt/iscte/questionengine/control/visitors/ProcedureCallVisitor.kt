package pt.iscte.questionengine.control.visitors

import pt.iscte.paddle.model.IBlock
import pt.iscte.paddle.model.IProcedureCall

class ProcedureCallVisitor : IBlock.IVisitor {

    var procedureCalls = mutableSetOf<String>()

    override fun visit(p: IProcedureCall): Boolean {
        procedureCalls.add(p.procedure.longSignature())
        return true
    }
}