package eirik.hangmanapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    int language;
    String currentWord = "";
    TypedArray imgs;
    private int guesses = 7;
    private int wrongCounter;
    private int correctCounter;
    private int wordLength;
    private ImageView imgView;
    Resources res;
    // private String[] words;
    private List<String> words;
    private ArrayList<String> usedWords = new ArrayList<>();
    private Random rand;
    private TextView[] charViews;
    private LinearLayout wordLayout;
    private LetterAdapter ltrAdapt;
    private GridView letters;
    Drawable drawable;
    private int round;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        res = getResources();
        Intent intent = getIntent();
        language = intent.getIntExtra("language", 2); //Decides which game language to use based on the main activity's language selection;
        getWords(language);
        imgs = getResources().obtainTypedArray(R.array.imgs);
        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageDrawable(imgs.getDrawable(0));
        wordLayout = (LinearLayout)findViewById(R.id.word);
        letters = (GridView)findViewById(R.id.letters);
        rand = new Random();
        round = 1;
        playGame();
        }

        private void getWords(int language){
            if(language == 1){
                words = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.norWords)));
            }else{
                words = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.engWords)));
            }
        }

        public void playGame(){
            imgView.setImageDrawable(imgs.getDrawable(0));
        // Word selection
            boolean exit = false;
            boolean wordTaken = false;
            int r = rand.nextInt(words.size());
            String newWord = "";
            newWord = words.get(r);
            currentWord = newWord;
            words.remove(r);

            /*while(!exit) {
                newWord = words.get(rand.nextInt(words.size()));
                for (int i = 0; i < usedWords.size(); i++) {
                    if (newWord.equals(usedWords.get(i))) {
                        wordTaken = true;
                        break;
                    }
                }
                if (!wordTaken) {
                    usedWords.add(newWord);
                    exit = true;
                }
            }*/


            // End of word selection HERE

            charViews = new TextView[newWord.length()];

            wordLayout.removeAllViews();
            for(int c=0; c < currentWord.length(); c++) {
                charViews[c] = new TextView(this);
                //set the current letter
                charViews[c].setText("" + currentWord.charAt(c));
                //set layout
                charViews[c].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                charViews[c].setGravity(Gravity.CENTER);
                charViews[c].setTextColor(Color.WHITE);
                charViews[c].setBackgroundResource(R.drawable.letter_bg);
                //add to display
                wordLayout.addView(charViews[c]);
            }


            //reset adapter
            ltrAdapt=new LetterAdapter(this, language);
            letters.setAdapter(ltrAdapt);

            //start part at zero
            wrongCounter = 0;
            //set word length and correct choices
            wordLength=currentWord.length();
            correctCounter=0;
    }

    //letter pressed method
    public void letterPressed(View view){
        //find out which letter was pressed
        String ltr=((TextView)view).getText().toString();
        char letterChar = ltr.charAt(0);
        //disable view
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);
        //check if correct
        boolean correct=false;
        for(int k=0; k<currentWord.length(); k++){
            if(currentWord.charAt(k)==letterChar){
                correct=true;
                correctCounter++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }
        //check in case won


        if(correct){
            if(correctCounter==wordLength) {
                //disable all buttons
                disableBtns();
                //let user know they have won, ask if they want to play again
                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
                if (round == 10) {
                    winBuild.setTitle("YOU MADE IT");
                    winBuild.setMessage("The answer was:\n\n" + currentWord + "\n\n You completed all the words!");
                    winBuild.setPositiveButton("Play Again",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getWords(language);
                                    round = 1;
                                    GameActivity.this.playGame();
                                }
                            });
                    winBuild.setNegativeButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    GameActivity.this.finish();
                                }
                            });
                    winBuild.show();
                } else{
                    winBuild.setTitle("Round " + round + "/10 completed!");
                    winBuild.setMessage("You win!\n\nThe answer was:\n\n" + currentWord);
                    winBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GameActivity.this.playGame();
                                round++;
                            }
                        });
                    winBuild.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GameActivity.this.finish();
                            }
                        });
                    winBuild.show();
                    }
                }
            }
        //check if user still has guesses
        else if(wrongCounter<guesses){
            //show next part
            imgView.setImageDrawable(imgs.getDrawable(wrongCounter));
            wrongCounter++;
        }
        else{
            //user has lost
            disableBtns();
            //let the user know they lost, ask if they want to play again
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("OOPS");
            loseBuild.setMessage("You lose!\n\nThe answer was:\n\n"+currentWord);
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GameActivity.this.playGame();
                            round = 1;
                        }});
            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            GameActivity.this.finish();
                        }});
            loseBuild.show();
        }
    }

    //disable letter buttons
    public void disableBtns(){
        int numLetters = letters.getChildCount();
        for(int l=0; l<numLetters; l++){
            letters.getChildAt(l).setEnabled(false);
        }
    }


}

