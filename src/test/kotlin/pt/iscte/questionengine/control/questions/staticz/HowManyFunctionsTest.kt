package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class HowManyFunctionsTest {

    private val howManyFunctions = HowManyFunctions()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)


    @Test
    fun whenProcedureCalls1Function_thenAnswerIs1() {
        Assertions.assertEquals(1, howManyFunctions.answer(module.getProcedure("calls1Function")))
    }

    @Test
    fun whenProcedureCalls2Functions_thenAnswerIs2() {
        Assertions.assertEquals(2, howManyFunctions.answer(module.getProcedure("calls2Functions")))
    }
}
