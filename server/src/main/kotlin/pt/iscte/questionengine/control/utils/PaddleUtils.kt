package pt.iscte.questionengine.control.utils

import pt.iscte.paddle.model.IModule
import pt.iscte.paddle.model.javaparser.Java2Paddle
import java.io.File

class PaddleUtils {

    companion object {
        fun loadCode(path: File): IModule {
            return Java2Paddle(path).parse()
        }

        fun loadCode(code: String): IModule {
            return Java2Paddle("someModuleId", code).parse()
        }

    }
}