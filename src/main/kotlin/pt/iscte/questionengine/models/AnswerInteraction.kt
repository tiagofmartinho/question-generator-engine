package pt.iscte.questionengine.models

data class AnswerInteraction(val userId: Long, val questionsAnswers: Collection<QuestionAnswer>) {}
