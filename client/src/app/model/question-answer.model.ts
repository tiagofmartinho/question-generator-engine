export class QuestionAnswer {
    questionId?: number;
    question?: string;
    answer?: string;

    constructor(questionId?: number, question?: string, answer?: string) {
        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
    }
}