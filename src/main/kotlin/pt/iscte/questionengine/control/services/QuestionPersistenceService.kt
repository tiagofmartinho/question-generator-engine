package pt.iscte.questionengine.control.services

import org.springframework.stereotype.Service
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.questions.dynamic.DynamicQuestion
import pt.iscte.questionengine.control.questions.staticz.StaticQuestion
import pt.iscte.questionengine.control.repositories.QuestionRepository
import pt.iscte.questionengine.control.repositories.QuestionTemplateRepository
import pt.iscte.questionengine.control.services.computation.ProcedureData
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.entity.*

@Service
class QuestionPersistenceService(private val questionTemplateRepository: QuestionTemplateRepository,
                                 private val questionRepository: QuestionRepository,
                                 private val proficiencyService: ProficiencyService) {

    fun saveStaticQuestions(procedureQuestions: Map<IProcedure, Set<StaticQuestion>>, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        procedureQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveStaticQuestionTemplate(it)
                val question = questionRepository.save(
                    Question(
                    null,
                    questionTemplate,
                    codeSubmission,
                    language,
                    null,
                    it.question(entry.key),
                    it.answer(entry.key).toString()
                )
                )
                questions.add(question)
            }
        }
        return questions
    }

    fun saveDynamicQuestions(procedureQuestions: Map<ProcedureData, Set<DynamicQuestion>>, codeSubmission: CodeSubmission, language: Language): Collection<Question> {
        val questions = mutableSetOf<Question>()
        procedureQuestions.forEach { entry ->
            entry.value.forEach {
                var questionTemplate = questionTemplateRepository.findQuestionTemplateByClazz(it::class::simpleName.get().toString())
                if (questionTemplate == null) questionTemplate = saveDynamicQuestionTemplate(it)
                val question = questionRepository.save(
                    Question(
                    null,
                    questionTemplate,
                    codeSubmission,
                    language,
                    null,
                    it.question(entry.key),
                    it.answer(entry.key).toString()
                )
                )
                questions.add(question)
            }
        }
        return questions
    }

    private fun saveStaticQuestionTemplate(question: StaticQuestion): QuestionTemplate {
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
