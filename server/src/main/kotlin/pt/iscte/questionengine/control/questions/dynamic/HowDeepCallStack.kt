package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.ICallStack
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.interpreter.IStackFrame
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowDeepCallStack(): DynamicQuestion<IProcedure, IProgramState, Int>() {

    override fun question(target: IProcedure, args: Array<Any>): String {
        val argumentList = QuestionUtils.formatArgumentList(args)
        return if (argumentList.isNotEmpty()) "Qual o nível de profundidade máximo da pilha de chamadas ao executar a função ${target.signature()} " +
                "com argumentos ${argumentList}?"
        else "Qual o nível de profundidade máximo da pilha de chamadas ao executar a função ${target.signature()}?"
    }

     override fun applicableTo(target: IProcedure, answer: Any): Boolean {
        return answer.toString().toInt() > 1
    }

     override fun answer(target: IProcedure, state: IProgramState, args: Array<Any>): Int {
         var callStackDepth = 0
         state.callStack.addListener(object : ICallStack.IListener {
             override fun stackFrameCreated(stackFrame: IStackFrame?) {
                 if (stackFrame != null) {
                     if (stackFrame.callStack.size > callStackDepth)
                         callStackDepth = stackFrame.callStack.size
                 }
             }
         })
         state.execute(target, *args)
         return callStackDepth
     }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.A
}

//    override fun question(): String {
//        return if (argValues.isNotEmpty()) "How deep does the call stack grow from executing $procSignature " +
//                "with arguments ${argValues.contentToString()}?"
//        else "How deep does the call stack grow from executing $procSignature?"
//    }
