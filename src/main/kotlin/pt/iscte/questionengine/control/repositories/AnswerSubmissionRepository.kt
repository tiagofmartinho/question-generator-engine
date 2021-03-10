package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.AnswerSubmission
import pt.iscte.questionengine.entity.User

interface AnswerSubmissionRepository : CrudRepository<AnswerSubmission, Long> {

    fun findAnswerSubmissionsByUser(user: User): Collection<AnswerSubmission>
}
