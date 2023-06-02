package IO;

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
        try
        {
            //Open txt file
            FileReader fr = new FileReader("Questions/tests.txt");
            BufferedReader br= new BufferedReader(fr);

            br.readLine();

            String line= br.readLine();

            //Loop through each line in file and get data
            while(line!= null) {
                String s[]= line.split(",");
                int testNumber = Integer.parseInt(s[0]);
                String type = s[1].trim();
                String name = s[2].trim();

                ArrayList<String> data = new ArrayList<>();

                data.add(type);
                data.add(name);

                tests.put(testNumber, data);

                line= br.readLine();
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        return tests;
    }
}
