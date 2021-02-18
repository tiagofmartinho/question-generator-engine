package pt.iscte.questionengine.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "user", schema = "questionengine")
class User {

    @Id @GeneratedValue var id: Long? = null
    @OneToMany(mappedBy = "user") var codeSubmissions: MutableCollection<CodeSubmission>? = null
    @OneToMany(mappedBy = "user") var answerSubmissions: MutableCollection<AnswerSubmission>? = null

}