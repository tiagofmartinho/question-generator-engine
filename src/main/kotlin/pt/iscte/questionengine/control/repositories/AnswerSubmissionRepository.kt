package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entity.AnswerSubmission
import pt.iscte.questionengine.entity.User

@Repository
interface AnswerSubmissionRepository : CrudRepository<AnswerSubmission, Long> {

    fun findAnswerSubmissionsByUser(user: User): Collection<AnswerSubmission>
}
