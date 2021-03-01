package pt.iscte.questionengine.control.questions.staticz

import pt.iscte.paddle.model.IBlock
import pt.iscte.paddle.model.ILoop
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyLoops : StaticQuestion<IProcedure, Int>() {

//    override fun question(target: IProcedure) = "How many cycles does the function ${target.signature()} have?"
    override fun question(target: IProcedure) = "Quantos ciclos tem a função ${target.signature()}?"
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
