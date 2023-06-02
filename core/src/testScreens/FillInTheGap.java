package testScreens;

import IO.ReadFillInTheGap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gameApp.Game;
import questionTypes.FillInTheGapQuestion;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class FillInTheGap extends ScreenAdapter {

    /**
     * A class representing a fill-in-the-gap quiz game. This class contains the game logic and UI elements.
     *
     * @param game The game instance that this quiz game is a part of.
     * @param stage The stage that the UI elements of this quiz game will be added to.
     * @param skin The skin to use for the UI elements.
     * @param questions The list of fill-in-the-gap questions to use in the quiz game.
     * @param fieldMap A map of the text fields used in the quiz game to their corresponding answers.
     * @param nextQuestion The button used to move on to the next question.
     */
    private final Game game;

    private final Stage stage;
    private final Table table;

    private int score=0;
    private int currentQuestion=0;

    private final ArrayList<FillInTheGapQuestion> questions;
    private final HashMap fieldMap;
    private boolean testPassed=false;

    private final Skin skin;

    private final TextButton nextQuestion;

    /**
     * Constructs a new FillInTheGap object, which is a game mode where the player fills in the missing words in a sentence.
     *
     * @param game The game object that this mode is a part of.
     * @param fileName The name of the file containing the questions for this game mode.
     * @throws FileNotFoundException if the file containing the questions cannot be found.
     */
    public FillInTheGap(Game game, String fileName) throws FileNotFoundException {

        this.game=game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    
        table =new Table();
    
        //Load skin from files
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));

        questions= ReadFillInTheGap.loadQuestions(fileName);
        fieldMap=new HashMap<FillInTheGapQuestion,ArrayList<TextField>>();
    
        nextQuestion=new TextButton("Siguiente pregunta",skin);

        updateQuestion();
    }

    /**
     * Updates the current question in the UI by clearing the table and adding the new question and answer fields.
     * Also sets up the "next question" button to either check answers or move to the next question.
     */
    private void updateQuestion() {

        FillInTheGapQuestion actualQuestion=questions.get(currentQuestion);

        table.clear();

        //Create labels
        Label titleLabel = new Label("Pregunta " + (currentQuestion + 1) + ": ", skin, "title");
        Label questionLabel = new Label(actualQuestion.getQuestion(), skin, "button");
        questionLabel.setWrap(true);
        questionLabel.setHeight(questionLabel.getPrefHeight());

        //add labels to title
        table.add(titleLabel).colspan(2).padBottom(25).row();
        table.add(questionLabel).colspan(2).width(800).padBottom(50).row();

        ArrayList<TextField> fields = new ArrayList<>(actualQuestion.getNumberBlanks());

        //To create the textfields
        for (int i=0;i<actualQuestion.getNumberBlanks();i++){
            Label label=new Label((i+1)+": ",skin,"button");
            TextField field=new TextField("Escribe aqui tu respuesta",skin);
            fields.add(field);
            table.add(label).left().width(50).height(50).padBottom(50);
            table.add(field).left().width(600).height(100).padBottom(50).row();
        }
        fieldMap.put(actualQuestion, fields);

        /**
         * Sets the text of the "next question" button to "Terminar prueba" if the current question is the last one.
         */
        if (currentQuestion+1==questions.size()){
            nextQuestion.setText("Terminar prueba");
        }

        /**
         * Adds a click listener to the "next question" button. If the current question is the last one,
         * the method checkAnswers() is called. Otherwise, the current question index is incremented and
         * the updateQuestion() method is called.
         */
        nextQuestion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentQuestion+1==questions.size()){
                    checkAnswers();
                }else{
                    currentQuestion+=1;
                    updateQuestion();
                }

            }
        });

        
        table.add(nextQuestion).right().colspan(2).width(200).height(50).row();
        table.pad(25);

        //Creates scroll pane to add to the screen
        ScrollPane scroll = new ScrollPane(table, skin);
        scroll.setFillParent(true);

        stage.addActor(scroll);

    }
    
    /**
     * Shows the screen and sets the input processor to the stage.
     */
    @Override
    public void show() {

        updateQuestion();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Checks the answers of the fill-in-the-gap questions and updates the score and results accordingly.
     * If all questions are answered correctly, the test is considered passed.
     */
    private void checkAnswers() {

        ArrayList<Integer> results=new ArrayList<>(questions.size());

        /**
         * Calculates the score and results for a list of fill-in-the-gap questions.
         *
         * @param questions The list of fill-in-the-gap questions to be scored.
         * @return An ArrayList of integers representing the results of each question (1 for correct, 0 for incorrect).
         */
        for (FillInTheGapQuestion question : questions) {

            int questionPoints = 0;
            ArrayList<String> correctAnswers = question.getCorrectAnswers();
            ArrayList<TextField> fields = (ArrayList<TextField>) fieldMap.get(question);

            for (int j = 0; j < fields.size(); j++) {

                String input = fields.get(j).getText();

                if (input.equals(correctAnswers.get(j))) {
                    questionPoints += 1;
                }
            }

            if (questionPoints == fields.size()) {
                score += 1;
                results.add(1);
            } else {
                results.add(0);
            }
        }

        if (score==questions.size()){
            testPassed=true;
        }

        game.endTest(testPassed,results);
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
