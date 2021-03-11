package ch.zhaw.it.pm2.professor.model;

/**
 * The QuestionGenerator uses this class to save a question and it's result in Strings.
 */
public class Question {
    private String question;
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
