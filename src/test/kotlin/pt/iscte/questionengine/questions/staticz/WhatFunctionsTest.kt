package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class WhatFunctionsTest {

    private val whichFunctions = WhatFunctions()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns function set with 1 element when depends on 1 function`() {
        Assertions.assertEquals(setOf("sum"), whichFunctions.answer(module.getProcedure("calls1Function")))
    }

    @Test
    fun `returns function set with 2 elements when depends on 2 functions`() {
        Assertions.assertEquals(setOf("sum", "fact"), whichFunctions.answer(module.getProcedure("calls2Functions")))
    }
}
