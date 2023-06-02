package IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import questionTypes.FillInTheGapQuestion;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ReadFillInTheGap {
    /**
     * Loads fill-in-the-gap questions from a JSON file and returns them as an ArrayList.
     *
     * @param fileName The name of the JSON file to load questions from.
     * @return An ArrayList of FillInTheGapQuestion objects loaded from the JSON file.
     */
    public static ArrayList<FillInTheGapQuestion> loadQuestions(String fileName) {

        ArrayList<FillInTheGapQuestion> questions= new ArrayList<>();

        FileHandle file = Gdx.files.internal("Questions/"+fileName+".json");
        JsonReader jsonReader = new JsonReader();
        JsonValue baseValue = jsonReader.parse(file);

        // Loop through each JSON object in the array
        for (JsonValue value : baseValue) {
            // Get the question and options
            String question = value.getString("PREGUNTA");
            int spaces = value.getInt("HUECOS");

            FillInTheGapQuestion figq = new FillInTheGapQuestion(question, spaces);

            // Get the right answers for the question
            for (int i = 1; i <= spaces; i++) {
                String answerKey = "RESPUESTA " + i;
                String answer = value.getString(answerKey, "");
                figq.addAnswer(answer);
            }

            questions.add(figq);
        }
        return questions;
    }
}
