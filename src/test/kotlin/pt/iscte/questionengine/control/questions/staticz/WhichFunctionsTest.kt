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
    fun `returns function set with 1 element when depends on 1 function`() {
        Assertions.assertEquals(setOf("sum"), whichFunctions.answer(module.getProcedure("calls1Function")))
    }

    @Test
    fun `returns function set with 2 elements when depends on 2 functions`() {
        Assertions.assertEquals(setOf("sum", "fact"), whichFunctions.answer(module.getProcedure("calls2Functions")))
    }
}
