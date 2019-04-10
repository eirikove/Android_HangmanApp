package eirik.hangmanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class HelpActivity extends AppCompatActivity {
    private TextView textView;
    int language;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);
        Intent intent = getIntent();
        language = intent.getIntExtra("language", 2);
        textView = (TextView) findViewById(R.id.textView);
        if(language == 2){
            textView.setText(R.string.engDescription);
        }else{
            textView.setText(R.string.norDescription);
        }
    }
}
