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
    fun whenProcedureHas1FixedParamAnd1FixedVariable_thenAnswerIs2Variables() {
        Assertions.assertEquals(setOf("a", "x"), whichFixedValueVariables.answer(module.getProcedure("sum")))
    }

    @Test
    fun whenProcedureHas2FixedParams_thenAnswerIs2Variables() {
        Assertions.assertEquals(setOf("a", "n"), whichFixedValueVariables.answer(module.getProcedure("count")))
    }

}
