package pt.iscte.questionengine.control.visitors

import pt.iscte.paddle.model.IBlock
import pt.iscte.paddle.model.IVariableDeclaration

class VariableDeclarationVisitor : IBlock.IVisitor {

    var variables = mutableSetOf<IVariableDeclaration>()

    override fun visit(v: IVariableDeclaration) {
        variables.add(v)
    }
}