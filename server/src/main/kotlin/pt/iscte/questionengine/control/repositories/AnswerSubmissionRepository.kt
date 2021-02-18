package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.AnswerSubmission

interface AnswerSubmissionRepository : CrudRepository<AnswerSubmission, Long> {
}