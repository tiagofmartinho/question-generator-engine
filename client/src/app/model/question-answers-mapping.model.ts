import { Question } from "./question.model";

export class QuestionAnswersMapping {
    questionId?: number;
    question?: string;
    userAnswer?: string;
    correctAnswer?: string;

    constructor(questionId: number, question?: string, userAnswer?: string, correctAnswer?: string) {
        this.questionId = questionId;
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
    }
}