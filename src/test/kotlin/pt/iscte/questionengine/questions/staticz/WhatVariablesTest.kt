package pt.iscte.questionengine.questions.staticz

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pt.iscte.questionengine.questions.PathConstants
import pt.iscte.questionengine.utils.PaddleUtils
import java.io.File

class WhatVariablesTest {

    private val whichVariables = WhatVariables()
    private val file = File(PathConstants.TEST_DATA_PATH)
    private var module = PaddleUtils.loadCode(file)

    @Test
    fun `returns empty set when it has no variables`() {
        Assertions.assertEquals(emptySet<String>(), whichVariables.answer(module.getProcedure("fact")))
    }

    @Test
    fun `returns set with 3 variables if it has 3 variables`() {
        Assertions.assertEquals(setOf("x", "s", "i"), whichVariables.answer(module.getProcedure("sum")))
    }
}
