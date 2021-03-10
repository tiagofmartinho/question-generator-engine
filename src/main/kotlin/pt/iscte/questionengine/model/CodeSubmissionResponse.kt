package pt.iscte.questionengine.model

data class CodeSubmissionResponse(val questions: Collection<QuestionModel>, var userId: Long?) {}
