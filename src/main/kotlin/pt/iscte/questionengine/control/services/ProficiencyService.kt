package pt.iscte.questionengine.control.services

import org.springframework.stereotype.Service
import pt.iscte.questionengine.control.repositories.ProficiencyRepository
import pt.iscte.questionengine.entity.Proficiency
import pt.iscte.questionengine.entity.ProficiencyLevel
import pt.iscte.questionengine.exceptions.QuestionEngineException

@Service
class ProficiencyService(private val proficiencyRepository: ProficiencyRepository) {

    fun getUserProficiency(userRatio: Float): Proficiency {
        when (userRatio) {
            in 0.0..0.5 -> return getProficiency(ProficiencyLevel.C)
            in 0.5..0.7 -> return getProficiency(ProficiencyLevel.B)
            in 0.7..1.0 -> return getProficiency(ProficiencyLevel.A)
        }
        throw QuestionEngineException()
    }

    fun getProficiency(proficiencyLevel: ProficiencyLevel): Proficiency {
        return proficiencyRepository.findProficiencyByProficiencyLevel(proficiencyLevel)
            ?: return proficiencyRepository.save(Proficiency(proficiencyLevel, null, null, null))
    }
}
