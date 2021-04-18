package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class WhichParamsTest {

    private val whichParams = WhichParams()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun whenProcedureHas1Param_thenAnswerIs1Param() {
        Assertions.assertEquals(setOf("a"), whichParams.answer(module.getProcedure("sum")))
    }

    @Test
    fun whenProcedureHas2Params_thenAnswerIs2Params() {
        Assertions.assertEquals(setOf("a", "n"), whichParams.answer(module.getProcedure("count")))
    }
}
