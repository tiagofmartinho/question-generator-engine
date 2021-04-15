package pt.iscte.questionengine.entity

import javax.persistence.*

@Entity
@Table(name = "user", schema = "question_engine")
data class User(var firstName: String, var lastName: String, var email: String,
           @ManyToOne @JoinColumn var proficiency: Proficiency,
           @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
           @OneToMany(mappedBy = "user") var codeSubmissions: MutableCollection<CodeSubmission>? = null,
           @OneToMany(mappedBy = "user") var answerSubmissions: MutableCollection<AnswerSubmission>? = null) : AuditableEntity()
{}
