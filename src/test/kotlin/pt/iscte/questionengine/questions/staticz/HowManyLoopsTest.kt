package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class HowManyLoopsTest {

    private val howManyLoops = HowManyLoops()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns 0 if it has no loops`() {
        Assertions.assertEquals(0, howManyLoops.answer(module.getProcedure("fact")))
    }

    @Test
    fun `returns 1 if it has 1 loop`() {
        Assertions.assertEquals(1, howManyLoops.answer(module.getProcedure("sum")))
    }

}
