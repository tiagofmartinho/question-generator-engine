package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.visitors.VariableDeclarationVisitor

class WhatVariables : ProcedureQuestion {

   override fun question(target: IProcedure): String = "What are the variables (not including parameters) of function ${target.id}?"

    // override fun question(target: IProcedure): String = "Quais são as variáveis (não incluindo parâmetros) da função <b>${target.id}</b>?"
    override fun applicableTo(target: IProcedure): Boolean = HowManyVariables().answer(target) > 0
    override fun answer(target: IProcedure): Set<String> {
        val v = VariableDeclarationVisitor()
        target.accept(v)
        return v.variables.filter { it.id != "this" }.map { it.id }.toSet()
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}
