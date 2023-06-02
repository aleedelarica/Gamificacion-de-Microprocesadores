package gameScreens;

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

public class WinScreen extends ScreenAdapter {

    /**
     * A class representing the game over screen of the game. This screen is displayed when the game ends.
     * It contains a restart button, an end button, and a game over label.
     *
     * @param game The game object that this screen belongs to.
     * @param stage The stage that this screen will be displayed on.
     * @param table The table that will hold the buttons and labels.
     * @param skin The skin used for the buttons and labels.
     * @param skinDialog The skin used for the dialog box.
     * @param restartButton The button that restarts the game.
     * @param endButton The button that ends the game.
     * @param gameOverLabel The label that displays the game over message.
     */

    private Game game;

    private Stage stage;
    private Table table;

    private Skin skin, skinDialog;

    private TextButton restartButton, endButton;
    private Label gameOverLabel;

    /**
     * The EndScreen class represents the screen that is displayed when the game is over.
     * It contains a game object, a stage, a table, a skin, a skinDialog, a gameOverLabel, a restartButton,
     * an endButton, and a titleImage.
     *
     * @param game The game object that this screen is associated with.
     */
    public WinScreen(Game game) {
        
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table =new Table();
        table.setFillParent(true);

        //Loads two skins from JSON files to be used in the UI.
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));
        skinDialog = new Skin(Gdx.files.internal("SkinDialog/terra-mother-ui.json"));

        gameOverLabel = new Label("Enhorabuena, ¡¡has superado todas las pruebas!!.\n El juego ha terminado para ti, ahora entiendes de microprocesadores", skinDialog, "black");
        gameOverLabel.setPosition(200,Gdx.graphics.getHeight()/2+50);
        stage.addActor(gameOverLabel);

        restartButton =new TextButton("Comenzar de nuevo",skin);
        endButton = new TextButton("Cerrar Juego", skin);

        //Creates a texture from the file "Title/title.png", creates an image from the texture
        Texture texture = new Texture(Gdx.files.internal("Title/title.png"));
        Image titleImage = new Image(texture);
        titleImage.setSize(texture.getWidth()*3,texture.getHeight()*3);
        titleImage.setPosition(300,Gdx.graphics.getHeight()*2/3);
        stage.addActor(titleImage);

        stage.addActor(table);
    }

    /**
     * Shows the game over screen with restart and end game buttons.
     * Sets up click listeners for the buttons to call the appropriate game methods.
     */
    @Override
    public void show() {

        //Adds a click listener to the restart button that restarts the game when clicked.
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.restartGame();
            }
        });

        //Adds a click listener to the end button that ends the game when clicked.
        endButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.endGame();
            }
        });

        table.add(restartButton).padTop(300).width(300).height(50);
        table.add(endButton).padTop(300).width(300).height(50);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders the stage
     *
     * @param v The time in seconds since the last render call.
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
}
