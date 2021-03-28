package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.paddle.model.IVariableAssignment
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyTimesVariableChanged: DynamicQuestion<IProcedure, IProgramState, Int>() {

    override fun proficiencyLevel(): ProficiencyLevel {
        TODO("Not yet implemented")
    }

    override fun question(target: IProcedure, args: Array<Any>): String {
        TODO("Not yet implemented")
    }

    override fun applicableTo(target: IProcedure, answer: Any): Boolean {
        return target.variables.size > 0
    }

    override fun answer(target: IProcedure, state: IProgramState, args: Array<Any>): Int {
        var variableAssignments = 0
        state.addListener(object: IProgramState.IListener {
            override fun step(currentInstruction: IProgramElement) {
                if (currentInstruction is IVariableAssignment) {
                    variableAssignments++
                }
            }
        })
        state.execute(target, *args)
        return variableAssignments
    }
}
