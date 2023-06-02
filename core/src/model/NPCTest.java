package model;

public class NPCTest extends NPC{

    /**
     * Represents a dialog between an NPC and a player during a test.
     *
     * @param associatedTestNumber The number of the test associated with this dialog
     * @param npcDialog The dialog spoken by the NPC
     * @param restDialog The dialog spoken by the player in response
     */
    private final int associatedTestNumber;
    private final String npcDialog;
    private final String restDialog;


    /**
     * Represents an NPC test entity with a given position, associated test number, NPC dialog, rest dialog, and name.
     *
     * @param x The x-coordinate of the NPC's position
     * @param y The y-coordinate of the NPC's position
     * @param associatedTestNumber The number of the test associated with this NPC
     * @param npcDialog The dialog spoken by the NPC during the test
     * @param restDialog The dialog spoken by the NPC during rest periods
     * @param name The name of the NPC
     */
    public NPCTest(float x, float y, int associatedTestNumber, String npcDialog, String restDialog, String name) {
        super(x, y, "People/Secundarios/prueba"+ associatedTestNumber +".png",name);
        this.associatedTestNumber = associatedTestNumber;
        this.npcDialog = npcDialog;
        this.restDialog = restDialog;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////
    
    public int getAssociatedTestNumber() {
        return associatedTestNumber;
    }

    public String getNpcDialog(){
        return npcDialog;
    }

    public String getRestDialog(){
        return restDialog;
    }
}
