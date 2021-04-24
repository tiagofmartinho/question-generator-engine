package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.Proficiency
import pt.iscte.questionengine.entity.ProficiencyLevel

interface ProficiencyRepository : CrudRepository<Proficiency, Long> {

    fun findProficiencyByProficiencyLevel(proficiencyLevel: ProficiencyLevel): Proficiency?
}
