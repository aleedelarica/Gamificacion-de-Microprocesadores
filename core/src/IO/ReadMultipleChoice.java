package IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import questionTypes.MultipleChoiceQuestion;

import java.util.ArrayList;

public class ReadMultipleChoice {
    /**
     * Loads a set of multiple choice questions from a JSON file.
     *
     * @param fileName The name of the JSON file to load the questions from.
     * @return An ArrayList of MultipleChoiceQuestion objects.
     */
    public static ArrayList<MultipleChoiceQuestion> loadQuestions(String fileName) {

        //Create empty list of questions
        ArrayList<MultipleChoiceQuestion> questions= new ArrayList<>();

        //Open file as JSON
        FileHandle file = Gdx.files.internal("Questions/"+fileName+".json");
        JsonReader jsonReader = new JsonReader();
        JsonValue baseValue = jsonReader.parse(file);

        // Loop through each JSON object in the array
        for (JsonValue value : baseValue) {
            // Get the question and options
            String question = value.getString("PREGUNTA");

            String answerKey = "OPCION 1";
            String optionA = value.getString(answerKey, "");

            answerKey = "OPCION 2";
            String optionB = value.getString(answerKey, "");

            answerKey = "OPCION 3";
            String optionC = value.getString(answerKey, "");

            answerKey = "OPCION 4";
            String optionD = value.getString(answerKey, "");

            answerKey = "CORRECTA";
            String correctAnswer = value.getString(answerKey, "");

            MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(question,optionA,optionB,optionC,optionD,correctAnswer);

            questions.add(mcq);
        }

        return questions;
    }
}
