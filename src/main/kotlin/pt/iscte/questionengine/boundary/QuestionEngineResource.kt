package pt.iscte.questionengine.boundary

import com.google.googlejavaformat.java.Formatter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.iscte.questionengine.control.services.QuestionEngineService
import pt.iscte.questionengine.exceptions.InvalidCodeException
import pt.iscte.questionengine.model.AnswerInteraction
import pt.iscte.questionengine.model.CodeSubmissionModel
import pt.iscte.questionengine.model.CodeSubmissionResponse

@CrossOrigin
@RestController
@RequestMapping("questionengine")
class QuestionEngineResource(val service: QuestionEngineService) {

    @PostMapping("code")
    fun submitCode(@RequestBody codeSubmissionModel: CodeSubmissionModel): ResponseEntity<CodeSubmissionResponse> {
        try {
            codeSubmissionModel.code = Formatter().formatSource(codeSubmissionModel.code)
        } catch (exception: Exception) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(service.getQuestions(codeSubmissionModel))
    }

    @PostMapping("answer")
    fun submitAnswer(@RequestBody answerInteraction: AnswerInteraction): ResponseEntity<Map<Long, String>> {
        return ResponseEntity.ok(service.getCorrectAnswers(answerInteraction))
    }

    @GetMapping("wakeup", produces = ["text/plain"])
    fun wakeup() = "I'm awake!"
}
