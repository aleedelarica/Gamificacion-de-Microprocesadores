package questionTypes;

public class MultipleChoiceQuestion {

    /**
     * Represents a multiple choice question with four options and a correct answer.
     *
     * @param question The question being asked
     * @param optionA The first option for the question
     * @param optionB The second option for the question
     * @param optionC The third option for the question
     * @param optionD The fourth option for the question
     * @param correctAnswer The correct answer to the question
     */
    private final String question;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final String correctAnswer;

    /**
     * Constructs a multiple choice question with the given question and answer options.
     *
     * @param question The question being asked
     * @param optionA The first answer option
     * @param optionB The second answer option
     * @param optionC The third answer option
     * @param optionD The fourth answer option
     * @param correctAnswer The correct answer to the question
     */
    public MultipleChoiceQuestion(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

}
