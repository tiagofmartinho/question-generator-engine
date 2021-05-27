package pt.iscte.questionengine.models

data class CodeSubmissionResponse(val questions: Collection<QuestionModel>,
                                  val formattedCode: String,
                                  val userId: Long?) {}
