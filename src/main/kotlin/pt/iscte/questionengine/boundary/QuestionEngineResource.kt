package pt.iscte.questionengine.boundary

import com.google.googlejavaformat.java.Formatter
import org.springframework.http.MediaType
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
    fun submitCode(@RequestBody codeSubmissionModel: CodeSubmissionModel): CodeSubmissionResponse {
        try {
            Formatter().formatSource(codeSubmissionModel.code);
        } catch (exception: Exception) {
            throw InvalidCodeException()
        }
        return service.getQuestions(codeSubmissionModel)
    }

    @PostMapping("answer")
    fun submitAnswer(@RequestBody answerInteraction: AnswerInteraction): Map<Long, String> {
        return service.getCorrectAnswers(answerInteraction)
    }

    @GetMapping("wakeup", produces = ["text/plain"])
    fun wakeup() = "I'm awake!"
}
