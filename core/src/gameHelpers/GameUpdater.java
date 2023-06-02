package gameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import model.NPC;
import model.Person;
import model.Player;

import java.util.ArrayList;

public class GameUpdater {

    /**
     * Represents a game world, containing a player, collision layer, and a list of NPCs.
     */
    private Player player;

    private TiledMapTileLayer collisionLayer;

    private ArrayList<NPC> npcList;

    public GameUpdater(Player player, TiledMapTileLayer collisionLayer, ArrayList<NPC> npc) {
        this.player = player;
        this.collisionLayer =collisionLayer;
        this.npcList = npc;
    }

    /**
     * Updates the game state based on the player's input and current position.
     *
     * @param delta The time elapsed since the last update
     */
    public void update(float delta) {
        //If player is in any of these coordinates, teleport to another place (To move between maps)
        if(player.getX()>15 && player.getX()<20 && player.getY()==41){
            player.teleport(96,6);
        }
        else if(player.getX()>94 && player.getX()<98 && player.getY()==1){
            player.teleport(18,46);
        }
        else if(player.getX()>42 && player.getX()<47 && player.getY()==56){
            player.teleport(112,83);
        }
        else if(player.getX()>109 && player.getX()<115 && player.getY()==84){
            player.teleport(45,62);
        }

        //If player pressed any of these keys and there is no collision, move
        //If there is a collision there, turn
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            if (!checkCollision(player.getX(), player.getY()+1, collisionLayer)){
                player.move(0,1);
            }else{
                player.turn(0,1);
            }

        } else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            if (!checkCollision(player.getX(), player.getY()-1, collisionLayer)) {
                player.move(0, -1);
            }else{
                player.turn(0,-1);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)){
            if (!checkCollision(player.getX()+1, player.getY(), collisionLayer)) {
                player.move(1, 0);
            }else{
                player.turn(1,0);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)){
            if (!checkCollision(player.getX()-1, player.getY(), collisionLayer)) {
                player.move(-1, 0);
            }else{
                player.turn(-1,0);
            }
        }
        //Update player
        player.update(delta);
    }

    /**
     * Determines if the next position of the player is blocked by a collision layer or an NPC.
     *
     * @param nextX The next X position of the player.
     * @param nextY The next Y position of the player.
     * @param collisionLayer The collision layer of the game map.
     * @return True if the next position is blocked, false otherwise.
     */
    public boolean checkCollision(float nextX, float nextY, TiledMapTileLayer collisionLayer) {
        boolean npcNext = false;
        //Get the cell at the next position
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int)nextX , (int)nextY );
        // Check if the cell is null
        if(cell == null){
            //If cell is null, there is no collision
            //Iterate through people
            for(Person p:npcList){
                //Check if npc next to player
                if(p.getX()==nextX && p.getY()==nextY){
                    npcNext = true;
                }
            }
        }
        //If cell is not null or there is a npc next to the player, player is bloqued
        return cell!=null || npcNext;
    }

    /**
     * Checks if the player is interacting with an NPC by checking if the 'E' key is pressed and
     * the player is facing an NPC.
     *
     * @return The NPC that the player is interacting with, or null if there is no interaction.
     */
    public NPC checkInteraction() {
        NPC npc = null;
        //Check if player pressed key E in the keyboard
        if (Gdx.input.isKeyPressed(Input.Keys.E)){
            //Get the interacting direction
            if(player.getDirection().equals("derecha")){
                //Check if there is an NPC in that direction
                npc = checkNPC(player.getX()+1,player.getY());
            }
            else if(player.getDirection().equals("izquierda")){
                npc = checkNPC(player.getX()-1,player.getY());
            }
            else if(player.getDirection().equals("espaldas")){
                npc = checkNPC(player.getX(),player.getY()+1);
            }
            else if(player.getDirection().equals("frente")){
                npc = checkNPC(player.getX(),player.getY()-1);
            }
        }
        return npc;
    }

    /**
     * Searches for an NPC in the list of NPCs at a given (x, y) coordinate.
     *
     * @param x The x-coordinate to search for
     * @param y The y-coordinate to search for
     * @return The NPC at the given (x, y) coordinate, or null if no NPC is found
     */
    public NPC checkNPC(float x, float y) {
        NPC npc = null;
        //Iterate through NPCs list
        for(NPC p:npcList){
            if(p.getY()==y && p.getX()==x){
                npc=p;
            }
        }
        return npc;
    }

    /**
     * Checks for user input and returns a value based on the key pressed.
     *
     * @return -1 if no key is pressed, 0 if the enter key is pressed, and 1 if the escape key is pressed.
     */
    public int checkInput() {
        //Check keyboard input
        int ret = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){ret = 1;}
        else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){ret = 0;}
        return ret;
    }
}
