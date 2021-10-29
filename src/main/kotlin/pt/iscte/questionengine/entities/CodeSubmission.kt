package pt.iscte.questionengine.entities

import javax.persistence.*

@Entity
@Table(name = "code_submission", schema = "question_engine")
data class CodeSubmission(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
                          @ManyToOne @JoinColumn var user: User, @Column(columnDefinition = "text") var content: String,
                          @OneToMany(mappedBy = "codeSubmission") var questions: MutableCollection<Question>?,
                          @Enumerated(EnumType.STRING) var submissionCode: SubmissionCode
) : AuditableEntity()
{}
