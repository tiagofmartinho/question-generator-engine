package pt.iscte.questionengine.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entities.AnswerSubmission
import pt.iscte.questionengine.entities.User

@Repository
interface AnswerSubmissionRepository : CrudRepository<AnswerSubmission, Long> {

    fun findAnswerSubmissionsByUser(user: User): Collection<AnswerSubmission>
}
