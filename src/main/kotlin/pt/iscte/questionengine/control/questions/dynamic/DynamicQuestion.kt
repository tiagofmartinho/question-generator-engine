package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.questions.Question
import pt.iscte.questionengine.control.services.computation.ProcedureData

abstract class DynamicQuestion: Question<ProcedureData>  {
    abstract override fun question(target: ProcedureData): String
    abstract override fun applicableTo(target: ProcedureData): Boolean
    abstract override fun answer(target: ProcedureData): Any
}
