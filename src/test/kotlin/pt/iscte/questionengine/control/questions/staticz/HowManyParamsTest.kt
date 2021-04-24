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
    fun `returns 0 if it has no params`() {
        Assertions.assertEquals(0, howManyParams.answer(module.getProcedure("calls1Function")))
    }

    @Test
    fun `returns 1 if it has 1 param`() {
        Assertions.assertEquals(1, howManyParams.answer(module.getProcedure("fact")))
    }
}
