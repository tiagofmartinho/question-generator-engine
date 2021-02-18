package pt.iscte.questionengine.control.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pt.iscte.questionengine.entity.QuestionTemplate

interface QuestionTemplateRepository : JpaRepository<QuestionTemplate?, Long> {

    fun findQuestionTemplateByClazz(clazz: String): QuestionTemplate?

}