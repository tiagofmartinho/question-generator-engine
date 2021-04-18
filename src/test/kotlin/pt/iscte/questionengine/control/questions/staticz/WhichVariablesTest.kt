package pt.iscte.questionengine.control.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.control.questions.PathConstants
import pt.iscte.questionengine.control.utils.PaddleUtils
import java.io.File

class WhichVariablesTest {

    private val whichVariables = WhichVariables()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun whenProcedureHas0Variables_thenAnswerIsEmptySet() {
        Assertions.assertEquals(emptySet<String>(), whichVariables.answer(module.getProcedure("fact")))
    }

    @Test
    fun whenProcedureHas3Variables_thenAnswerIs3Variables() {
        Assertions.assertEquals(setOf("x", "s", "i"), whichVariables.answer(module.getProcedure("sum")))
    }
}
