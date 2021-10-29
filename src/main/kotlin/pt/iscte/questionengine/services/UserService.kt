package pt.iscte.questionengine.services

import org.springframework.stereotype.Service
import pt.iscte.questionengine.entities.AnswerSubmission
import pt.iscte.questionengine.entities.ProficiencyLevel
import pt.iscte.questionengine.entities.SubmissionCode
import pt.iscte.questionengine.entities.User
import pt.iscte.questionengine.exceptions.DuplicateCodeSubmissionException
import pt.iscte.questionengine.exceptions.QuestionEngineException
import pt.iscte.questionengine.models.UserModel
import pt.iscte.questionengine.repositories.AnswerSubmissionRepository
import pt.iscte.questionengine.repositories.UserRepository

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
//        return userRepository.findUserByEmail(userModel.email)
        val student = userRepository.findUserByStudentNumber(userModel.studentNumber)
            ?: return userRepository.save(
                User(userModel.firstName, userModel.lastName, userModel.email, userModel.studentNumber, proficiencyService.getProficiency(
                    ProficiencyLevel.C))
            )
        if (!student.codeSubmissions.isNullOrEmpty() && student.codeSubmissions!!.any { codeSubmission -> codeSubmission.submissionCode == SubmissionCode.S11 }) {
            throw DuplicateCodeSubmissionException()
        }

        return student
    }

    private fun getUserCorrectAnswersRatio(userAnswers: Collection<AnswerSubmission>): Float {
        val numberOfCorrectAnswers = userAnswers.stream().filter{ it.answer == it.question.correctAnswer }.count()
        return numberOfCorrectAnswers.toFloat() / userAnswers.count()
    }
}
