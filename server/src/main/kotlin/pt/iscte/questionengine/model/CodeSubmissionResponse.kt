package pt.iscte.questionengine.model

data class CodeSubmissionResponse(val formattedCode: String, val questions: Collection<QuestionModel>, var userId: Long?) {}