package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class HowManyParamsTest {
    private val howManyParams = HowManyParams()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun whenProcedureHasNoParams_thenAnswerIs0() {
        Assertions.assertEquals(0, howManyParams.answer(module.procedures[3]))
    }

    @Test
    fun whenProcedureHas1Param_thenAnswerIs1() {
        Assertions.assertEquals(1, howManyParams.answer(module.procedures[0]))
    }
}
