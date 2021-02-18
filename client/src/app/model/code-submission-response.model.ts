import { Question } from "./question.model";

export class CodeSubmissionResponse {
    formattedCode?: string;
    questions?: Question[];
    userId?: number; //TODO remove
}