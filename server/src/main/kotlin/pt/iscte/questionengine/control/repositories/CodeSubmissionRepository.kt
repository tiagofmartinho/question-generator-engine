package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.CodeSubmission

interface CodeSubmissionRepository : CrudRepository<CodeSubmission, Long> {
}