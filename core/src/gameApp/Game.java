package gameApp;

import IO.ReadTests;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import gameScreens.*;
import testScreens.DropDown;
import testScreens.EndTestScreen;
import testScreens.FillInTheGap;
import testScreens.MultipleChoice;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends com.badlogic.gdx.Game {

 /**
  * A class that manages the different screens of the game, as well as the game state.
  * Contains references to the different screens, as well as the current test being played,
  * the number of lives remaining, and whether the game state has been saved.
  *
  * @param gameScreen The screen where the game is played
  * @param titleScreen The screen where the game title is displayed
  * @param selectScreen The screen where the player selects a test to play
  * @param endScreen The screen displayed when the player loses all their lives
  * @param endTestScreen The screen displayed when the player completes a test
  * @param actualTestScreen The screen where the current test is displayed
  * @param tests A HashMap
  */
  
	//Screens
	private GameScreen gameScreen;
	private TitleScreen titleScreen;
	private SelectScreen selectScreen;
	private GameOverScreen endScreen;
	private WinScreen winScreen;
	private EndTestScreen endTestScreen;
	private ScreenAdapter actualTestScreen;

	//Tests
	private HashMap<Integer, ArrayList<String>> tests;

	private int nextTestNumber;
	private int lives;

	//Data variables
	private boolean saved=false;
	private HashMap<String, String > savedData;
	private ArrayList<String> keys;

	private Music menuMusic;

	public Game() {
	}

	/**
  * Initializes the game by loading keys and data, setting up the title and select screens,
  * loading tests, and playing menu music.
  */
	@Override
	public void create () {
		//Load the previous game if there is one
		loadKeys();
		loadData();


		if(savedData.get(keys.get(0))!=null){
			//If there is a previous game saved
			saved = true;
			//Load nextTestNumber and lives
			nextTestNumber = Integer.parseInt(savedData.get(keys.get(1)));
			lives = Integer.parseInt(savedData.get(keys.get(0)));
		}else{
			//If not, put nextTestNumber and lives to its initial values (1, 3)
			nextTestNumber = 1;
			lives = 3;
		}
		//nextTestNumber=6;
		//Initialize first screens
		titleScreen = new TitleScreen(this,saved);
		selectScreen = new SelectScreen(this);

		//Read and load tests (In ReadTests class)
		tests = ReadTests.loadTests();

		//Load background music
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/song.ogg"));
		menuMusic.setLooping(true);
		menuMusic.play();
		menuMusic.setVolume(0.1f);

		//Set main screen to the title screen
		setScreen(titleScreen);

	}


 /**
  * Handles the player selection process. If the game has been saved, it starts the game.
  * Otherwise, it sets the screen to the select screen.
  */
	public void playerSelection(){
		//Close title screen
		titleScreen.dispose();
		if (saved){
			//If there is saved data start the game
			startGame();
		}else{
			//If not,set main screen to the selection screen
			setScreen(selectScreen);
		}
	}


 /**
  * Starts the game by saving the player's data, initializing the game screen, and displaying it.
  * If the player's data has not been saved before, it will be saved with default values.
  */
	public void startGame(){
		if (!saved) {
			//If there is no saved data, save the initial variables
			savedData.put(keys.get(2), selectScreen.getName());
			savedData.put(keys.get(3), selectScreen.getGender());
			savedData.put(keys.get(4), "13");
			savedData.put(keys.get(5), "70");
			savedData.put(keys.get(6), "frente");
		}

		//Initialize the game screen
		initializeGameScreen();

	}

 /**
  * Initializes the game screen and sets it as the current screen.
  * Disposes of the select screen.
  * @throws FileNotFoundException if the saved data file is not found
  */
	private void initializeGameScreen() {
		try {
			//Create the GameScreen
			gameScreen = new GameScreen(this, savedData, keys);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//Close the selection screen
		selectScreen.dispose();
		//Set main screen to game screen
		setScreen(gameScreen);
	}

 /**
  * Starts a test of a given test number.
  *
  * @param testNumber The number of the test to start.
  */
	public void startTest(int testNumber) {
		//First we save data
		saveData();


		if (testNumber == nextTestNumber) {
			ArrayList<String> test = tests.get(testNumber);
			//If we are in the correct test
			try {
				//Create test screen depending on the type
				if (test.get(0).equals("MC")) {
					actualTestScreen = new MultipleChoice(this, test.get(1));
				}
				if (test.get(0).equals("DD")) {
					actualTestScreen = new DropDown(this, test.get(1));
				}
				if (test.get(0).equals("FG")) {
					actualTestScreen = new FillInTheGap(this, test.get(1));
				}
				//Set main screen to test screen
				setScreen(actualTestScreen);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

 /**
  * Ends the current test and displays the appropriate screen based on whether the test was passed or not.
  *
  * @param passed  A boolean indicating whether the test was passed or not.
  * @param results An ArrayList of integers representing the results of the test.
  */
	public void endTest(boolean passed, ArrayList<Integer> results){
		if(passed){
			//If the test was passed, add one to nextTestNumber
			nextTestNumber += 1;
		}else{
			//If not, take one life away
			lives-=1;
		}
		//Close the test screen
		actualTestScreen.dispose();

		if(lives > 0){
			//If there are lives left, set main screen to end test screen
			endTestScreen = new EndTestScreen(this, passed, results);
			winScreen= new WinScreen(this);
			if (nextTestNumber==14){
				setScreen(winScreen);
			}else{
				setScreen(endTestScreen);
			}

		}else {
			//If not, game over
			gameOver();
		}
	}

	
 /**
  * Returns the game screen after disposing of the end test screen.
  */
	public void returnGame(){
		//Close end test screen
		endTestScreen.dispose();
		//Set main screen to game screen
		setScreen(gameScreen);
	}

 /**
  * Resets the game state and displays the end screen.
  */
	public void gameOver() {
		//Reset nextTestNumber and lives to initial values (1, 3)
		lives = 3;
		nextTestNumber =1;
		//Save data
		saveData();
		//Set main screen to End screen
		endScreen = new GameOverScreen(this);
		setScreen(endScreen);
	}

 /**
  * Loads the keys for the game state.
  * The keys are added to an ArrayList.
  */
	private void loadKeys() {
		//Initialize key values for data saving file
		keys = new ArrayList<>();
		keys.add("lives");
		keys.add("nextTest");
		keys.add("name");
		keys.add("gender");
		keys.add("x");
		keys.add("y");
		keys.add("direction");
	}


 /**
  * Saves the current game data to the device's local storage.
  * The data includes the current number of lives and the next test number.
  */
	public void saveData() {
		//Store in game the data in game screen
		savedData = gameScreen.getSavedData(keys);
		savedData.put(keys.get(0), String.valueOf(lives));
		savedData.put(keys.get(1), String.valueOf(nextTestNumber));
		//Save data in local file
		Preferences prefs = Gdx.app.getPreferences("bin");
		prefs.put(savedData);
		prefs.flush();
	}

 /**
  * Loads data from the preferences file and stores it in a HashMap.
  * If a key is not found in the preferences file, it is added to the HashMap with a null value.
  */
	private void loadData() {
		savedData = new HashMap<>();
		//Load data from local file
		Preferences prefs = Gdx.app.getPreferences("bin");
		for(String key:keys){
			if(prefs.contains(key)) {
				//If local file contains key, save value for that key
				savedData.put(key, prefs.getString(key));
			}
			else{
				//If not, save null
				savedData.put(key,null);
			}
		}

	}

 /**
  * Restarts the game by disposing of the end screen and setting the game screen as the current screen.
  */
	public void restartGame(){
		//Close the End screen
		endScreen.dispose();
		//Set main screen to game screen
		setScreen(gameScreen);
	}

 /**
  * Ends the game by disposing of the end screen and the current game instance, and then exits the application.
  */
	public void endGame(){
		//Close the end screen
		endScreen.dispose();
		//Close game
		this.dispose();
		//Close the app
		Gdx.app.exit();
	}

 /**
  * Closes the game by disposing of the current screen and exiting the application.
  */
	public void closeGame(){
		//Close game
		this.dispose();
		//Close the app
		Gdx.app.exit();
	}

 /**
  * Resets the game state to the beginning, by setting saved to false and creating a new TitleScreen.
  * The current screen is then set to the new TitleScreen.
  */
	public void startOver(){
		//Put data saved to false
		saved=false;

		//Set variables to initial values
		lives=3;
		nextTestNumber=1;

		//Close the title screen, create a new one and set it as main screen
		titleScreen.dispose();
		titleScreen =new TitleScreen(this,saved);
		setScreen(titleScreen);
	}

	@Override
	public void dispose () {
		saveData();
	}

	/////////////////////////
	// GETTERS AND SETTERS //
	/////////////////////////

	public int getLives() {
		return lives;
	}

	public void setLives(int l) {
		lives=l;
	}

	public void setMusicVolume(float volume){
		menuMusic.setVolume(volume/100);
	}

	public float getMusicVolume(){
		return menuMusic.getVolume();
	}

	public int getNextTestNumber(){
		return nextTestNumber;
	}


}
