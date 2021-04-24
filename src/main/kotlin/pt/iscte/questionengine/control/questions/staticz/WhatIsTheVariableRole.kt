package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IVariableDeclaration
import pt.iscte.paddle.model.roles.IGatherer
import pt.iscte.paddle.model.roles.IStepper
import pt.iscte.paddle.model.roles.IVariableRole
import pt.iscte.questionengine.entity.ProficiencyLevel
import pt.iscte.questionengine.entity.VariableRole
import java.security.InvalidParameterException

class WhatIsTheVariableRole: VariableQuestion {

    override fun proficiencyLevel() = ProficiencyLevel.C
    override fun applicableTo(target: IVariableDeclaration): Boolean {
        val role = IVariableRole.match(target)
        return role is IStepper || role is IGatherer
    }

    override fun question(target: IVariableDeclaration): String {
        return "Qual é o papel mais determinante da variável <b>${target.id}</b> da função <b>${target.ownerProcedure.id}</b>?"
    }

    override fun answer(target: IVariableDeclaration): VariableRole {
        val role = IVariableRole.match(target)
        if (role is IStepper) {
            if (role.direction == IStepper.Direction.INC) return VariableRole.INCREMENTOR
            if (role.direction == IStepper.Direction.DEC) return VariableRole.DECREMENTOR
        }
        if (role is IGatherer) return VariableRole.ACCUMULATOR
        throw InvalidParameterException()
    }

}
