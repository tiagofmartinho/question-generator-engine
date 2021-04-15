package pt.iscte.questionengine.model

data class CodeSubmissionResponse(val questions: Collection<QuestionModel>,
                                  val formattedCode: String,
                                  val userId: Long?) {}
