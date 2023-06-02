package testScreens;

import IO.ReadDropDown;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gameApp.Game;
import questionTypes.DropDownQuestion;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DropDown extends ScreenAdapter {

    /**
     * A class representing a quiz game. It contains the game object, the stage, table, score, current question,
     * a list of questions, a map of boxes, a boolean indicating whether the test has been passed, a skin, and a button
     * to move to the next question.
     *
     * @param game The game object
     * @param stage The stage to display the quiz on
     * @param table The table to display the quiz on
     * @param questions The list of questions in the quiz
     * @param boxMap The map of boxes in the quiz
     * @param skin The skin to use for the quiz
     * @param nextQuestion The button to move to the next question
     */
    private final Game game;

    private final Stage stage;
    private final Table table;

    private int score=0;
    private int currentQuestion=0;

    private final ArrayList<DropDownQuestion> questions;
    private final HashMap boxMap;
    private boolean testPassed=false;

    private final Skin skin;

    private final TextButton nextQuestion;


    /**
     * Creates a new DropDown object, which is a UI element that displays a series of questions
     * with multiple choice answers in a drop-down menu format.
     *
     * @param game The Game object that this DropDown is associated with
     * @param fileName The name of the file containing the questions and answers for this DropDown
     * @throws FileNotFoundException if the specified file cannot be found
     */
    public DropDown(Game game, String fileName) throws FileNotFoundException {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.game=game;

        table =new Table();

        //Load skin from files
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));

        questions= ReadDropDown.loadQuestions(fileName);

        boxMap=new HashMap<DropDownQuestion,ArrayList<SelectBox>>();

        nextQuestion=new TextButton("Siguiente pregunta",skin);

        updateQuestion();

    }

    /**
     * Updates the current question and its corresponding answer options in the UI.
     * This method is called when the user clicks the "Next" button to move on to the next question.
     */
    private void updateQuestion() {

        DropDownQuestion actualQuestion=questions.get(currentQuestion);

        table.clear();

        //Create labels
        Label titleLabel = new Label("Pregunta " + (currentQuestion + 1) + ": ", skin, "title");
        Label questionLabel = new Label(actualQuestion.getQuestion(), skin, "button");
        questionLabel.setWrap(true);
        questionLabel.setHeight(questionLabel.getPrefHeight());

        //add labels to title
        table.add(titleLabel).colspan(2).padBottom(25).row();
        table.add(questionLabel).colspan(2).width(800).padBottom(50).row();

        ArrayList<SelectBox<String>> boxes = new ArrayList<>(actualQuestion.getNumberBlanks());

        /**
         * If the current question is the last question in the list of questions, set the text of the nextQuestion button to "Terminar prueba".
         * 
         * @param currentQuestion The index of the current question being displayed.
         * @param questions The list of questions being displayed.
         */
        if (currentQuestion+1==questions.size()){
            nextQuestion.setText("Terminar prueba");
        }

        //To create the dropdown boxes
        for (int i=0;i<actualQuestion.getNumberBlanks();i++){
            Label boxLabel=new Label((i+1)+": ",skin,"button");
            SelectBox<String> box=new SelectBox<>(skin);
            box.setItems(actualQuestion.getOptions().toArray(new String[actualQuestion.getNumberOptions()]));
            boxes.add(box);
            table.add(boxLabel).left().width(50).height(50).padBottom(50);
            table.add(box).left().width(600).height(100).padBottom(50).row();
        }

        boxMap.put(actualQuestion, boxes);

        /**
         * Adds a click listener to the "next question" button. When clicked, the listener will either check the answers if the current question is the last one, or update the current question and display it.
         *
         * @param event The input event that triggered the click
         * @param x The x-coordinate of the click
         * @param y The y-coordinate of the click
         */
        nextQuestion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentQuestion+1==questions.size()){
                    checkAnswers();
                }else{
                    currentQuestion+=1;
                    //System.out.println(currentQuestion);
                    updateQuestion();
                }

            }
        });

        //Setup label
        table.padBottom(25);
        table.padTop(25);
        table.add(nextQuestion).right().colspan(2).width(200).height(50).row();
        table.pad(25);
        table.layout();

        //Creates scroll pane to add to the screen
        ScrollPane scroll = new ScrollPane(table, skin);
        scroll.setFillParent(true);
        scroll.layout();

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
     * Checks the answers of the quiz and updates the score and results accordingly.
     * If all questions are answered correctly, the test is considered passed.
     */
    private void checkAnswers() {
        ArrayList<Integer> results=new ArrayList<>(questions.size());

        /**
         * Calculates the score and results of a quiz based on the user's answers.
         *
         * @param questions The list of questions in the quiz.
         * @param boxMap The map of questions to their corresponding select boxes.
         * @return An ArrayList of integers representing the results of each question (1 for correct, 0 for incorrect).
         */
        for (int i=0;i<questions.size();i++){
            int questionPoints=0;
            ArrayList<String> correctAnswers=questions.get(i).getCorrectAnswers();
            ArrayList<SelectBox> boxes= (ArrayList<SelectBox>) boxMap.get(questions.get(i));

            /**
             * Iterates through a list of answer boxes and checks if the selected answer matches the correct answer.
             * If the selected answer is correct, the questionPoints variable is incremented by 1.
             */
            for (int j=0;j<boxes.size();j++){
                String selected=(String)boxes.get(j).getSelected();
                if (selected.equals(correctAnswers.get(j))){
                    questionPoints+=1;

                }
            }
            if (questionPoints==boxes.size()){
                score+=1;
                results.add(1);
            }else{
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
