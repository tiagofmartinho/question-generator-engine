package pt.iscte.questionengine.services

import org.springframework.stereotype.Service
import pt.iscte.questionengine.entities.Language
import pt.iscte.questionengine.entities.LanguageCode
import pt.iscte.questionengine.repositories.LanguageRepository

@Service
class LanguageService(private val languageRepository: LanguageRepository) {

    fun getLanguage(languageCode: LanguageCode): Language {
        return languageRepository.findLanguageByLanguageCode(languageCode)
            ?: return languageRepository.save(Language(null, null, languageCode))
    }
}
