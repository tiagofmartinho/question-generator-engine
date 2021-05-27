package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class WhatParametersTest {

    private val whichParams = WhatParameters()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns param set with 1 element when it has 1 param`() {
        Assertions.assertEquals(setOf("a"), whichParams.answer(module.getProcedure("sum")))
    }

    @Test
    fun `returns param set with 2 elements when it has 2 params`() {
        Assertions.assertEquals(setOf("a", "n"), whichParams.answer(module.getProcedure("count")))
    }
}
