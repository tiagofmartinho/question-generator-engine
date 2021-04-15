package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.*

interface ProficiencyRepository : CrudRepository<Proficiency, Long> {

    fun findProficiencyByProficiencyLevel(proficiencyLevel: ProficiencyLevel): Proficiency?
}
