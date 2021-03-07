package pt.iscte.questionengine

import pt.iscte.paddle.interpreter.IMachine
import pt.iscte.paddle.interpreter.IProgramState
import pt.iscte.paddle.model.IBlockElement
import pt.iscte.paddle.model.IProgramElement
import pt.iscte.paddle.model.javaparser.Java2Paddle
import pt.iscte.paddle.model.javaparser.Paddle2Java
import pt.iscte.paddle.model.javaparser.antlr.JavaParser
import pt.iscte.questionengine.control.questions.dynamic.HowDeepCallStack

val testCode = """
    public class Test {
        static int fact(int n) {
            if(n == 1) return 1;
            else return n*(fact(n-1));
        }
        
        static int sum(int[] a) {
            int s = 0;
            int i = 0;
            while(i < a.length) {
                s += a[i];
                i++;
            }
            return s;
        }
        
        static int count(char[] a, char n) {
            int c = 0;
            int i = 0;
            while(i < a.length) {
                if(a[i] == n)
                    c++;
                i++;
            }
            return c;
        }
    }
"""

fun main() {

    val p = Java2Paddle("Test", testCode)
    val m = p.parse()
    println(Paddle2Java().translate(m))

    val state = IMachine.create(m)
    state.addListener(object: IProgramState.IListener {
        override fun step(currentInstruction: IProgramElement?) {
            if(currentInstruction is IBlockElement)
             println(Paddle2Java().translate(currentInstruction))
        }
    })
    val q = HowDeepCallStack()
//    q.loadState(m.procedures[2], state)
//    val ap = q.applicableTo()
//    println(ap)
//    println(q.question() + q.answer())

}
