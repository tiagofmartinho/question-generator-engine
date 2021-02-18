package pt.iscte.questionengine.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "answer_submission", schema = "questionengine")
class AnswerSubmission(@Id @GeneratedValue var id: Long? = null,
                       @OneToOne @JoinColumn var question: Question,
                       @ManyToOne @JoinColumn var user: User, var answer: String) {
}