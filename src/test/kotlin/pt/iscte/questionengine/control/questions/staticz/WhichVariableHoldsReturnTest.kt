package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class WhichVariableHoldsReturnTest {

    private val whichVariableHoldsReturn = WhichVariableHoldsReturn()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun whenProcedureHas1VariableThatHoldsReturn_thenAnswerIs1VariableThatHoldsReturn() {
        Assertions.assertEquals("s", whichVariableHoldsReturn.answer(module.getProcedure("sum")))
    }

}
