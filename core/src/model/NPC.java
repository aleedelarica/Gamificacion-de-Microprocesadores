package model;

public abstract class NPC extends Person{

    private final String name;

    /**
     * Creates a new NPC object with the given coordinates, image URL, and name.
     */
    public NPC(float x, float y, String url,String name){
        super(x,y,url);
        this.name =name;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getName() {
        return name;
    }


}
