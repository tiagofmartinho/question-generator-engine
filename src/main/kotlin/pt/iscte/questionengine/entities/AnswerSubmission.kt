package pt.iscte.questionengine.entities

import javax.persistence.*

@Entity
@Table(name = "answer_submission", schema = "question_engine")
data class AnswerSubmission(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @OneToOne @JoinColumn var question: Question,
    @ManyToOne @JoinColumn var user: User,
    var answer: String, var confidenceLevel: Int
) : AuditableEntity() {}
