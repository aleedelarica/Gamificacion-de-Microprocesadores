package IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import questionTypes.DropDownQuestion;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ReadDropDown {

    /**
     * Loads a set of drop-down questions from a JSON file.
     *
     * @param fileName The name of the JSON file to load the questions from.
     * @return An ArrayList of DropDownQuestion objects.
     */
    public static ArrayList<DropDownQuestion> loadQuestions(String fileName) {

        //Create empty list of questions
        ArrayList<DropDownQuestion> questions= new ArrayList<>();

        //Open file as JSON
        FileHandle file = Gdx.files.internal("Questions/"+fileName+".json");
        JsonReader jsonReader = new JsonReader();
        JsonValue baseValue = jsonReader.parse(file);


        //Parses a JSON array of dropdown questions and returns a list of DropDownQuestion objects.
        for (JsonValue value : baseValue) {
            // Get the question and options
            String question = value.getString("PREGUNTA");
            int spaces = value.getInt("HUECOS");
            int options = value.getInt("OPCIONES");

            DropDownQuestion ddq = new DropDownQuestion(question, spaces, options);

            // Get the options for the question
            for (int i = 1; i <= options; i++) {
                String optionKey = "OPCION " + (char) (i + 64);
                String option = value.getString(optionKey, "");
                ddq.addOption(option);
            }

            //Get correct answers for the question
            for (int i = 1; i <= spaces; i++) {
                String inputKey = "ENTRADA " + i;
                String input = value.getString(inputKey, "");
                ddq.addAnswer(input);
            }
            questions.add(ddq);
        }
            return questions;
    }
}
