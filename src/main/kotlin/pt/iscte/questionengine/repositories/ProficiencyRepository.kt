package pt.iscte.questionengine.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entities.Proficiency
import pt.iscte.questionengine.entities.ProficiencyLevel

@Repository
interface ProficiencyRepository : CrudRepository<Proficiency, Long> {

    fun findProficiencyByProficiencyLevel(proficiencyLevel: ProficiencyLevel): Proficiency?
}
