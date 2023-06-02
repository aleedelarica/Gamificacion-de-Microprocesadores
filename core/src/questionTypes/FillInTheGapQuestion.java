package questionTypes;

import java.util.ArrayList;

public class FillInTheGapQuestion {

    /**
     * Represents a question in a fill-in-the-blank quiz game.
     *
     * @param numberBlanks The number of blanks in the question.
     * @param question The question prompt with blanks represented by underscores.
     * @param correctAnswers A list of correct answers to the question.
     */
    private final int numberBlanks;
    private final String question;
    private final ArrayList<String> correctAnswers;

    /**
     * Constructs a FillInTheGapQuestion object with the given question and number of blanks.
     *
     * @param question The question to be asked.
     * @param numberBlanks The number of blanks in the question.
     */
    public FillInTheGapQuestion(String question,int numberBlanks) {
        this.numberBlanks = numberBlanks;
        this.question = question;
        correctAnswers=new ArrayList<>(numberBlanks);
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public void addAnswer(String answer) {
        correctAnswers.add(answer);
    }

    public int getNumberBlanks() {
        return numberBlanks;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }


}
