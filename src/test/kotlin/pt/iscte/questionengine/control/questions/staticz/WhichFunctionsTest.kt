package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class WhichFunctionsTest {

    private val whichFunctions = WhichFunctions()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun whenProcedureDependsOn1Function_thenAnswerIs1Function() {
        Assertions.assertEquals(setOf("sum"), whichFunctions.answer(module.procedures[3]))
    }

    @Test
    fun whenProcedureHas2FixedParams_thenAnswerIs2Variables() {
        Assertions.assertEquals(setOf("sum", "fact"), whichFunctions.answer(module.procedures[4]))
    }
}
