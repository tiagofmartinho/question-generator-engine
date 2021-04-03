package pt.iscte.questionengine.entity

import javax.persistence.*

@Entity
@Table(name = "question", schema = "question_engine")
data class Question(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
               @ManyToOne @JoinColumn var questionTemplate: QuestionTemplate,
               @ManyToOne @JoinColumn var codeSubmission: CodeSubmission,
               @ManyToOne @JoinColumn var language: Language,
               @OneToOne var answerSubmission: AnswerSubmission?,
               var question: String,
               var correctAnswer: String) : AuditableEntity()
{}
