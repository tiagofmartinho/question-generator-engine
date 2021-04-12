package pt.iscte.questionengine.control.services

import org.springframework.stereotype.Service
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.paddle.model.IVariableDeclaration
import pt.iscte.questionengine.control.questions.dynamic.DynamicQuestion
import pt.iscte.questionengine.control.questions.staticz.ProcedureQuestion
import pt.iscte.questionengine.control.questions.staticz.StaticQuestion
import pt.iscte.questionengine.control.questions.staticz.VariableQuestion
import pt.iscte.questionengine.control.repositories.QuestionRepository
import pt.iscte.questionengine.control.repositories.QuestionTemplateRepository
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.entity.*

@Service
class QuestionPersistenceService(private val questionTemplateRepository: QuestionTemplateRepository,
                                 private val questionRepository: QuestionRepository,
                                 private val proficiencyService: ProficiencyService) {

    fun saveQuestions(procedureQuestions: Map<IProcedure, Set<ProcedureQuestion>>,
                      variableQuestions: Map<IVariableDeclaration, Set<VariableQuestion>>,
                      dynamicQuestions: Map<ProcedureData, Set<DynamicQuestion>>,
                      codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        questions.addAll(getStaticQuestionEntities(procedureQuestions, codeSubmission, language))
        questions.addAll(getStaticQuestionEntities(variableQuestions, codeSubmission, language))
        questions.addAll(getDynamicQuestionEntities(dynamicQuestions, codeSubmission, language))
        return questionRepository.saveAll(questions).toSet()
    }

    fun<T : IProgramElement> getStaticQuestionEntities(procedureQuestions: Map<T, Set<StaticQuestion<T>>>, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        procedureQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveStaticQuestionTemplate(it)
                val question =
                    Question(
                    null,
                    questionTemplate,
                    codeSubmission,
                    language,
                    null,
                    it.question(entry.key),
                    it.answer(entry.key).toString()
                )
                questions.add(question)
            }
        }
        return questions
    }

    fun getDynamicQuestionEntities(procedureQuestions: Map<ProcedureData, Set<DynamicQuestion>>, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        procedureQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveDynamicQuestionTemplate(it)
                val question =
                    Question(
                    null,
                    questionTemplate,
                    codeSubmission,
                    language,
                    null,
                    it.question(entry.key),
                    it.answer(entry.key).toString()
                )
                questions.add(question)
            }
        }
        return questions
    }


    private fun saveStaticQuestionTemplate(question: StaticQuestion<out IProgramElement>): QuestionTemplate {
        return questionTemplateRepository.save(
            QuestionTemplate(null, question::class::simpleName.get().toString(), QuestionType.STATIC, null,
                proficiencyService.getProficiency(question.proficiencyLevel()), QuestionUtils.getReturnTypeOfAnswer(question::class).toUpperCase())
        )
    }

    private fun saveDynamicQuestionTemplate(question: DynamicQuestion): QuestionTemplate {
        return questionTemplateRepository.save(
            QuestionTemplate(null, question::class::simpleName.get().toString(), QuestionType.DYNAMIC, null,
                proficiencyService.getProficiency(question.proficiencyLevel()), QuestionUtils.getReturnTypeOfAnswer(question::class).toUpperCase())
        )
    }

}
