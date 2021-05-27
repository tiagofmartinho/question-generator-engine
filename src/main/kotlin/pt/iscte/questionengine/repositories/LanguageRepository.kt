package pt.iscte.questionengine.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entities.Language
import pt.iscte.questionengine.entities.LanguageCode

@Repository
interface LanguageRepository : CrudRepository<Language, Long> {

    fun findLanguageByLanguageCode(languageCode: LanguageCode): Language?
}
