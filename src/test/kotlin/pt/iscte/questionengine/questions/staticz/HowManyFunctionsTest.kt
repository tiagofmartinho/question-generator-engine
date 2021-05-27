package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class HowManyFunctionsTest {

    private val howManyFunctions = HowManyFunctions()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)


    @Test
    fun `returns 1 when calls 1 function`() {
        Assertions.assertEquals(1, howManyFunctions.answer(module.getProcedure("calls1Function")))
    }

    @Test
    fun `returns 2 when calls 2 functions`() {
        Assertions.assertEquals(2, howManyFunctions.answer(module.getProcedure("calls2Functions")))
    }
}
