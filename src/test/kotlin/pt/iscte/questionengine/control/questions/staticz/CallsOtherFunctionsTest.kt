package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class CallsOtherFunctionsTest {

    private val callsOtherFunctions = CallsOtherFunctions()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns true if it calls other functions`() {
        Assertions.assertEquals(true, callsOtherFunctions.answer(module.getProcedure("calls1Function")))
    }

    @Test
    fun `function call on expression`() {
        Assertions.assertEquals(true, callsOtherFunctions.answer(module.getProcedure("callsFunctionReturn")))
    }

    @Test
    fun `returns false if it doesn't calls other functions`() {
        Assertions.assertEquals(false, callsOtherFunctions.answer(module.getProcedure("sum")))
    }
}
