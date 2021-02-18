package pt.iscte.questionengine.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "code_submission", schema = "questionengine")
class CodeSubmission(@Id @GeneratedValue var id: Long? = null,
                     @ManyToOne @JoinColumn var user: User, var content: String,
                     @OneToMany(mappedBy = "codeSubmission") var questions: MutableCollection<Question>?)
{}