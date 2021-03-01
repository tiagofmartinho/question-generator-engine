package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

class HowManyMethodsCalled(): DynamicQuestion<IProcedure, IProgramState, Int>() {

    private lateinit var argValues: Array<Any>
    private lateinit var procSignature: String
    private var methodsCalled = 0

//    override fun question(): String {
//        return if (argValues.isNotEmpty()) "How many methods are called from executing $procSignature " +
//                "with arguments ${argValues.contentToString()}?"
//        else "How many methods are called from executing $procSignature ?"
//    }

    override fun question(): String {
        return if (argValues.isNotEmpty()) "Quantas funções são chamadas ao executar a função $procSignature " +
                "com os argumentos ${argValues.contentToString()}?"
        else "Quantas funções são chamadas ao executar a função $procSignature ?"
    }

    override fun applicableTo(): Boolean {
        return methodsCalled > 0
    }

    override fun answer(): Int {
        return methodsCalled
    }

    override fun loadState(target: IProcedure, state: IProgramState) {
        procSignature = target.signature()
        argValues = QuestionUtils.generateValuesForParams(target.parameters, state)
        methodsCalled = state.execute(target, *argValues).totalProcedureCalls-1
    }
    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.B
}
