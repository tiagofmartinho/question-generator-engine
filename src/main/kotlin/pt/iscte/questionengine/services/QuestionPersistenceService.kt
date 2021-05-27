package pt.iscte.questionengine.services

import org.springframework.stereotype.Service
import pt.iscte.paddle.model.IProcedure
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.paddle.model.IVariableDeclaration
import pt.iscte.questionengine.entities.*
import pt.iscte.questionengine.questions.dynamic.DynamicQuestion
import pt.iscte.questionengine.questions.staticz.ProcedureQuestion
import pt.iscte.questionengine.questions.staticz.StaticQuestion
import pt.iscte.questionengine.questions.staticz.VariableQuestion
import pt.iscte.questionengine.repositories.QuestionRepository
import pt.iscte.questionengine.repositories.QuestionTemplateRepository
import pt.iscte.questionengine.services.computation.ProcedureData
import pt.iscte.questionengine.utils.QuestionUtils
import java.security.InvalidParameterException

@Service
class QuestionPersistenceService(private val questionTemplateRepository: QuestionTemplateRepository,
                                 private val questionRepository: QuestionRepository,
                                 private val proficiencyService: ProficiencyService) {

    fun saveQuestions(procedureQuestions: Map<IProcedure, Set<ProcedureQuestion>>,
                      variableQuestions: Map<IVariableDeclaration, Set<VariableQuestion>>,
                      dynamicQuestions: Map<ProcedureData, Set<DynamicQuestion>>,
                      codeSubmission: CodeSubmission, language: Language
    ): Collection<Question> {
        val questions = mutableSetOf<Question>()
        questions.addAll(getStaticQuestionEntities(procedureQuestions, codeSubmission, language))
        questions.addAll(getStaticQuestionEntities(variableQuestions, codeSubmission, language))
        questions.addAll(getDynamicQuestionEntities(dynamicQuestions, codeSubmission, language))
        return questionRepository.saveAll(questions).toSet()
    }

    fun<T : IProgramElement> getStaticQuestionEntities(staticQuestions: Map<T, Set<StaticQuestion<T>>>, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        staticQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveStaticQuestionTemplate(it)
                val question = Question(null, questionTemplate, codeSubmission, language, null, it.question(entry.key), it.answer(entry.key).toString(), getFunctionId(entry.key)
                )
                questions.add(question)
            }
        }
        return questions
    }

    fun getDynamicQuestionEntities(dynamicQuestions: Map<ProcedureData, Set<DynamicQuestion>>, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        dynamicQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveDynamicQuestionTemplate(it)
                val question = Question(null, questionTemplate, codeSubmission, language, null, it.question(entry.key), it.answer(entry.key).toString(), entry.key.procedure.id)
                questions.add(question)
            }
        }
        return questions
    }

    private fun getFunctionId(key: IProgramElement): String {
        return when (key) {
            is IProcedure -> key.id
            is IVariableDeclaration -> key.ownerProcedure.id
            else -> throw InvalidParameterException()
        }
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
