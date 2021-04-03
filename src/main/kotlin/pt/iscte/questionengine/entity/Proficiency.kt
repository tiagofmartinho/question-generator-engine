package pt.iscte.questionengine.entity

import javax.persistence.*

@Entity
@Table(name = "proficiency", schema = "question_engine")
data class Proficiency(@Enumerated(EnumType.STRING) var proficiencyLevel: ProficiencyLevel,
                  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
                  @OneToMany(mappedBy = "proficiency") var questionTemplates: MutableCollection<QuestionTemplate>?,
                  @OneToMany(mappedBy = "proficiency") var users: MutableCollection<User>?) {}
