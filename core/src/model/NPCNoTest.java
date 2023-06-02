package model;

public class NPCNoTest extends NPC{

    private final String dialog;

    /**
     * Represents a non-playable character (NPC) that does not have a test associated with it.
     *
     * @param x The x-coordinate of the NPC's location
     * @param y The y-coordinate of the NPC's location
     * @param url The URL of the NPC's image
     * @param name The name of the NPC
     * @param dialog The dialog spoken by the NPC
     */
    public NPCNoTest(float x, float y, String url, String name, String dialog) {
        super(x, y, url, name);
        this.dialog=dialog;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////    

    public String getDialog() {
        return dialog;
    }
}
