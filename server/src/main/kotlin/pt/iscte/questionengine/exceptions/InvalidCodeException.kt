package pt.iscte.questionengine.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="invalid_code")
class InvalidCodeException: QuestionEngineException() {}
