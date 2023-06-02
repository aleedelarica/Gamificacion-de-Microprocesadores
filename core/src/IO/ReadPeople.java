package IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import model.NPC;
import model.NPCNoTest;
import model.NPCTest;
import java.util.ArrayList;

public class ReadPeople {
    /**
     * Loads the NPCs from a JSON file and returns them as an ArrayList.
     *
     * @return An ArrayList of NPCs loaded from the JSON file.
     */
    public static ArrayList<NPC> loadPeople() {

        //Create empty list of people
        ArrayList<NPC> people= new ArrayList<>();

        //Open file as JSON
        FileHandle file = Gdx.files.internal("People/people.json");
        JsonReader jsonReader = new JsonReader();
        JsonValue baseValue = jsonReader.parse(file);

        // Loop through each JSON object in the array
        for (JsonValue value : baseValue) {
            // Get the options for each NPC
            int asociatedTest = value.getInt("Prueba");
            int x = value.getInt("Coordenada X");
            int y = value.getInt("Coordenada Y");
            String npcDialog=value.getString("Personaje de la prueba");
            String restDialog=value.getString("El resto de personajes");
            String name=value.getString("Nombre");

            NPC npc= new NPCTest(x,y,asociatedTest,npcDialog,restDialog,name);
            people.add(npc);
        }

        //Create NPC with no tests
        NPC zipi=new NPCNoTest(91,19,"People/Secundarios/Zipi.png","Zipi","Que te pongo?");
        NPC zape=new NPCNoTest(91,15,"People/Secundarios/Zape.png","Zape","Quieres un Charlie? Claro, sin problema.");
        NPC pau=new NPCNoTest(89,7,"People/Secundarios/Pau.png","Pau","Yo soy de ICADE, mejor busca a otro");
        NPC josedani=new NPCNoTest(126,82,"People/Secundarios/Jose Daniel.png","Jose Daniel","Ponte a estudiar");

        //Add them to the list
        people.add(zipi);
        people.add(zape);
        people.add(pau);
        people.add(josedani);

        return people;
}
}
