package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class WhichVariableHoldsReturnTest {

    private val whichVariableHoldsReturn = WhichVariableHoldsReturn()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns variable that hold the return value`() {
        Assertions.assertEquals("s", whichVariableHoldsReturn.answer(module.getProcedure("sum")))
    }

}
