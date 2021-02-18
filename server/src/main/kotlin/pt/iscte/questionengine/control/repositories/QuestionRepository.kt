package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.Question

interface QuestionRepository : CrudRepository<Question, Long> {
}