package pt.iscte.questionengine.control.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.iscte.questionengine.control.repositories.AnswerSubmissionRepository
import pt.iscte.questionengine.control.repositories.CodeSubmissionRepository
import pt.iscte.questionengine.control.repositories.QuestionRepository
import pt.iscte.questionengine.entity.AnswerSubmission
import pt.iscte.questionengine.entity.CodeSubmission
import pt.iscte.questionengine.entity.User
import pt.iscte.questionengine.model.AnswerInteraction
import pt.iscte.questionengine.model.CodeSubmissionModel
import pt.iscte.questionengine.model.CodeSubmissionResponse
import pt.iscte.questionengine.model.QuestionModel
import kotlin.streams.toList

@Service
class QuestionEngineService(
    private val userService: UserService,
    private val codeSubmissionRepository: CodeSubmissionRepository,
    private val questionRepository: QuestionRepository,
    private val answerSubmissionRepository: AnswerSubmissionRepository,
    private val languageService: LanguageService,
    private val questionGeneratorService: QuestionGeneratorService
) {


    @Transactional
    fun getQuestions(codeSubmissionModel: CodeSubmissionModel): CodeSubmissionResponse {
        val user = userService.getUser(codeSubmissionModel.user)
        val language = languageService.getLanguage(enumValueOf(codeSubmissionModel.languageCode.toUpperCase()))
        val codeSubmission = saveCodeSubmission(codeSubmissionModel.code, user)
        val questions = questionGeneratorService.generateQuestions(codeSubmission, language)
        val questionModels =
            questions.stream().map { QuestionModel(it.id, it.question, it.questionTemplate.returnType, it.function) }
                .toList().sortedBy { it.function }
        return CodeSubmissionResponse(questionModels, codeSubmission.content, user.id)
    }

    @Transactional
    fun getCorrectAnswers(answerInteraction: AnswerInteraction): Map<Long, String> {
        val questions =
            questionRepository.findAllById(answerInteraction.questionsAnswers.stream().map { it.question.questionId }
                .toList())
        val user = userService.getUser(answerInteraction.userId)
        val questionCorrectAnswerMap = mutableMapOf<Long, String>()
        for (qa in answerInteraction.questionsAnswers) {
            val question = questions.find { qa.question.questionId == it.id }
            if (question != null) {
                questionCorrectAnswerMap[question.id!!] = question.correctAnswer
                val savedAnswer = answerSubmissionRepository.save(AnswerSubmission(null, question, user, qa.userAnswer, qa.confidenceLevel))
                question.answerSubmission = savedAnswer
                questionRepository.save(question)
            }
        }
        userService.updateUserProficiency(user)
        return questionCorrectAnswerMap
    }

    private fun saveCodeSubmission(code: String, user: User): CodeSubmission {
        return codeSubmissionRepository.save(CodeSubmission(null, user, code, null))
    }

}
