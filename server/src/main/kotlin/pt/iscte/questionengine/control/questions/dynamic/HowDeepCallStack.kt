package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.ICallStack
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.interpreter.IStackFrame
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature

class HowDeepCallStack(): DynamicQuestion<IProcedure, IProgramState, Int>() {

    private lateinit var argValues: Array<Any>
    private lateinit var procSignature: String
    private var callStackDepth = 0

    override fun question(): String {
        return if (argValues.isNotEmpty()) "How deep does the call stack grow from executing $procSignature " +
                "with arguments ${argValues.contentToString()}?"
        else "How deep does the call stack grow from executing $procSignature ?"
    }

     override fun applicableTo(): Boolean {
        return callStackDepth > 1
    }

     override fun answer(): Int {
        return callStackDepth
    }

    override fun loadState(target: IProcedure, state: IProgramState) {
        procSignature = target.signature()
        argValues = QuestionUtils.generateValuesForParams(target.parameters)
        state.callStack.addListener(object : ICallStack.IListener {
            override fun stackFrameCreated(stackFrame: IStackFrame?) {
                if (stackFrame != null) {
                    if (stackFrame.callStack.size > callStackDepth)
                        callStackDepth = stackFrame.callStack.size
                }
            }
        })
        state.execute(target, *argValues)
    }
}
