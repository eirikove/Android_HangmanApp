package eirik.hangmanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button newGameBtn;
    private Button helpBtn;
    private Button languageBtn;
    private Button exitBtn;
    private int language = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameBtn = (Button)findViewById(R.id.newGameButton);
        helpBtn = (Button)findViewById(R.id.helpButton);
        languageBtn = (Button)findViewById(R.id.languageButton);
        exitBtn = (Button)findViewById(R.id.exitButton);
    }

    public void newGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("language", language);
        this.startActivity(intent);
    }

    public void help(View view){
        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra("language", language);
        this.startActivity(intent);
    }

    // Changes the language integer between 1 and 2 to determine which language to use for both UI and gameplay,
    // then changes the text on each button
    public void changeLanguage(View v){
        if (language == 2){
            language = 1;
        }else if(language == 1){
            language = 2;
        }
        changeText();
    }

    // Changes the text on the buttons according to what the language integer is
    private void changeText(){
        if (language == 2){
            newGameBtn.setText(R.string.new_game);
            helpBtn.setText(R.string.help);
            languageBtn.setText(R.string.change_language);
            exitBtn.setText(R.string.exit_app);
        }else if(language == 1){
            newGameBtn.setText(R.string.nytt_spill);
            helpBtn.setText(R.string.hjelp);
            languageBtn.setText(R.string.bytt_språk);
            exitBtn.setText(R.string.avslutt);
        }
    }

    // Exits the app completely (instead of just returning to home screen)
    public void exitApp(View v){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
