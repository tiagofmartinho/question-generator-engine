package pt.iscte.questionengine.control.utils

import pt.iscte.paddle.model.IModule
import pt.iscte.paddle.model.javaparser.Java2Paddle
import java.io.File

class PaddleUtils {

    companion object {
        fun loadCode(path: File): IModule {
            val java2Paddle = Java2Paddle(path)
            return getModule(java2Paddle)
        }

        fun loadCode(code: String): IModule {
            val java2Paddle = Java2Paddle("someModuleId", code)
            return getModule(java2Paddle)
        }

        private fun getModule(java2Paddle: Java2Paddle): IModule {
            java2Paddle.loadBuiltInProcedures(Math::class.java)
            return java2Paddle.parse()
        }


    }
}
