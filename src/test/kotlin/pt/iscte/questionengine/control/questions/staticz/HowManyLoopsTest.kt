package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class HowManyLoopsTest {

    private val howManyLoops = HowManyLoops()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun whenProcedureHasNoLoops_thenAnswerIs0() {
        Assertions.assertEquals(0, howManyLoops.answer(module.procedures[0]))
    }

    @Test
    fun whenProcedureHas1Loop_thenAnswerIs1() {
        Assertions.assertEquals(1, howManyLoops.answer(module.procedures[1]))
    }

}
