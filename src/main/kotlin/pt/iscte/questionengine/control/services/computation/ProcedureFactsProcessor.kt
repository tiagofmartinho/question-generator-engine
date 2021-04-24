package pt.iscte.questionengine.control.services.computation

import pt.iscte.paddle.interpreter.ICallStack
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.interpreter.IStackFrame
import pt.iscte.paddle.interpreter.IValue
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.paddle.model.IVariableAssignment

class ProcedureFactsProcessor {

    companion object {
        fun processFacts(procedure: IProcedure, state: IProgramState, args: Array<Any>): Collection<ProcedureFact> {
            val facts = mutableSetOf<ProcedureFact>()
            var callStackDepth = 0
            val variableAssignments = mutableMapOf<String, MutableList<Any>>()
            state.callStack.addListener(object : ICallStack.IListener {
                override fun stackFrameCreated(stackFrame: IStackFrame?) {
                    if (stackFrame != null) {
                        if (stackFrame.callStack.size > callStackDepth)
                            callStackDepth = stackFrame.callStack.size
                    }
                }
            })
            state.addListener(object: IProgramState.IListener {
                override fun step(currentInstruction: IProgramElement) {
                    if (currentInstruction is IVariableAssignment) {
                        val ref = state.callStack.topFrame.getVariableStore(currentInstruction.target)
                        val key = currentInstruction.target.id
                        variableAssignments.getOrPut(key, ::mutableListOf) += ref.value as IValue
                    }
                }
            })
            val executionData = state.execute(procedure, *args)
            facts.add(ProcedureFact(FactType.CALL_STACK_DEPTH, callStackDepth))
            facts.add(ProcedureFact(FactType.METHOD_CALLS, executionData.totalProcedureCalls - 1)) //TODO
            facts.add(ProcedureFact(FactType.RETURN_VALUE, executionData.returnValue))
            facts.add(ProcedureFact(FactType.VARIABLE_ASSIGNMENTS, variableAssignments))
            return facts
        }
    }
}
