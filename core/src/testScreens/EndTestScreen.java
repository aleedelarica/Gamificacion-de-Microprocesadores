package testScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import gameApp.Game;

import java.util.ArrayList;

public class EndTestScreen extends ScreenAdapter{

    /**
     * A class representing a game screen.
     * Contains a game, a stage, a table, two skins, a list of results, and an end button.
     */
    private Game game;

    private Stage stage;
    private Table table;

    private Skin skin;
    private Skin skinDialog;

    private ArrayList<Integer> results;

    private TextButton endButton;

    public EndTestScreen(Game game, boolean passed, ArrayList<Integer> results, String key) {

        this.game = game;
        this.results=results;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table =new Table();
        table.setFillParent(true);

        //loads skins from files
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));
        skinDialog = new Skin(Gdx.files.internal("SkinDialog/terra-mother-ui.json"));

        
        /**
         * Creates a label and sets its position. If the player has passed the test, the label will display a congratulatory message
         * and will be added to the stage. If the player has failed the test, the label will display a message indicating that they have lost a life
         * and will display the results of the test. The results are displayed as images of hearts.
         */
        Label label = new Label("", skinDialog, "black");
        label.setPosition(300,Gdx.graphics.getHeight()/2+50);
        if(passed){
            label.setText("Enhorabuena, has superado la prueba!! \nBusca la siguiente prueba por el mapa. Clave: " + key);
            stage.addActor(label);
        }else{
            table.row();
            label.setText("No has superado la prueba, pierdes una vida. \nEste es el resultado de la prueba:");
            stage.addActor(label);
            for (Integer i: results){
                Image answer= new Image(new Texture("Lives/"+i+".png"));
                table.add(answer).padTop(100);
            }
            table.row();
        }

        endButton =new TextButton("Volver al Juego",skin);

        //Create image textures and adds to table
        Texture texture = new Texture(Gdx.files.internal("Title/title.png"));
        Image titleImage = new Image(texture);
        titleImage.setSize(texture.getWidth()*3,texture.getHeight()*3);
        titleImage.setPosition(300,Gdx.graphics.getHeight()*2/3);
        stage.addActor(titleImage);

        stage.addActor(table);
    }

    /**
     * Shows the end game screen with the end button and sets the input processor to the stage.
     * When the end button is clicked, the game will return to the main menu.
     */
    @Override
    public void show() {

        endButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.returnGame();
            }
        });

        table.add(endButton).colspan(results.size()).padTop(20).width(300).height(50);

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
