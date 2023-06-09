package gameScreens;

import gameHelpers.GameRenderer;
import gameHelpers.GameUpdater;
import IO.ReadPeople;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gameApp.Game;
import model.NPC;
import model.NPCNoTest;
import model.NPCTest;
import model.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen extends ScreenAdapter{

    /**
     * Represents the game state, including the game object, the map, player and NPC information, camera, collision layer,
     * renderer, updater, skins, stages, labels, images, buttons, dialogs, and other variables.
     *
     * @param game The game object
     * @param map The TiledMap object representing the game map
     * @param playerName The name of the player
     * @param gender The gender of the player
     * @param camera The OrthographicCamera object representing the game camera
     * @param collisionLayer The TiledMapTileLayer object representing the collision layer of the game map
     * @param player The Player object representing the player in the game
     * @param npc The ArrayList of NPC objects representing the non
     */

    private Game game;

    private TiledMap map;

    private String playerName;
    private String gender;

    private OrthographicCamera camera;
    private TiledMapTileLayer collisionLayer;

    //People
    private Player player;
    private ArrayList<NPC> npc;

    private SpriteBatch batch;

    //Game helpers
    private GameRenderer gameRenderer;
    private GameUpdater gameUpdater;

    //Skins
    private Skin skinDialog;
    private Skin skin;
    private Skin skinSettings;

    //Stages
    private Stage stageDialog;
    private Stage stageLives;

    //Dialog variables
    private Label label;
    private Image portrait;
    private boolean draw=false;
    private boolean write=false;
    private boolean interacting=false;
    private NPC npcInteract;
    private int count=0;
    private int letterCount=0;
    private String text;

    //Lives and settings variables
    private Image lives;
    private Button settings;
    private Dialog settingsBox;


    /**
     * Constructs a new GameScreen object, which is responsible for rendering the game world and handling game logic.
     *
     * @param game The Game object that this screen belongs to.
     * @param saveData A HashMap containing the saved data for the player.
     * @param keys An ArrayList containing the keys for the saved data.
     * @throws FileNotFoundException If the map file cannot be found.
     */
    public GameScreen(Game game, HashMap<String, String> saveData, ArrayList<String> keys) throws FileNotFoundException {

        this.game = game;
        this.playerName= saveData.get(keys.get(2));
        this.gender=saveData.get(keys.get(3));;

        batch = new SpriteBatch();

        map = new TmxMapLoader().load("Map/icaiMap.tmx");

        stageDialog = new Stage(new ScreenViewport());
        stageLives=new Stage(new ScreenViewport());

        loadSkins();

        createDialogBlueprint();

        createSettingsBox();

        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Colisiones");

        player=new Player(
                (float) Double.parseDouble(saveData.get(keys.get(4))),
                (float) Double.parseDouble(saveData.get(keys.get(5))),
                gender,
                saveData.get(keys.get(6)));

        //Read and load people from a JSON file (in ReadPeople class)
        npc=ReadPeople.loadPeople();

        gameUpdater = new GameUpdater(player,collisionLayer,npc);

        //Set camera to initial values
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 24,16 );
        batch.setProjectionMatrix(camera.combined);

        //Add people to gameRenderer
        gameRenderer = new GameRenderer(map,batch);
        gameRenderer.addPerson(player);
        for (NPC p: npc) {
            gameRenderer.addPerson(p);
        }
        gameRenderer.setView(camera);

    }

    /**
     * Loads the skins for the UI elements of the game.
     * The skin files are located in the internal directory of the game.
     * The skin files are in JSON format and are loaded using the LibGDX framework.
     */
    private void loadSkins() {
        skinDialog = new Skin(Gdx.files.internal("SkinDialog/terra-mother-ui.json"));
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));
        skinSettings=new Skin(Gdx.files.internal("SkinSettings/cloud-form-ui.json"));
    }

    /**
     * Creates a dialog blueprint with a label of a specific size and position.
     * The label is added to the stage of the dialog.
     */
    private void createDialogBlueprint() {
        label = new Label("",skinDialog,"year199x");
        label.setSize(850, 120);
        label.setPosition(100,0);

        stageDialog.addActor(label);
    }

    /**
     * Creates a settings box with various options for the game.
     * The settings box includes options for volume control, saving the game, and closing the game.
     * The settings box is a dialog window with a table layout.
     */
    private void createSettingsBox() {

        //Create settings button
        Image gear=new Image(new Texture("Lives/gear.png"));
        settings = new Button(gear,skin,"clear");
        settings.setPosition(860,575);

        //Initialize setting window
        settingsBox=new Dialog("CONFIGURACION",skinSettings,"dialog");
        settingsBox.setSize(250,250);

        Table table=new Table();

        //Create all the components of the window
        Label titleLabel=new Label("AJUSTES\nTeclas - Movimiento: W A S D\nTeclas - Hablar: E",skinSettings);
        Label volLabel=new Label("Volumen: ",skinSettings);
        final Slider volSlider= new Slider(0,100,5,false,skinSettings);
        volSlider.setSize(200,50);
        volSlider.setValue(game.getMusicVolume()*100);
        Label saveLabel= new Label("Guardar juego: ",skinSettings);
        Button saveButton= new TextButton("Guardar",skinSettings);
        Button saveCloseButton= new TextButton("Guardar y Cerrar",skinSettings);
        Button closeSettingsButton=new TextButton("Cerrar Configuracion",skinSettings);

        //Add components to the table
        table.add(titleLabel).colspan(2).pad(25).padBottom(25).row();
        table.add(volLabel).pad(25);
        table.add(volSlider).pad(25).width(200).row();
        table.add(saveLabel).pad(25);
        table.add(saveButton).pad(25);
        table.add(saveCloseButton).pad(25).row();
        table.add(closeSettingsButton).colspan(2).pad(25);

        //Add table to settings window
        settingsBox.add(table);

        //Adds a ChangeListener to the volume slider, which sets the music volume of the game to the value of the slider.
        volSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setMusicVolume(volSlider.getValue());
            }
        });

        //Adds a click listener to the close settings button that hides the settings box when clicked.
        closeSettingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsBox.setVisible(false);
            }
        });

        //Adds a listener to the save button that saves the game data when clicked.
        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.saveData();
            }
        });

        //Adds a click listener to the saveCloseButton that saves the game data and closes the game when clicked.
        saveCloseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.saveData();
                game.closeGame();
            }
        });
    }

    /**
     * Renders the game screen by clearing the screen, updating the game renderer, cycling the game, and drawing the lives.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Clear the screen before drawing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateGameRenderer();

        gameCycle(delta);

        drawLives(delta);

    }

    /**
     * Runs the game cycle, updating the game and checking for NPC interactions.
     * If an interaction is detected, the appropriate NPC dialog is displayed and
     * the game state is updated accordingly.
     *
     * @param delta The time elapsed since the last frame
     */
    private void gameCycle(float delta) {
        NPCTest npcInteractTest;
        NPCNoTest npcInteractNoTest;

        /**
         * Checks if the player is interacting with an NPC and updates the game accordingly.
         * If the player is interacting with an NPC, the NPC's dialog is displayed along with their portrait.
         * If the NPC is associated with a test, the player's progress is checked to see if they are on the correct test.
         */
        if (!interacting){

            gameUpdater.update(delta);

            npcInteract = gameUpdater.checkInteraction();

            if (npcInteract!=null){
                interacting = true;

                /**
                 * Handles the interaction with an NPC of type NPCTest. If the NPC is associated with the next test in the game,
                 * the NPC's dialog is displayed. Otherwise, the NPC's rest dialog is displayed.
                 */
                if (npcInteract instanceof NPCTest){

                    //Downcasting npcInteract to NPCTest
                    npcInteractTest=(NPCTest)npcInteract;

                    int nextPrueba = game.getNextTestNumber();
                    if(nextPrueba==npcInteractTest.getAssociatedTestNumber()){
                        text = npcInteract.getName()+": "+npcInteractTest.getNpcDialog();
                    }
                    else{
                        text = npcInteractTest.getName()+": "+((NPCTest)npc.get(nextPrueba-1)).getRestDialog();
                    }

                    if (portrait != null) {
                        portrait.remove();
                    }
                    portrait=new Image(new Texture("People/Retratos/prueba"+npcInteractTest.getAssociatedTestNumber()+".png"));
                    portrait.setPosition(0,0);
                    portrait.setSize(100,100);
                    stageDialog.addActor(portrait);

                
                }
                //Handles interaction with an NPC that does not require a test.
                //Displays the NPC's name and dialog, and shows their portrait.
                else if (npcInteract instanceof NPCNoTest){

                    //Downcasting npcInteract to NPCNoTest
                    npcInteractNoTest=(NPCNoTest) npcInteract;
                    text=npcInteractNoTest.getName()+": "+npcInteractNoTest.getDialog();

                    if (portrait != null) {
                        portrait.remove();
                    }
                    portrait=new Image(new Texture("People/Retratos/"+npcInteractNoTest.getName()+".png"));
                    portrait.setPosition(0,0);
                    portrait.setSize(100,100);
                    stageDialog.addActor(portrait);
                }

                //Set state variables accordingly
                draw=true;
                write = true;
            }

        }
        /**
         * Handles the case where the npc is not speaking and player has interacted with an NPC.
         * If the user has inputted ENTER, and the NPC is an instance of NPCTest,
         * the game will start the test associated with the NPC. Otherwise, the interaction
         * will end and the label will be reset.
         * If the user has inputted ESC, the interaction will end and the label will be reset.
         */
        else if(!write){

            if(gameUpdater.checkInput()==0){

                if (npcInteract instanceof NPCTest){
                    game.startTest(((NPCTest)npcInteract).getAssociatedTestNumber());
                }
                draw=false;
                interacting = false;
                letterCount = 0;
                label.setText("");

            }else if(gameUpdater.checkInput()==1){
                draw = false;
                interacting = false;
                letterCount = 0;
                label.setText("");
            }
        }

        //Animates the text by writing it out letter by letter, with a delay of two counts between each letter.
        if (write){
            count+=1;

            if(count == 1){
                label.setText(text.substring(0, letterCount));
                letterCount+=1;
                count = 0;
            }

            if (letterCount==text.length()+1){
                write = false;
            }
        }

        //If the draw flag is set to true, the dialog stage is updated and drawn.
        if (draw){
            stageDialog.act(delta);
            stageDialog.draw();
        }
    }

    /**
     * Draws the current number of lives on the screen.
     *
     * @param delta The time in seconds since the last render call
     */
    private void drawLives(float delta) {
        lives=new Image(new Texture("Lives/"+game.getLives()+"lives.png"));
        lives.setPosition(10,575);
        stageLives.addActor(lives);

        stageLives.act(delta);
        stageLives.draw();
    }

    /**
     * Updates the game renderer with the current camera position and view.
     * The screen coordinates are also set based on the camera position and viewport size.
     */
    private void updateGameRenderer() {
        // Set the camera's position to the player's position
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
        gameRenderer.setView(camera);

        float screenX=camera.position.x-camera.viewportWidth/2;
        float screenY=camera.position.y-camera.viewportHeight/2;
        gameRenderer.setScreenCoordinates(screenX,screenY);

        gameRenderer.render();
    }

    /**
     * Shows the settings button on the stage and sets the input processor to the stage.
     * When the settings button is clicked, the settings box is shown and made visible.
     */
    @Override
    public void show() {

       //Adds a click listener to the settings button that shows the settings box when clicked.
       settings.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               settingsBox.show(stageLives);
               settingsBox.setVisible(true);
           }
       });

       stageLives.addActor(settings);
       Gdx.input.setInputProcessor(stageLives);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * Returns a HashMap containing the save data for a player.
     *
     * @param keys An ArrayList of keys to use for the HashMap
     * @return A HashMap containing the save data for a player
     */
    public HashMap<String, String> getSavedData(ArrayList<String> keys) {
        HashMap<String,String> saveData = new HashMap<>();
        saveData.put(keys.get(2), playerName);
        saveData.put(keys.get(4), String.valueOf(player.getX()));
        saveData.put(keys.get(5), String.valueOf(player.getY()));
        saveData.put(keys.get(6), player.getDirection());
        saveData.put(keys.get(3), gender);

        return saveData;
    }

}
