package IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadTests {
    /**
     * Loads the tests from a file and returns them as a HashMap.
     *
     * @return A HashMap containing the tests, where the key is the test number and the value is an ArrayList containing the type and name of the test.
     * @throws IOException If there is an error reading the file.
     */
    public static HashMap<Integer, ArrayList<String>> loadTests()
    {
        //Create empty hashmap of tests
        HashMap<Integer, ArrayList<String>> tests= new HashMap<>();
        FileHandle file = Gdx.files.internal("Questions/tests.json");
        JsonReader jsonReader = new JsonReader();
        JsonValue baseValue = jsonReader.parse(file);

        // Loop through each JSON object in the array
        for (JsonValue value : baseValue) {
            //Loop through each line in file and get data
            int testNumber = value.getInt("TestNum");
            String type = value.getString("TestType");
            String name = value.getString("FileName");
            String key = value.getString("Key");

            ArrayList<String> data = new ArrayList<>();

            data.add(type);
            data.add(name);
            data.add(key);
            
            tests.put(testNumber, data);

        }

        return tests;
    }
}
