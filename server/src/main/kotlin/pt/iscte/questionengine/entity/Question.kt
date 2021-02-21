package pt.iscte.questionengine.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "question", schema = "question_engine")
class Question(@Id @GeneratedValue var id: Long? = null,
               @ManyToOne @JoinColumn var questionTemplate: QuestionTemplate,
               @ManyToOne @JoinColumn var codeSubmission: CodeSubmission,
               @OneToOne var answerSubmission: AnswerSubmission?,
               var question: String,
               var correctAnswer: String)
{}