package pt.iscte.questionengine.control.questions.dynamic

import pt.iscte.questionengine.control.questions.Question
import pt.iscte.questionengine.control.services.computation.ProcedureData

interface DynamicQuestion: Question<ProcedureData>  {
    override fun question(target: ProcedureData): String
    override fun answer(target: ProcedureData): Any
    override fun applicableTo(target: ProcedureData): Boolean
}
