package pt.iscte.questionengine.entity

import javax.persistence.*
import kotlin.reflect.KType

@Entity
@Table(name = "question_template", schema = "question_engine")
class QuestionTemplate(@Id @GeneratedValue var id: Long? = null, @Column(unique=true) var clazz: String,
                       @Enumerated(EnumType.STRING) var type: QuestionType,
                       @OneToMany(mappedBy = "questionTemplate") var questions: MutableCollection<Question>?,
                       @ManyToOne @JoinColumn var proficiency: Proficiency,
                       var returnType: String
)
{}
