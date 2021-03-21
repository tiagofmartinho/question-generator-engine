package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class HowManyVariablesTest {

    private val howManyVariables = HowManyVariables()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun whenProcedureHasNoVariables_thenAnswerIs0() {
        Assertions.assertEquals(0, howManyVariables.answer(module.procedures[3]))
    }

    @Test
    fun whenProcedureHas1Variable_thenAnswerIs1() {
        Assertions.assertEquals(1, howManyVariables.answer(module.procedures[5]))
    }

    @Test
    fun whenProcedureHas2Variables_thenAnswerIs2() {
        Assertions.assertEquals(2, howManyVariables.answer(module.procedures[1]))
    }
}
