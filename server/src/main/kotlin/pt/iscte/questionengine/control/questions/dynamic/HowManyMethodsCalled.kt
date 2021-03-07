package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyMethodsCalled(): DynamicQuestion<IProcedure, IProgramState, Int>() {

    override fun question(target: IProcedure, args: Array<Any>): String {
        val argumentList = QuestionUtils.formatArgumentList(args)
        return if (argumentList.isNotEmpty()) "Quantas funções são chamadas ao executar a função ${target.signature()} " +
                "com os argumentos ${argumentList}?"
        else "Quantas funções são chamadas ao executar a função ${target.signature()} ?"
    }

    override fun applicableTo(target: IProcedure, answer: Any): Boolean {
        return answer.toString().toInt() > 0
    }

    //TODO
    override fun answer(target: IProcedure, state: IProgramState, args: Array<Any>): Int {
        return state.execute(target, *args).totalProcedureCalls - 1
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}

//    override fun question(): String {
//        return if (argValues.isNotEmpty()) "How many methods are called from executing $procSignature " +
//                "with arguments ${argValues.contentToString()}?"
//        else "How many methods are called from executing $procSignature ?"
//    }
