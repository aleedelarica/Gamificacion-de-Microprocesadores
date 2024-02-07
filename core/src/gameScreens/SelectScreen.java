package gameScreens;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gameApp.Game;
import questionTypes.FillInTheGapQuestion;

public class SelectScreen extends ScreenAdapter {
    
    /**
     * A class representing the character creation screen of the game.
     * Allows the player to choose their gender and name before starting the game.
     * Contains a table with UI elements such as buttons and text fields.
     *
     * @param game The game instance
     */

    private Game game;

    private Stage stage;
    private Table table;

    private Skin skin;

    private Button girlButton, boyButton;
    private TextField name;

    private String gender;

    private TextButton startButton;
    private TextField clave;

    /**
     * The SelectScreen class represents the screen where the player can select their character and enter their name.
     * @param game The game instance.
     */
    public SelectScreen(Game game) {

        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table =new Table();
        table.setFillParent(true);

        //Loads the skin for the UI elements of the game.
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));

        //Create title texture
        Texture texture = new Texture(Gdx.files.internal("Title/title.png"));
        Image titleImage = new Image(texture);
        titleImage.setSize(texture.getWidth()*2,texture.getHeight()*2);
        titleImage.setPosition(350,Gdx.graphics.getHeight()*2/3+55);
        stage.addActor(titleImage);

        //Create label and textfield for name input
        Label nameLabel=new Label("Nombre de tu personaje: ",skin,"title");
        name= new TextField("",skin);
        Label claveLabel=new Label("Clave nivel: ",skin,"title");
        clave = new TextField("",skin);


        //Create label and buttons for gender selection
        Label spriteLabel=new Label("Elige personaje: ",skin,"title");
        Texture boyTexture = new Texture(Gdx.files.internal("People/Retratos/Boy.png"));
        Image boyImage = new Image(boyTexture);
        Texture girlTexture = new Texture(Gdx.files.internal("People/Retratos/Lady2.png"));
        Image girlImage = new Image(girlTexture);

        boyButton=new Button(boyImage,skin);
        girlButton= new Button(girlImage,skin);
        startButton =new TextButton("Iniciar Juego",skin);
        boyButton.setChecked(true);

        //Add components to table
        table.add(nameLabel).padBottom(25);
        table.add(name).padBottom(25).row();
        table.add(claveLabel).padBottom(25);
        table.add(clave).padBottom(25).row();
        table.add(spriteLabel).colspan(2).left().padBottom(25).row();

        //Add table to stage
        stage.addActor(table);
    }

    /**
     * Shows the main menu screen and sets up the button listeners for starting the game and selecting the player's gender.
     * The gender is stored in the gender variable, which is updated when the player selects a gender.
     * The start button is only enabled if the player has entered a name.
     */
    @Override
    public void show() {

        //Adds a listener to the start button that starts the game if the name field is not empty.
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!name.getText().equals("")){
                    game.startGame();
                }
            }
        });

        //Adds a ClickListener to the boyButton that sets the gender variable to "ChicoProtagonista"
        boyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                girlButton.setChecked(false);
                gender="ChicoProtagonista";
            }
        });

        //Adds a ClickListener to the boyButton that sets the gender variable to "ChicaProtagonista"
        girlButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boyButton.setChecked(false);
                gender="ChicaProtagonista";
            }
        });

        //Add buttons to table
        table.add(boyButton).padBottom(50);
        table.add(girlButton).padBottom(50).row();
        table.add(startButton).colspan(2).right();


        //Sets the input processor for the current stage.
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders the stage with a clear color and updates the actors within the stage.
     *
     * @param v The time in seconds since the last render call
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(207/255f, 185/255f, 151/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getGender(){
        return gender;
    }

    public String getName(){
        return name.getText();
    }

    public String getClave(){
        return clave.getText();
    }
}
