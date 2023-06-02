package gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gameApp.Game;

public class TitleScreen extends ScreenAdapter {
    
    /**
     * Represents a game screen with a start button and a restart button.
     * The screen is built using a Stage and a Table, and uses a Skin for styling.
     * The saved field indicates whether the game state has been saved.
     *
     * @param game The game instance
     */
    private Game game;

    private Stage stage;
    private Table table;
    
    private Skin skin;

    private boolean saved;

    private TextButton startButton;
    private TextButton restartButton;

    /**
     * The TitleScreen class represents the screen that is displayed when the game is launched.
     * It contains a title image, a start button, and a restart button if there is a previous game.
     *
     * @param game The Game object that this screen belongs to.
     * @param saved A boolean indicating whether the game has been saved previously.
     */
    public TitleScreen(Game game,boolean saved) {

        this.game = game;
        this.saved=saved;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table =new Table();
        table.setFillParent(true);

        //Loads the skin for the UI elements of the game.
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));

        //Create buttons
        startButton =new TextButton("Comenzar",skin);
        restartButton=new TextButton("Reiniciar partida", skin);

        //Create title texture
        Texture texture = new Texture(Gdx.files.internal("Title/title.png"));
        Image titleImage = new Image(texture);
        titleImage.setSize(texture.getWidth()*6,texture.getHeight()*6);
        titleImage.setPosition(50,Gdx.graphics.getHeight()*1/3);
        stage.addActor(titleImage);

        stage.addActor(table);
    }

    /**
     * Shows the main menu screen and sets up the buttons for starting a new game or restarting an existing one.
     * If a saved game exists, the restart button is displayed and the start button's text is changed to "Continuar jugando".
     */
    @Override
    public void show() {

        //Adds a listener to the start button that triggers the player selection method when clicked.
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playerSelection();
            }
        });

        //Adds a click listener to the restart button that calls the startOver() method of the game object.
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.startOver();
            }
        });

        table.add(startButton).padTop(300).width(300).height(50);

        //If the game is saved, adds a restart button to the table. Changes the text of the start button to "Continuar jugando".
        if(saved){
            table.add(restartButton).padTop(300).width(300).height(50);
            startButton.setText("Continuar jugando");
        }

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders the stage.
     *
     * @param v The time in seconds since the last render.
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
