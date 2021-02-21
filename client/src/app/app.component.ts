import {Component} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AppService } from './app.service';
import { Interaction } from './model/interaction.model';
import { QuestionAnswersMapping } from './model/question-answers-mapping.model';
import {GoogleLoginProvider, SocialAuthService} from 'angularx-social-login';
import {User} from './model/user.model';
import 'codemirror/mode/clike/clike';
import {Question} from './model/question.model';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  interaction = new Interaction([]);
  feedback: string[] = [];
  phase = 0;
  loading = false;
  allAnswersCorrect = true;
  user: User;
  code: string;

  constructor(private service: AppService, private toastr: ToastrService, private authService: SocialAuthService){}

  login() {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then(user => {
      this.user = new User(user.firstName, user.lastName, user.email);
      console.log(this.user);
      this.phase = 1;
    });
  }

  submitCode() {
    this.loading = true;
    return this.service.submitCode(this.code, this.user)
    .toPromise().then(
      (data) => {
        console.log(data);
        this.user.userId = data?.userId;
        this.interaction.userId = data?.userId;
        this.mapQuestionsToModel(data?.questions)
        this.loading = false;
      }, (error) => {
        this.handleError(error);
        this.loading = false;
      });
  }

  private handleError(error: HttpErrorResponse) {
    console.log(error);
    if (error.status === 400) {
      if (error.error.message === 'invalid_code') {
        this.toastr.error('Your code has errors. Please submit syntactically correct code.');
      }
      else {
        this.toastr.error('Invalid submission. Please fill all necessary inputs.')
      }
    } else if (error.status === 500) {
      this.toastr.error('Server is not available at the moment or there was an error processing your request. Try again later.')
    }
  }

  private mapQuestionsToModel(questions: Question[]) {
    if (questions?.length > 0) {
      questions.forEach(q => this.interaction.qas.push(new QuestionAnswersMapping(new Question(q.questionId, q.question))));
      this.phase = 2;
    } else {
      this.toastr.error('No questions were generated for the code submitted. Add at least one method to your code.')
    }
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
      this.handleError(error);
      this.loading = false;
    });
  }

  private mapCorrectAnswersToInteractionModel(data: Map<number, string>) {
    for (const [questionId, correctAnswer] of Object.entries(data)) {
      this.interaction.qas.forEach(qa => {
        if (qa.question.questionId.toString() === questionId) {
          qa.correctAnswer = correctAnswer;
          this.checkAnswer(qa);
        }
      });
    }
  }

  private checkAnswer(qa: QuestionAnswersMapping) {
    if (qa.correctAnswer === qa.userAnswer) {
      this.feedback.push(
        `For the question \"${qa.question.question}\" you answered CORRECTLY with \"${qa.userAnswer}\"`
      );
    } else {
      this.allAnswersCorrect = false;
      this.feedback.push(
        `For the question \"${qa.question.question}\" you answered INCORRECTLY with: \"${qa.userAnswer}\" and the correct answer is \"${qa.correctAnswer}\"`
      );
    }
  }

  private showResultsToast() {
    if (this.allAnswersCorrect) {
      this.toastr.success('All questions were answered correctly!');
    } else {
      this.toastr.error('You answered incorrectly to at least one question.');
    }
  }

  cleanup() {
    this.phase = 1;
    this.feedback = [];
    this.allAnswersCorrect = true;
    this.code = '';
    this.interaction = new Interaction([]);
  }
}
