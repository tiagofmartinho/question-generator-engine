package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File


class IsRecursiveTest {

    private val isRecursive = IsRecursive()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns true if it is recursive`() {
        Assertions.assertEquals(true, isRecursive.answer(module.getProcedure("fact")))
    }

    @Test
    fun `returns false if it is not recursive`() {
        Assertions.assertEquals(false, isRecursive.answer(module.getProcedure("sum")))
    }
}
