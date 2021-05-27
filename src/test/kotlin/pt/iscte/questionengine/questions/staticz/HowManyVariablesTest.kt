package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class HowManyVariablesTest {

    private val howManyVariables = HowManyVariables()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns 0 if it has no variables`() {
        Assertions.assertEquals(0, howManyVariables.answer(module.getProcedure("calls1Function")))
    }

    @Test
    fun `returns 1 if it has 1 variable`() {
        Assertions.assertEquals(1, howManyVariables.answer(module.getProcedure("methodWithVariable")))
    }

    @Test
    fun `returns 3 if it has 3 variables`() {
        Assertions.assertEquals(3, howManyVariables.answer(module.getProcedure("sum")))
    }
}
