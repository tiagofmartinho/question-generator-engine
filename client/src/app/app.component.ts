import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AppService } from './app.service';
import { Interaction } from './model/interaction.model';
import { QuestionAnswersMapping } from './model/question-answers-mapping.model';
import { Question } from './model/question.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  interaction = new Interaction([]);
  feedback: string[] = []
  phase = 1;
  loading = false;
  allAnswersCorrect = true;
  code = "";

  constructor(private service: AppService, private toastr: ToastrService){}

  submitCode() {
    this.loading = true;
    return this.service.submitCode(this.code)
    .toPromise().then(
      (data) => {
        this.code = data?.formattedCode;
        this.interaction.userId = data?.userId; //TODO remove
        data?.questions.forEach(q => this.interaction.qas.push(new QuestionAnswersMapping(q.questionId, q.question)))
        this.phase = 2;
        this.loading = false;
      }, (error) => {
        console.log(error);
        this.toastr.error("Your code has errors. Please submit syntatically correct code.");
        this.loading = false;
      })
  }

  submitAnswers() {
    this.loading = true;
    return this.service.submitAnswers(this.interaction.userId, this.interaction.qas)
    .toPromise().then((data) => {
      console.log(data);
      this.mapCorrectAnswersToInteractionModel(data);
      this.showResultsToast();
      this.phase = 3;
      this.loading = false;
    }, (error) => {
      console.log(error);
      this.toastr.error("Please submit non-empty answers to all the questions.")
      this.loading = false;
    })
  }

  private mapCorrectAnswersToInteractionModel(data: Map<number, string>) {
    for (const [questionId, corectAnswer] of Object.entries(data)) {
      this.interaction.qas.forEach(qa => { 
        if (qa.questionId.toString() == questionId) {
          qa.correctAnswer = corectAnswer;
          this.checkAnswer(qa) 
        }
      })
    }
  }

  private checkAnswer(qa: QuestionAnswersMapping) {
    if (qa.correctAnswer == qa.userAnswer) {
      this.feedback.push(
        `For the question \"${qa.question}\" you answered CORRECTLY with \"${qa.userAnswer}\"`        
      )
    } else {
      this.allAnswersCorrect = false;
      this.feedback.push(
        `For the question \"${qa.question}\" you answered INCORRECTLY with: \"${qa.userAnswer}\" and the correct answer is \"${qa.correctAnswer}\"`  
      );
    }
  }

  private showResultsToast() {
    if (this.allAnswersCorrect) {
      this.toastr.success("All questions were answered correctly!")
    } else {
      this.toastr.error("You answered incorrectly to at least one question.")
    }
  }

  cleanup() {
    this.phase = 1;
    this.feedback = []
    this.allAnswersCorrect = true;
    this.code = "";
    this.interaction = new Interaction([]);
  }
}
