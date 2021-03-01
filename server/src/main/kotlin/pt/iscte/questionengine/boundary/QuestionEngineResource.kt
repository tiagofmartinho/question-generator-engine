package pt.iscte.questionengine.boundary

import com.google.googlejavaformat.java.Formatter
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.iscte.questionengine.control.services.QuestionEngineService
import pt.iscte.questionengine.exceptions.InvalidCodeException
import pt.iscte.questionengine.model.AnswerInteraction
import pt.iscte.questionengine.model.CodeSubmissionModel
import pt.iscte.questionengine.model.CodeSubmissionResponse

@CrossOrigin
@RestController
@RequestMapping("submit")
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
}
