package pt.iscte.questionengine.entities

import javax.persistence.*

@Entity
@Table(name = "question_template", schema = "question_engine")
data class QuestionTemplate(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null, @Column(unique=true) var clazz: String,
                            @Enumerated(EnumType.STRING) var type: QuestionType,
                            @OneToMany(mappedBy = "questionTemplate") var questions: MutableCollection<Question>?,
                            @ManyToOne @JoinColumn var proficiency: Proficiency,
                            var returnType: String) : AuditableEntity()
{}
