package pt.iscte.questionengine.models

data class QuestionAnswer(val question: QuestionModel, val userAnswer: String, val confidenceLevel: Int) {}
