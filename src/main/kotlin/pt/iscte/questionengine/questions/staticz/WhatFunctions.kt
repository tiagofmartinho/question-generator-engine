package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.visitors.ProcedureCallVisitor

class WhatFunctions : ProcedureQuestion {

//   override fun question(target: IProcedure) = "What functions does function <b>${target.id}</b> depend on? " +
//   "Use only the names of the functions to answer the question, for example \"func\"."
     override fun question(target: IProcedure) = "De que funções depende a função <b>${target.id}</b>? " +
         "Usa apenas os nomes das funções para responderes, por exemplo \"func\"."
    override fun applicableTo(target: IProcedure) = CallsOtherFunctions().applicableTo(target) && CallsOtherFunctions().answer(target)
    override fun answer(target: IProcedure) : Set<String> {
        val v = ProcedureCallVisitor()
        target.accept(v)
        return v.procedureCalls
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
