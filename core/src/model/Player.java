package model;

import com.badlogic.gdx.math.Interpolation;

public class Player extends Person{

    /**
     * Represents a character in a game with its current state and properties.
     *
     * @param elapsedTime The time elapsed since the character started moving
     * @param moving Whether the character is currently moving or not
     * @param targetX The target x-coordinate of the character's movement
     * @param targetY The target y-coordinate of the character's movement
     * @param gender The gender of the character
     * @param direction The direction the character is facing
     * @param velocity The velocity of the character's movement
     */
    private float elapsedTime;
    private boolean moving;
    private float targetX;
    private float targetY;

    private final String gender;

    private String direction;

    private static float velocity=1/6f;

    /**
     * Constructs a new Player object with the given x and y coordinates, gender, and direction.
     *
     * @param x The x-coordinate of the player's position
     * @param y The y-coordinate of the player's position
     * @param gender The gender of the player
     * @param direction The direction the player is facing
     */
    public Player(float x, float y, String gender, String direction) {
        super(x, y,"People/"+gender+"/frente.png");
        this.gender=gender;
        setDirection(direction);
    }

    /**
     * Moves the character by dx and dy units, updating the target position and direction.
     * If the character is already moving, this method does nothing.
     *
     * @param dx The amount to move in the x direction
     * @param dy The amount to move in the y direction
     */
    public void move(int dx, int dy){
        if (!moving) {
            if (dx==1){
                setDirection("derecha");
            }if (dx==-1){
                setDirection("izquierda");
            }if (dy==1){
                setDirection("espaldas");
            }if (dy==-1){
                setDirection("frente");
            }

            targetX = super.x + (float) dx;
            targetY = super.y + (float) dy;
            moving = true;
        }
    }

    /**
     * Updates the position of the object based on the elapsed time and velocity.
     * If the object is not moving, the method returns without doing anything.
     *
     * @param delta The time elapsed since the last update
     */
    public void update(float delta) {
        if(!moving){
            return;
        }
        elapsedTime+= delta;
        float alpha = Math.min(elapsedTime / velocity, 1);

        // use Interpolation.linear to move the player towards the target position
        super.x = Interpolation.linear.apply(super.x, targetX, alpha);
        super.y = Interpolation.linear.apply(super.y, targetY, alpha);

        animate(alpha);
    }

    /**
     * Animates the sprite based on the given alpha value.
     *
     * @param alpha The alpha value to use for the animation.
     *              Should be between 0 and 1, where 0 is the start of the animation and 1 is the end.
     */
    public void animate(float alpha){


        /**
         * Sets the texture of the sprite based on the alpha value.
         * If alpha is less than 1/3, the sprite texture will be set to "People/gender/andar_direction_1.png".
         * If alpha is between 1/3 and 2/3, the sprite texture will be set to "People/gender/direction.png".
         * If alpha is between 2/3 and 1, the sprite texture will be set to "People/gender/andar_direction_2.png".
         */
        if (alpha < 1/3f){
            setSpriteTexture("People/"+gender+"/andar_"+direction+"_1.png");
        }else if(alpha>=1/3f && alpha<2/3f){
            setSpriteTexture("People/"+gender+"/"+direction+".png");
        }else if(alpha>=2/3f && alpha<1){
            setSpriteTexture("People/"+gender+"/andar_"+direction+"_2.png");
        }

        /**
         * If the alpha value is greater than or equal to 1, the sprite's position is set to the target position,
         * the sprite is no longer moving, the elapsed time is reset to 0, and the sprite's texture is set to the
         * appropriate image based on the sprite's gender and direction.
         */
        if (alpha >= 1) {
            super.x = targetX;
            super.y = targetY;
            moving = false;
            elapsedTime = 0;
            setSpriteTexture("People/"+gender+"/"+direction+".png");
        }
    }

    /**
     * Changes the direction of the sprite based on the given dx and dy values.
     * If dx is 1, the direction is set to "derecha". If dx is -1, the direction is set to "izquierda".
     * If dy is 1, the direction is set to "espaldas". If dy is -1, the direction is set to "frente".
     * The sprite texture is then updated based on the new direction.
     */
    public void turn(int dx, int dy) {
        if (dx==1){
            setDirection("derecha");
        }if (dx==-1){
            setDirection("izquierda");
        }if (dy==1){
            setDirection("espaldas");
        }if (dy==-1){
            setDirection("frente");
        }

        setSpriteTexture("People/"+gender+"/"+direction+".png");
    }

    /**
     * Teleports the object to the specified coordinates.
     */
    public void teleport(float x, float y){
        this.x=x;
        this.y=y;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////
    
    private void setDirection(String direction) {
        this.direction=direction;
    }

    public String getDirection() {
        return direction;
    }
}

