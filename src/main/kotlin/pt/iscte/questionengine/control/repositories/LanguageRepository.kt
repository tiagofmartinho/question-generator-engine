package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.Language
import pt.iscte.questionengine.entity.LanguageCode

interface LanguageRepository : CrudRepository<Language, Long> {

    fun findLanguageByLanguageCode(languageCode: LanguageCode): Language?
}
