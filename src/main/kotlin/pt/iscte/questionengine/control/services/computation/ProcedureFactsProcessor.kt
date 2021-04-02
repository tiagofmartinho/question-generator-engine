package pt.iscte.questionengine.control.services.computation

import pt.iscte.paddle.interpreter.ICallStack
import pt.iscte.paddle.interpreter.IExecutionData
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.interpreter.IStackFrame
import pt.iscte.paddle.model.ILiteral
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.paddle.model.IVariableAssignment

class ProcedureFactsProcessor {

    companion object {
        fun processFacts(procedure: IProcedure, state: IProgramState, args: Array<Any>): Collection<ProcedureFact> {
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
                        val key = currentInstruction.target.toString()
                        variableAssignments.getOrPut(key, ::mutableListOf) += (currentInstruction.expression as ILiteral).stringValue
                    }
                }
            })
            val executionData = state.execute(procedure, *args)
            return getFacts(executionData, variableAssignments, callStackDepth)
        }

        private fun getFacts(executionData: IExecutionData, variableAssignments: Map<String, List<Any>>, callStackDepth: Int) : Collection<ProcedureFact> {
            val facts = mutableSetOf<ProcedureFact>()
            facts.add(ProcedureFact(FactType.CALL_STACK_DEPTH, callStackDepth))
            facts.add(ProcedureFact(FactType.METHOD_CALLS, executionData.totalProcedureCalls - 1)) //TODO
            facts.add(ProcedureFact(FactType.RETURN_VALUE, executionData.returnValue))
            facts.add(ProcedureFact(FactType.VARIABLE_ASSIGNMENTS, variableAssignments))
            return facts
        }
    }
}
