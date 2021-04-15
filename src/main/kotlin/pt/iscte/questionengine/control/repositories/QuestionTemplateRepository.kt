package pt.iscte.questionengine.control.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entity.QuestionTemplate

@Repository
interface QuestionTemplateRepository : JpaRepository<QuestionTemplate?, Long> {

    fun findQuestionTemplateByClazz(clazz: String): QuestionTemplate?

}