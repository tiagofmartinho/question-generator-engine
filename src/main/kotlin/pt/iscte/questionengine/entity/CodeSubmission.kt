package pt.iscte.questionengine.entity

import javax.persistence.*

@Entity
@Table(name = "code_submission", schema = "question_engine")
class CodeSubmission(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
                     @ManyToOne @JoinColumn var user: User, @Column(columnDefinition = "text") var content: String,
                     @OneToMany(mappedBy = "codeSubmission") var questions: MutableCollection<Question>?)
{}
