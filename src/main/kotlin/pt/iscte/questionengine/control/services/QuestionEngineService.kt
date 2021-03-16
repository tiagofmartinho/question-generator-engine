package pt.iscte.questionengine.control.services

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.iscte.paddle.interpreter.IMachine
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.repositories.*
import pt.iscte.questionengine.control.utils.PaddleUtils
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.entity.*
import pt.iscte.questionengine.model.*
import kotlin.streams.toList

@Service
class QuestionEngineService(private val userService: UserService,
                            private val codeSubmissionRepository: CodeSubmissionRepository,
                            private val questionTemplateRepository: QuestionTemplateRepository,
                            private val questionRepository: QuestionRepository,
                            private val answerSubmissionRepository: AnswerSubmissionRepository,
                            private val languageService: LanguageService,
                            private val proficiencyService: ProficiencyService) {

    private val logger = KotlinLogging.logger {}
    val staticQuestions = QuestionUtils.getStaticQuestions()
    val dynamicQuestions = QuestionUtils.getDynamicQuestions()

    @Transactional
    fun getQuestions(codeSubmissionModel: CodeSubmissionModel): CodeSubmissionResponse {
        val user = userService.getUser(codeSubmissionModel.user)
        val language = languageService.getLanguage(enumValueOf(codeSubmissionModel.languageCode.toUpperCase()))
        val codeSubmission = saveCodeSubmission(codeSubmissionModel.code, user)
        val questions = generateQuestions(codeSubmission, language)
        val questionModels = questions.stream().map { QuestionModel(it.id, it.question, it.questionTemplate.returnType) }.toList()
        return CodeSubmissionResponse(questionModels, codeSubmission.content, user.id)
    }

    @Transactional
    fun getCorrectAnswers(answerInteraction: AnswerInteraction): Map<Long, String> {
        val questions = questionRepository.findAllById(answerInteraction.questionsAnswers.stream().map { it.question.questionId }.toList())
        val user = userService.getUser(answerInteraction.userId)
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
        userService.updateUserProficiency(user)
        return questionCorrectAnswerMap
    }

    private fun generateQuestions(codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val module = PaddleUtils.loadCode(codeSubmission.content)
        val vMachine = IMachine.create(module)
        val questions = mutableSetOf<Question>()
        for(procedure in module.procedures) {
            logger.debug { "generating static questions for procedure: $procedure" }
            questions.addAll(generateStaticQuestions(procedure, codeSubmission, language))
            logger.debug { "generating dynamic questions for procedure: $procedure" }
            questions.addAll(generateDynamicQuestions(procedure, vMachine, codeSubmission, language))
        }
        logger.debug { "got all questions!" }
        return questions
    }

    private fun generateStaticQuestions(procedure: IProcedure, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
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
                            proficiencyService.getProficiency(it.proficiencyLevel()), returnType.toUpperCase()))
                }
                val question = questionRepository.save(Question(null, questionTemplate, codeSubmission, language,null,
                    it.question(procedure), it.answer(procedure).toString()))
                questions.add(question)
            }
        return questions
    }

    private fun generateDynamicQuestions(procedure: IProcedure, state: IProgramState, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        dynamicQuestions.forEach {
            val args = QuestionUtils.generateValuesForParams(procedure.parameters, state)
            val answer = it.answer(procedure, state, args)
            if (it.applicableTo(procedure, answer)) {
                var questionTemplate = questionTemplateRepository
                    .findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) {
                    val returnType = QuestionUtils.getReturnTypeOfAnswer(it::class)
                    questionTemplate = questionTemplateRepository
                        .save(QuestionTemplate(null, it::class::simpleName.get().toString(), QuestionType.DYNAMIC, null,
                            proficiencyService.getProficiency(it.proficiencyLevel()), returnType.toUpperCase()))
                }
                val question = questionRepository.save(Question(null, questionTemplate, codeSubmission, language, null,
                    it.question(procedure, args), answer.toString()))
                questions.add(question)
            }
        }
        return questions
    }

    private fun saveCodeSubmission(code: String, user: User): CodeSubmission {
        return codeSubmissionRepository.save(CodeSubmission(null, user, code, null))
    }
}
