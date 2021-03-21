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
    fun whenProcedureIsRecursive_thenAnswerIsTrue() {
        Assertions.assertEquals(true, isRecursive.answer(module.procedures[0]))
    }

    @Test
    fun whenProcedureIsNotRecursive_thenAnswerIsFalse() {
        Assertions.assertEquals(false, isRecursive.answer(module.procedures[1]))
    }
}