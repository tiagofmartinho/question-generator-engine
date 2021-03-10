package pt.iscte.questionengine.control.services

import org.springframework.stereotype.Service
import pt.iscte.questionengine.control.repositories.AnswerSubmissionRepository
import pt.iscte.questionengine.control.repositories.UserRepository
import pt.iscte.questionengine.entity.AnswerSubmission
import pt.iscte.questionengine.entity.Proficiency
import pt.iscte.questionengine.entity.ProficiencyLevel
import pt.iscte.questionengine.entity.User
import pt.iscte.questionengine.exceptions.QuestionEngineException
import pt.iscte.questionengine.model.UserModel

@Service
class UserService(private val userRepository: UserRepository,
                  private val answerSubmissionRepository: AnswerSubmissionRepository,
                  private val proficiencyService: ProficiencyService) {

    fun updateUserProficiency(user: User) : User {
        val userAnswers = answerSubmissionRepository.findAnswerSubmissionsByUser(user)
        val userRatio = getUserCorrectAnswersRatio(userAnswers)
        user.proficiency = proficiencyService.getUserProficiency(userRatio)
        return userRepository.save(user)
    }

    fun getUser(userId: Long) : User {
        return userRepository.findById(userId).orElseThrow()
    }

    fun getUser(userModel: UserModel): User {
        return userRepository.findUserByEmail(userModel.email) ?:
        return userRepository.save(
            User(userModel.firstName, userModel.lastName, userModel.email, proficiencyService.getProficiency(
                ProficiencyLevel.C))
        )
    }

    private fun getUserCorrectAnswersRatio(userAnswers: Collection<AnswerSubmission>): Float {
        val numberOfCorrectAnswers = userAnswers.stream().filter{ it.answer == it.question.correctAnswer }.count()
        return numberOfCorrectAnswers.toFloat() / userAnswers.count()
    }
}
