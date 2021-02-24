package pt.iscte.questionengine.control

import org.springframework.stereotype.Service
import pt.iscte.questionengine.control.questions.staticz.*
import pt.iscte.questionengine.control.repositories.UserRepository
import pt.iscte.questionengine.control.utils.PaddleUtils
import pt.iscte.questionengine.entity.User
import pt.iscte.questionengine.model.CodeSubmissionResponse
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.repositories.AnswerSubmissionRepository
import pt.iscte.questionengine.control.repositories.CodeSubmissionRepository
import pt.iscte.questionengine.control.repositories.QuestionRepository
import pt.iscte.questionengine.control.repositories.QuestionTemplateRepository
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.entity.AnswerSubmission
import pt.iscte.questionengine.entity.CodeSubmission
import pt.iscte.questionengine.entity.Question
import pt.iscte.questionengine.entity.QuestionTemplate
import pt.iscte.questionengine.entity.QuestionType
import pt.iscte.questionengine.model.AnswerInteraction
import pt.iscte.questionengine.model.CodeSubmissionModel
import pt.iscte.questionengine.model.QuestionModel
import pt.iscte.questionengine.model.UserModel
import kotlin.streams.toList

@Service
class QuestionEngineService(private val userRepository: UserRepository,
                            private val codeSubmissionRepository: CodeSubmissionRepository,
                            private val questionTemplateRepository: QuestionTemplateRepository,
                            private val questionRepository: QuestionRepository,
                            private val answerSubmissionRepository: AnswerSubmissionRepository) {


    val staticQuestions = setOf(CallsOtherFunctions(), HowManyFunctions(), HowManyLoops(), HowManyParams(), HowManyVariables(), IsRecursive(),
        WhichFixedValueVariables(), WhichFunctions(), WhichParams(), WhichVariableHoldsReturn(), WhichVariables()
    )

    fun getQuestions(codeSubmissionModel: CodeSubmissionModel): CodeSubmissionResponse {
        val user = getUser(codeSubmissionModel.user)
        val codeSubmission = saveCodeSubmission(codeSubmissionModel.code, user)
        val questions = getQuestions(codeSubmission)
        val questionModels = questions.stream().map { QuestionModel(it.id, it.question, it.questionTemplate.returnType) }.toList()
        return CodeSubmissionResponse(questionModels, user.id)
    }

    fun getCorrectAnswers(answerInteraction: AnswerInteraction): Map<Long, String> {
        val questions = questionRepository.findAllById(answerInteraction.questionsAnswers.stream().map { it.question.questionId }.toList())
        val user = userRepository.findById(answerInteraction.userId).get()
        val questionCorrectAnswerMap = mutableMapOf<Long, String>()
        for (qa in answerInteraction.questionsAnswers) {
            for (q in questions) {
                if (qa.question.questionId != null && qa.question.questionId == q.id) {
                    questionCorrectAnswerMap[qa.question.questionId] = q.correctAnswer
                    val savedAnswer = answerSubmissionRepository.save(AnswerSubmission(null, q, user, qa.userAnswer))
                    q.answerSubmission = savedAnswer
                    questionRepository.save(q)
                    break
                }
            }
        }

        return questionCorrectAnswerMap
    }

    private fun getUser(userModel: UserModel): User {
        return userRepository.findUserByEmail(userModel.email)
            ?: return userRepository.save(User(userModel.firstName, userModel.lastName, userModel.email))
    }

    private fun saveCodeSubmission(code: String, user: User): CodeSubmission {
        return codeSubmissionRepository.save(CodeSubmission(null, user, code, null))
    }

    private fun getQuestions(codeSubmission: CodeSubmission): Collection<Question> {
        val module = PaddleUtils.loadCode(codeSubmission.content)
        val questions = mutableSetOf<Question>()
        for(procedure in module.procedures) {
            questions.addAll(generateStaticQuestions(procedure, codeSubmission))
            //TODO dynamic questions
        }
        return questions
    }

    private fun generateStaticQuestions(procedure: IProcedure, codeSubmission: CodeSubmission): Collection<Question> {
        val questions = mutableSetOf<Question>()
        staticQuestions
            .filter { it.applicableTo(procedure) }
            .forEach {
                var questionTemplate = questionTemplateRepository
                    .findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) {
                    val returnType = QuestionUtils.getReturnTypeOfAnswer(it::class)
                    questionTemplate = questionTemplateRepository
                        .save(QuestionTemplate(null, it::class::simpleName.get().toString(), QuestionType.STATIC, null,
                            returnType.toUpperCase()))
                }
                val question = questionRepository.save(Question(null, questionTemplate, codeSubmission, null,
                    it.question(procedure), it.answer(procedure).toString()))
                questions.add(question)
            }
        return questions
    }
}

