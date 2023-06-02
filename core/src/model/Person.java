package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Person {
    /**
     * Represents a person in the game world.
     * Contains the person's position and texture information.
     */
    protected float x;
    protected float y;

    protected Texture personTexture;
    protected Sprite sprite;

    /**
     * Creates a new Person object with the given coordinates and texture URL.
     *
     * @param x The x-coordinate of the person
     * @param y The y-coordinate of the person
     * @param textureUrl The URL of the texture to use for the person
     */
    public Person(float x, float y,String textureUrl){
        this.x=x;
        this.y=y;

        personTexture=new Texture(textureUrl);

        sprite=new Sprite(personTexture);
        sprite.setOrigin(0,0);
        sprite.setScale(1/16f);
    }

    /**
     * Draws the sprite on the screen at the given position.
     */
    public void draw(SpriteBatch batch,float screenX,float screenY) {
        sprite.setPosition(getX()-screenX,getY()-screenY);
        sprite.draw(batch);
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    //Sets the texture of a sprite to the image located at the given URL.
    public void setSpriteTexture(String url) {
       Texture newTexture=new Texture(url);
       sprite.setTexture(newTexture);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return personTexture.getWidth();
    }

    public int getHeight() {
        return personTexture.getHeight();
    }
}
