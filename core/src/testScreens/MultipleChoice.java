package testScreens;

import IO.ReadMultipleChoice;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gameApp.Game;
import questionTypes.MultipleChoiceQuestion;

import java.util.ArrayList;

public class MultipleChoice extends ScreenAdapter {

    /**
     * A class representing a quiz game. The game consists of a series of multiple choice questions.
     * The user is presented with a question and four possible answers. The user must select one of the
     * answers and click the "Next" button to proceed to the next question. The game ends when all
     * questions have been answered.
     *
     * @param game The game object
     * @param stage The stage object
     * @param skin The skin object
     * @param questions An ArrayList of MultipleChoiceQuestion objects representing the questions in the game
     * @param answers An ArrayList of Strings representing the correct answers to the questions
     */
    private final Game game;

    private final Stage stage;
    private final Table table;

    private int score=0;
    private int currentQuestion=0;

    private final ArrayList<MultipleChoiceQuestion> questions;
    private final ArrayList<String> answers;

    private final Skin skin;

    private Label questionLabel;
    private CheckBox optionA, optionB, optionC, optionD;
    private final TextButton nextQuestion;


    public MultipleChoice(Game game, String fileName){

        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table =new Table();
        table.setFillParent(true);

        //Load skin from files
        skin = new Skin(Gdx.files.internal("Skin/flat-earth-ui.json"));

        questions= ReadMultipleChoice.loadQuestions(fileName);
        answers=new ArrayList<>(questions.size());

        updateQuestion();

        nextQuestion=new TextButton("Siguiente pregunta",skin);

    }

    private void updateQuestion() {

        MultipleChoiceQuestion actualQuestion=questions.get(currentQuestion);

        //Create labels and checkbox
        questionLabel = new Label("Pregunta "+(currentQuestion+1)+": "+actualQuestion.getQuestion(), skin,"title");
        optionA = new CheckBox(actualQuestion.getOptionA(), skin,"radio");
        optionB = new CheckBox(actualQuestion.getOptionB(), skin,"radio");
        optionC = new CheckBox(actualQuestion.getOptionC(), skin,"radio");
        optionD = new CheckBox(actualQuestion.getOptionD(), skin,"radio");

        //Create button group with answers
        ButtonGroup group = new ButtonGroup<>(optionA, optionB, optionC, optionD);
        group.setMaxCheckCount(1);
        group.setMinCheckCount(1);
        optionA.setChecked(true);

        questionLabel.setWrap(true);
        questionLabel.setHeight(questionLabel.getPrefHeight());

        answers.add(currentQuestion,"a");

    }

    /**
     * Shows the screen and sets the input processor to the stage.
     */
    @Override
    public void show() {

        displayQuestion();

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Checks the answers of the questions and calculates the score.
     * If the score is equal to the number of questions, the test is considered passed.
     * The results are then passed to the game object.
     */
    private void checkAnswers() {
        ArrayList<Integer> results=new ArrayList<>(questions.size());
       
        /**
         * Iterates through a list of questions and answers, comparing each answer to the correct answer
         * for the corresponding question. Keeps track of the score and adds the result to a list.
         *
         * @param questions A list of Question objects
         * @param answers A list of String objects representing the user's answers to the questions
         * @return A list of integers representing the results of each question (1 for correct, 0 for incorrect)
         */
        for (int i=0;i<questions.size();i++){
            String answer=answers.get(i);
            String correctAnswer= String.valueOf(questions.get(i).getCorrectAnswer().charAt(0));
            if (answer.equals(correctAnswer)){
                score+=1;
                results.add(1);
                System.out.println(i+"/"+score);
            }else{
                results.add(0);
            }
        }
        boolean testPassed;
        
        if (score==questions.size()){
            testPassed =true;
            game.endTest(testPassed,results);
        }else{
            testPassed =false;
            game.endTest(testPassed,results);
        }
    }

    /**
     * Displays the current question and its options on the screen, and sets up listeners for the options and the "next question" button.
     * If the current question is the last one, the "next question" button will be labeled "Terminar prueba".
     */
    private void displayQuestion() {
        /**
         * Adds a ChangeListener to optionA, which listens for changes in its checked state.
         * If optionA is checked, the answer for the current question is set to "a" and "A" is printed to the console.
         */
        optionA.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if(optionA.isChecked()){
                    answers.set(currentQuestion,"a");
                    System.out.println("A");
                }
            }
        });


        /**
         * Adds a ChangeListener to the optionB button. When the button is checked, the answer for the current question
         * is set to "b" and "B" is printed to the console.
         */
        optionB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if(optionB.isChecked()){
                    answers.set(currentQuestion,"b");
                    System.out.println("B");
                }
            }
        });

        /**
         * Adds a ChangeListener to the optionC button. When the button is checked, the answer for the current question is set to "c".
         */
        optionC.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if(optionC.isChecked()){
                    answers.set(currentQuestion,"c");
                    System.out.println("C");
                }

            }
        });


        /**
         * Adds a ChangeListener to the optionD button. When the button is checked, the answer for the current question is set to "d" and printed to the console.
         */
        optionD.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if(optionD.isChecked()){
                    answers.set(currentQuestion,"d");
                    System.out.println("D");
                }

            }
        });

        /**
         * Adds a click listener to the "next question" button. If the current question is the last one,
         * it will check the answers. Otherwise, it will update the current question and display it.
         */
        nextQuestion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentQuestion+1==questions.size()){
                    checkAnswers();
                }else{
                    currentQuestion+=1;
                    System.out.println(currentQuestion);
                    updateQuestion();
                    displayQuestion();
                }

            }
        });

        table.clear();

        //Adds actors to table
        table.add(questionLabel).width(800).padBottom(50).row();
        table.add(optionA).left().width(800).height(50).padBottom(20).row();
        table.add(optionB).left().width(800).height(50).padBottom(20).row();
        table.add(optionC).left().width(800).height(50).padBottom(20).row();
        table.add(optionD).left().width(800).height(50).padBottom(20).row();

        //Checks if its the last question and updates nextQuestion button
        if (currentQuestion+1==questions.size()){
            nextQuestion.setText("Terminar prueba");
        }

        table.add(nextQuestion).right().width(200).height(50).padTop(20).row();

        stage.addActor(table);
        table.align(Align.center);
        table.pad(50);
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
