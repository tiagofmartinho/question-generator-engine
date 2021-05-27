package pt.iscte.questionengine.questions.staticz

import pt.iscte.paddle.model.IBlock
import pt.iscte.paddle.model.ILoop
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.entities.ProficiencyLevel

class HowManyLoops : ProcedureQuestion {

    override fun question(target: IProcedure) = "How many loops does function <b>${target.id}</b> have?"
//    override fun question(target: IProcedure) = "Quantos ciclos tem a função <b>${target.id}</b>?"
    override fun applicableTo(target: IProcedure) = this.answer(target) > 0
    override fun answer(target: IProcedure): Int {
        val v = FindLoopCount()
        target.accept(v)
        return v.loops
    }

    class FindLoopCount : IBlock.IVisitor {
        var loops = 0

        override fun visit(l: ILoop?): Boolean {
            loops++
            return true
        }
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.C
}
