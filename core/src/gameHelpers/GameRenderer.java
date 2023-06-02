package gameHelpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import model.Person;

import java.util.ArrayList;

public class GameRenderer extends OrthogonalTiledMapRenderer {

    /**
     * A class representing a group of people, with their screen coordinates and a sprite batch.
     *
     * @param people An ArrayList of Person objects in the group.
     * @param batch The SpriteBatch used to render the group.
     * @param screenX The x-coordinate of the group on the screen.
     * @param screenY The y-coordinate of the group on the screen.
     */
    
    private ArrayList<Person> people;

    SpriteBatch batch;

    private float screenX;
    private float screenY;

    public GameRenderer(TiledMap map, SpriteBatch batch) {
        super(map,1/16f);
        this.batch=batch;
        people=new ArrayList<>();
    }

    public void addPerson(Person p){
        people.add(p);
    }

    /**
     * Renders the map and people on the screen.
     * Begins the rendering process, then iterates through each layer of the map and renders
     * any tile layers. After rendering the map, the method begins the batch and iterates through
     * each person in the people list, drawing them on the screen. Finally, the batch is ended.
     */
    @Override
    public void render(){
        beginRender();
        //Iterate through map layer and render layer
        for (MapLayer layer:map.getLayers()){
            if (layer instanceof TiledMapTileLayer){
                renderTileLayer((TiledMapTileLayer)layer);
            }
        }
        endRender();


        batch.begin();
        //Iterate through all people and draw
        for (Person p: people){
            p.draw(batch,screenX,screenY);
        }
        batch.end();
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public void setScreenCoordinates(float screenX, float screenY) {
        this.screenX=screenX;
        this.screenY=screenY;
    }
}
