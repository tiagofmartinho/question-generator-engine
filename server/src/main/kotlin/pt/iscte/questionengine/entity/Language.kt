package pt.iscte.questionengine.entity

import javax.persistence.*

@Entity
@Table(name = "language", schema = "question_engine")
class Language(@Id @GeneratedValue var id: Long? = null,
               @OneToMany(mappedBy = "language") var questions: MutableCollection<Question>?,
               @Enumerated(EnumType.STRING) var languageCode: LanguageCode)
{}
