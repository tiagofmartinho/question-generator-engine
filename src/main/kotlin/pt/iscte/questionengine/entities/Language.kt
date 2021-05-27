package pt.iscte.questionengine.entities

import javax.persistence.*

@Entity
@Table(name = "language", schema = "question_engine")
data class Language(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
                    @OneToMany(mappedBy = "language") var questions: MutableCollection<Question>?,
                    @Enumerated(EnumType.STRING) var languageCode: LanguageCode
) {}
