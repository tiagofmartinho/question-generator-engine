package pt.iscte.questionengine.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "question_template", schema = "questionengine")
class QuestionTemplate(@Id @GeneratedValue var id: Long? = null, @Column(unique=true) var clazz: String,
                       @Enumerated(EnumType.STRING)var type: QuestionType,
                       @OneToMany(mappedBy = "questionTemplate") var questions: MutableCollection<Question>?) {
}