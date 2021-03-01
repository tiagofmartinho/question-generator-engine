package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.control.utils.QuestionUtils.Companion.signature
import pt.iscte.questionengine.entity.ProficiencyLevel

//TODO doesn't work with chars or Strings as return types
class WhatIsTheReturnValue(): DynamicQuestion<IProcedure, IProgramState, String>() {

    private lateinit var argValues: Array<Any>
    private lateinit var procSignature: String
    private lateinit var result: String
    private var isApplicableTo = false

//    override fun question(): String {
//        return if (argValues.isNotEmpty()) "What is the return value of $procSignature " +
//                "with arguments ${argValues.contentToString()}?"
//        else "What is the return value of $procSignature?"
//    }

    override fun question(): String {
        return if (argValues.isNotEmpty()) "Qual é o valor de retorno da função $procSignature " +
                "com argumentos ${argValues.contentToString()}?"
        else "Qual é o valor de retorno da função $procSignature?"
    }

    override fun applicableTo(): Boolean {
        return isApplicableTo
    }
    override fun answer(): String {
        return result
    }

    override fun loadState(target: IProcedure, state: IProgramState) {
        procSignature = target.signature()
        argValues = QuestionUtils.generateValuesForParams(target.parameters, state)
        isApplicableTo = target.returnType.isNumber || target.returnType.isBoolean
        if (isApplicableTo) result = state.execute(target, *argValues).returnValue.toString()
    }

    override fun proficiencyLevel(): ProficiencyLevel = ProficiencyLevel.A
}
