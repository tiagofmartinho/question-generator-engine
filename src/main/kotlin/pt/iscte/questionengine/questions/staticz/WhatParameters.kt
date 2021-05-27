package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel

class WhatParameters : ProcedureQuestion {
   override fun question(target: IProcedure) = "What are the parameters of function <b>${target.id}</b>?"
    // override fun question(target: IProcedure) = "Quais são os parâmetros da função <b>${target.id}</b>?"
    override fun applicableTo(target: IProcedure) = HowManyParams().applicableTo(target)
    override fun answer(target: IProcedure): Set<String> = target.parameters.map { it.id }.toSet()
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
