package pt.iscte.questionengine.control.visitors

import pt.iscte.paddle.model.*

class ProcedureCallVisitor : IBlock.IVisitor {

    var procedureCalls = mutableSetOf<IProcedureDeclaration>()

    override fun visit(p: IProcedureCall): Boolean {
        procedureCalls.add(p.procedure)
        return true
    }

}
