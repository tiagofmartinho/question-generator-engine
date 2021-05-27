package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class WhatFixedValueVariablesTest {

    private val whichFixedValueVariables = WhatFixedValueVariables()
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
