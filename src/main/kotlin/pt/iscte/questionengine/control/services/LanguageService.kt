package pt.iscte.questionengine.control.services

import org.springframework.stereotype.Service
import pt.iscte.questionengine.control.repositories.LanguageRepository
import pt.iscte.questionengine.entity.Language
import pt.iscte.questionengine.entity.LanguageCode

@Service
class LanguageService(private val languageRepository: LanguageRepository) {

    fun getLanguage(languageCode: LanguageCode): Language {
        return languageRepository.findLanguageByLanguageCode(languageCode)
            ?: return languageRepository.save(Language(null, null, languageCode))
    }
}
