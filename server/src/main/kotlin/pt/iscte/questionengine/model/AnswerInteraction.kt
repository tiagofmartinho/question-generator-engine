package pt.iscte.questionengine.model

data class AnswerInteraction(val userId: Long, val questionsAnswers: Collection<QuestionAnswer>) {}
