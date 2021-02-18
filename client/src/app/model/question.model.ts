export class Question {
    questionId?: number;
    question?: string;

    constructor(questionId: number, question: string) {
        this.questionId = questionId;
        this.question = question;
    }
}