package pt.iscte.questionengine.controllers

import com.google.googlejavaformat.java.Formatter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.iscte.questionengine.exceptions.DuplicateCodeSubmissionException
import pt.iscte.questionengine.models.AnswerInteraction
import pt.iscte.questionengine.models.CodeSubmissionModel
import pt.iscte.questionengine.models.CodeSubmissionResponse
import pt.iscte.questionengine.services.QuestionEngineService

@CrossOrigin
@RestController
@RequestMapping("questionengine")
class QuestionEngineController(val service: QuestionEngineService) {

    @PostMapping("code")
    fun submitCode(@RequestBody codeSubmissionModel: CodeSubmissionModel): ResponseEntity<CodeSubmissionResponse> {
        try {
            codeSubmissionModel.code = Formatter().formatSource(codeSubmissionModel.code)
        } catch (exception: Exception) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return try {
            ResponseEntity.ok(service.getQuestions(codeSubmissionModel))
        } catch (exception: DuplicateCodeSubmissionException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @PostMapping("answer")
    fun submitAnswer(@RequestBody answerInteraction: AnswerInteraction): ResponseEntity<Map<Long, String>> {
        return ResponseEntity.ok(service.getCorrectAnswers(answerInteraction))
    }

    @GetMapping("wakeup", produces = ["text/plain"])
    fun wakeup() = "I'm awake!"
}
