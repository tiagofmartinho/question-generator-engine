package pt.iscte.questionengine.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.iscte.questionengine.entities.User

@Repository
interface UserRepository : CrudRepository<User, Long> {

    fun findUserByEmail(email: String): User?

    fun findUserByStudentNumber(studentNumber: Int): User?
}
