package questionTypes;

import java.util.ArrayList;

public class DropDownQuestion {

    /**
     * Represents a question in a quiz game. Contains the question text, the number of blanks to fill in,
     * the number of options to choose from, the list of options, and the list of correct answers.
     *
     * @param numberBlanks The number of blanks to fill in for this question.
     * @param numberOptions The number of options to choose from for this question.
     * @param question The text of the question.
     * @param options The list of options to choose from for this question.
     * @param correctAnswers The list of correct answers for this question.
     */
    private final int numberBlanks;
    private final int numberOptions;

    private final String question;

    private ArrayList<String> options;
    private final ArrayList<String> correctAnswers;


    /**
     * Constructs a new DropDownQuestion object with the given question, number of blanks, and number of options.
     *
     * @param question The question to be displayed
     * @param numberBlanks The number of blanks in the question
     * @param numberOptions The number of options available for each blank
     */
    public DropDownQuestion(String question, int numberBlanks, int numberOptions) {
        this.question = question;
        this.numberBlanks=numberBlanks;
        this.numberOptions = numberOptions;
        options=new ArrayList<>(numberOptions);
        correctAnswers=new ArrayList<>(numberBlanks);
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getQuestion() {
        return question;
    }

    public int getNumberBlanks() {
        return numberBlanks;
    }

    public int getNumberOptions() {
        return numberOptions;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void addAnswer(String answer) {
        correctAnswers.add(answer);
    }

    public void addOption(String option) {
        options.add(option);
    }

}
