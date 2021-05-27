package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel

class IsRecursive : ProcedureQuestion {

    override fun question(target: IProcedure) = "Is function <b>${target.id}</b> recursive?"
//    override fun question(target: IProcedure) = "A função <b>${target.id}</b> é recursiva?"

    //this way it's not always asked and helps students understand that something that has loop isn't necessarily recursive
    // and vice-versa
    override fun applicableTo(target: IProcedure) = HowManyLoops().applicableTo(target) || target.isRecursive
    override fun answer(target: IProcedure): Boolean = target.isRecursive
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
