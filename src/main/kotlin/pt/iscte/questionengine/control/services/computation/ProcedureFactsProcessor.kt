package pt.iscte.questionengine.control.services.computation

import pt.iscte.paddle.interpreter.ICallStack
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.interpreter.IStackFrame
import pt.iscte.paddle.model.IProcedure

class ProcedureFactsProcessor {

    companion object {

        fun processFacts(procedure: IProcedure, state: IProgramState, args: Array<Any>): Collection<ProcedureFact> {
            val facts = mutableSetOf<ProcedureFact>()
            facts.add(getCallStackFact(procedure, args, state))
            facts.add(getReturnValueFact(procedure, args, state))
            facts.add(getMethodsCalledFact(procedure, args, state))
            return facts
        }

        private fun getCallStackFact(procedure: IProcedure, args: Array<Any>, state: IProgramState): ProcedureFact {
            var callStackDepth = 0
            state.callStack.addListener(object : ICallStack.IListener {
                override fun stackFrameCreated(stackFrame: IStackFrame?) {
                    if (stackFrame != null) {
                        if (stackFrame.callStack.size > callStackDepth)
                            callStackDepth = stackFrame.callStack.size
                    }
                }
            })
            state.execute(procedure, *args)
            return ProcedureFact("callStack", callStackDepth, null)
        }

        //TODO
        private fun getMethodsCalledFact(procedure: IProcedure, args: Array<Any>, state: IProgramState): ProcedureFact {
            val executionData = state.execute(procedure, *args)
            return ProcedureFact("methodsCalled", executionData.totalProcedureCalls - 1, null)
        }

        private fun getReturnValueFact(procedure: IProcedure, args: Array<Any>, state: IProgramState): ProcedureFact {
            val executionData = state.execute(procedure, *args)
            return ProcedureFact("returnValue", executionData.returnValue, null)
        }


    }
}
