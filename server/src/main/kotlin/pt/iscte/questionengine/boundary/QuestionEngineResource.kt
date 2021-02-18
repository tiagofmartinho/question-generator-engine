package pt.iscte.questionengine.boundary

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.iscte.questionengine.control.QuestionEngineService
import pt.iscte.questionengine.model.AnswerInteraction
import pt.iscte.questionengine.model.CodeSubmission
import pt.iscte.questionengine.model.CodeSubmissionResponse

@CrossOrigin
@RestController
@RequestMapping("submit")
class QuestionEngineResource(val service: QuestionEngineService) {

    @PostMapping("code")
    fun submitCode(@RequestBody codeSubmission: CodeSubmission): CodeSubmissionResponse {
        return service.generateQuestions(codeSubmission.userCode, codeSubmission.userId)
    }

    @PostMapping("answer")
    fun submitAnswer(@RequestBody answers: AnswerInteraction): Map<Long, String> {
        return service.getCorrectAnswers(answers)
    }


}