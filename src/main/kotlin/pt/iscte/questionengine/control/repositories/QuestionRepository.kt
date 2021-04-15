package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entity.Question

@Repository
interface QuestionRepository : CrudRepository<Question, Long> {}