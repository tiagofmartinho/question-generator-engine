package pt.iscte.questionengine.control.services

import mu.KotlinLogging
import org.springframework.stereotype.Service
import pt.iscte.paddle.interpreter.IMachine
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IProcedure
import pt.iscte.questionengine.control.questions.dynamic.DynamicQuestion
import pt.iscte.questionengine.control.questions.staticz.StaticQuestion
import pt.iscte.questionengine.control.services.computation.*
import pt.iscte.questionengine.control.utils.PaddleUtils
import pt.iscte.questionengine.control.utils.QuestionUtils
import pt.iscte.questionengine.entity.*

@Service
class QuestionGeneratorService(private val questionPersistenceService: QuestionPersistenceService) {

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
        questions.addAll(questionPersistenceService.saveStaticQuestions(staticQuestions, codeSubmission, language))
        logger.debug { "saving dynamic questions"}
        questions.addAll(questionPersistenceService.saveDynamicQuestions(dynamicQuestions, codeSubmission, language))
        logger.debug { "generated and saved all questions"}
        return questions
    }

    private fun generateStaticQuestions(procedures: Collection<IProcedure>): Map<IProcedure, Set<StaticQuestion>> {
        val map = mutableMapOf<IProcedure, MutableSet<StaticQuestion>>()
        val applicableQuestions = mutableSetOf<StaticQuestion>()
        procedures.forEach { procedure ->
            map[procedure] = mutableSetOf()
            applicableQuestions.addAll(staticQuestions.filter { it.applicableTo(procedure) })
        }
        applicableQuestions.forEach {
            val applicableProcedure = procedures.filter { procedure -> it.applicableTo(procedure) }.random()
            map[applicableProcedure]!!.add(it)
        }
        return map
    }

    private fun generateDynamicQuestions(procedures: Collection<IProcedure>, state: IProgramState): Map<ProcedureData, MutableSet<DynamicQuestion>> {
        val map = mutableMapOf<ProcedureData, MutableSet<DynamicQuestion>>()
        val applicableQuestions = mutableSetOf<DynamicQuestion>()
        procedures.forEach { procedure ->
            val args = QuestionUtils.generateValuesForParams(procedure.parameters, state)
            val facts = ProcedureFactsProcessor.processFacts(procedure, state, args)
            val elements = getProcedureElements(args, facts)
            val procedureData = ProcedureData(procedure, elements, facts)
            map[procedureData] = mutableSetOf()
            applicableQuestions.addAll(dynamicQuestions.filter { question -> question.applicableTo(procedureData)})
        }
        applicableQuestions.forEach {
            val applicableProcedureData = map.keys.filter { procedureData -> it.applicableTo(procedureData) }.random()
            map[applicableProcedureData]!!.add(it)
        }
        return map
    }

    private fun getProcedureElements(args: Array<Any>, facts: Collection<ProcedureFact>): Collection<ProcedureElement> {
        val argList = QuestionUtils.formatArgumentList(args)
        val elements = argList.map { ProcedureElement(ElementType.PARAMETER, it) }.toMutableList()
        val variableAssignmentsFact = facts.find { it.factType == FactType.VARIABLE_ASSIGNMENTS }
        if (variableAssignmentsFact != null) {
            val allVariableAssignments = variableAssignmentsFact.fact as Map<*, *>
            val variableAssignments = allVariableAssignments.filter { (it.value as List<*>).size > 1  }.entries.randomOrNull()
            if (variableAssignments != null) elements.add(ProcedureElement(ElementType.VARIABLE_ASSIGNMENTS, variableAssignments))
        }
        return elements
    }
}
