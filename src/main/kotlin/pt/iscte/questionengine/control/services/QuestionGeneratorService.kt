package pt.iscte.questionengine.control.services

import mu.KotlinLogging
import org.springframework.stereotype.Service
import pt.iscte.paddle.interpreter.IMachine
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.questions.dynamic.DynamicQuestion
import pt.iscte.questionengine.control.questions.staticz.StaticQuestion
import pt.iscte.questionengine.control.repositories.QuestionRepository
import pt.iscte.questionengine.control.repositories.QuestionTemplateRepository
import pt.iscte.questionengine.control.utils.PaddleUtils
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.entity.*

@Service
class QuestionGeneratorService(private val questionTemplateRepository: QuestionTemplateRepository,
                               private val questionRepository: QuestionRepository,
                               private val proficiencyService: ProficiencyService) {

    private val logger = KotlinLogging.logger {}
    private val staticQuestions = QuestionUtils.getStaticQuestions()
    private val dynamicQuestions = QuestionUtils.getDynamicQuestions()

    fun generateQuestions(codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val module = PaddleUtils.loadCode(codeSubmission.content)
        val vMachine = IMachine.create(module)
        val questions = mutableSetOf<Question>()
        logger.debug { "generating static questions" }
        val staticQuestions = generateStaticQuestions(module.procedures)
        logger.debug { "generating dynamic questions" }
        val dynamicQuestions = generateDynamicQuestions(module.procedures, vMachine)
        logger.debug { "saving static questions"}
        questions.addAll(saveStaticQuestions(staticQuestions, codeSubmission, language))
        logger.debug { "saving dynamic questions"}
        questions.addAll(saveDynamicQuestions(dynamicQuestions, vMachine, codeSubmission, language))
        logger.debug { "generated and saved all questions"}
        return questions
    }

    private fun saveStaticQuestions(procedureQuestions: Map<IProcedure, Set<StaticQuestion<IProcedure, out Any>>>, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        procedureQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveQuestionTemplate(it, QuestionType.STATIC)
                val question = questionRepository.save(Question(
                    null,
                    questionTemplate,
                    codeSubmission,
                    language,
                    null,
                    it.question(entry.key),
                    it.answer(entry.key).toString()
                ))
                questions.add(question)
            }
        }
        return questions
    }

    private fun saveDynamicQuestions(procedureQuestions: Map<IProcedure, Map<DynamicQuestion<IProcedure, IProgramState, out Any>, Array<Any>>>, state: IProgramState, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        procedureQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it.key::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveQuestionTemplate(it.key, QuestionType.DYNAMIC)
                val question = questionRepository.save(Question(
                    null,
                    questionTemplate,
                    codeSubmission,
                    language,
                    null,
                    it.key.question(entry.key, it.value),
                    it.key.answer(entry.key, state, it.value).toString()
                ))
                questions.add(question)
            }
        }
        return questions
    }

    private fun saveQuestionTemplate(question: pt.iscte.questionengine.control.questions.Question, questionType: QuestionType): QuestionTemplate {
        return questionTemplateRepository.save(
            QuestionTemplate(null, question::class::simpleName.get().toString(), questionType, null,
                proficiencyService.getProficiency(question.proficiencyLevel()), QuestionUtils.getReturnTypeOfAnswer(question::class).toUpperCase()))
    }

    private fun generateStaticQuestions(procedures: Collection<IProcedure>): Map<IProcedure, Set<StaticQuestion<IProcedure, out Any>>> {
        val map = mutableMapOf<IProcedure, MutableSet<StaticQuestion<IProcedure, out Any>>>()
        val applicableQuestions = mutableSetOf<StaticQuestion<IProcedure, out Any>>()
        procedures.forEach { procedure ->
            applicableQuestions.addAll(staticQuestions.filter { it.applicableTo(procedure) }.toSet())
            map[procedure] = mutableSetOf()
        }
        for (question in applicableQuestions) {
            var isApplicableToCurrentProcedure = false
            do  {
                val proc = procedures.shuffled()[0]
                if (question.applicableTo(proc)) {
                    map[proc]!!.add(question)
                    isApplicableToCurrentProcedure = true
                }
            } while (!isApplicableToCurrentProcedure)
        }
        return map
    }

    private fun generateDynamicQuestions(procedures: Collection<IProcedure>, state: IProgramState): Map<IProcedure, Map<DynamicQuestion<IProcedure, IProgramState, out Any>, Array<Any>>> {
        val map = mutableMapOf<IProcedure, MutableMap<DynamicQuestion<IProcedure, IProgramState, out Any>, Array<Any>>>()
        val applicableQuestions = mutableMapOf<DynamicQuestion<IProcedure, IProgramState, out Any>, Array<Any>>()
        for (procedure in procedures) {
            for (question in dynamicQuestions) {
                val args = QuestionUtils.generateValuesForParams(procedure.parameters, state)
                val answer = question.answer(procedure, state, args)
                if (question.applicableTo(procedure, answer)) {
                    applicableQuestions[question] = args
                }
            }
            map[procedure] = mutableMapOf()
        }
        for (entry in applicableQuestions) {
            var isApplicableToCurrentProcedure = false
            do  {
                val proc = procedures.shuffled()[0]
                val question = entry.key
                val args = entry.value
                if (proc.parameters.size == args.size) {
                    val answer = question.answer(proc, state, args)
                    if (question.applicableTo(proc, answer)) {
                        map[proc]!![question] = args
                        isApplicableToCurrentProcedure = true
                    }
                }
            } while (!isApplicableToCurrentProcedure)
        }
        return map
    }

}
