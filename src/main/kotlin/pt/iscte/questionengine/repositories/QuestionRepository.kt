package pt.iscte.questionengine.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entities.Question

@Repository
interface QuestionRepository : CrudRepository<Question, Long> {}
