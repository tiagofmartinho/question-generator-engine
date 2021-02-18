package pt.iscte.questionengine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuestionengineApplication

fun main(args: Array<String>) {
	runApplication<QuestionengineApplication>(*args)
}