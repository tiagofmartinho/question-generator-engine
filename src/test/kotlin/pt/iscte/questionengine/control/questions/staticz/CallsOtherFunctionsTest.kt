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
    fun whenProcedureCallsOtherFunctions_thenAnswerIsTrue() {
        Assertions.assertEquals(true, callsOtherFunctions.answer(module.procedures[3]))
    }

    @Test
    fun whenProcedureCallsOtherFunctions_thenAnswerIsFalse() {
        Assertions.assertEquals(false, callsOtherFunctions.answer(module.procedures[0]))
    }
}
