package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class WhichFixedValueVariablesTest {

    private val whichFixedValueVariables = WhichFixedValueVariables()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns 2 when it has 1 fixed param and 1 fixed variable`() {
        Assertions.assertEquals(setOf("a", "x"), whichFixedValueVariables.answer(module.getProcedure("sum")))
    }

    @Test
    fun `returns 2 when it has 2 fixed params`() {
        Assertions.assertEquals(setOf("a", "n"), whichFixedValueVariables.answer(module.getProcedure("count")))
    }

}
