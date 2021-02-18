package pt.iscte.questionengine.control.repositories

import org.springframework.data.repository.CrudRepository
import pt.iscte.questionengine.entity.User

interface UserRepository : CrudRepository<User, Long> {
}