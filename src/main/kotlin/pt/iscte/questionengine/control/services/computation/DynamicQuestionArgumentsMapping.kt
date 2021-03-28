package pt.iscte.questionengine.control.services.computation

import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.questions.dynamic.DynamicQuestion

class DynamicQuestionArgumentsMapping(val question: DynamicQuestion<IProcedure, IProgramState, Any>,
                                      val args: Array<Any>) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is DynamicQuestionArgumentsMapping) {
            return false;
        }
        return this.question == other.question
    }

    override fun hashCode(): Int {
        return question.hashCode()
    }
}
