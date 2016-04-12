package com.amazing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Intent nextScreen;
    public static Typeface customf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        getFont();
        editText();

        createMenu();
        addHighScore();

        versionBottom();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static Typeface getTypeFace() {
        return MainActivity.customf;
    }

    public void getFont() {
        String pathToFont;
        pathToFont = "fonts/slkscreb.ttf";
        try {
            MainActivity.customf = Typeface.createFromAsset(getAssets(), pathToFont);
        } catch (Exception e) {

        }

    }

    private void editText() {
        TextView title = (TextView) findViewById(R.id.text);

        if (customf != null) {
            title.setTypeface(customf);
        }
        title.setTextColor(Color.WHITE);
        title.setTextSize(40f);

    }

    private void versionBottom() {
        TextView versio = (TextView) findViewById(R.id.version);
        versio.setTextColor(Color.WHITE);
        if (customf != null) {
            versio.setTypeface(customf);
        }

    }

    private void addHighScore() {
        TextView high = (TextView) findViewById(R.id.highScore);

        if (customf != null) {
            high.setTypeface(customf);
        }
        high.setTextColor(Color.WHITE);
        int score = this.getSavedHighScore();
        if (score == 0) {
            high.setText("Never played");
        } else {
            high.setText("Best score : " + score);
        }
        //title.setTextSize(40f);
    }

    private void createMenu() {

        LinearLayout menuFrame = (LinearLayout) this.findViewById(R.id.relativeMain);
        menuFrame.setBackgroundColor(Color.LTGRAY);

        Button btnNextScreen = (Button) findViewById(R.id.button);
        btnNextScreen.setBackgroundColor(Color.GRAY);
        btnNextScreen.setTypeface(customf);
        btnNextScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                MainActivity.this.nextScreen = new Intent(getApplicationContext(), RunActivity.class);
                startActivity(nextScreen);

            }
        });
        /*
         Button btnfunct1 = (Button) findViewById(R.id.button1);
        
         btnfunct1.setBackgroundColor(Color.GRAY);
         btnfunct1.setTypeface(customf);
         btnfunct1.setOnClickListener(new View.OnClickListener() {

         public void onClick(View arg0) {

         MainActivity.this.nextScreen = new Intent(getApplicationContext(), ScoresActivity.class);
         startActivity(nextScreen);

         }
         });
         */
        Button btnfunct2 = (Button) findViewById(R.id.button2);
        btnfunct2.setBackgroundColor(Color.GRAY);
        btnfunct2.setTypeface(customf);
        Button btnExit = (Button) findViewById(R.id.exit);
        btnExit.setBackgroundColor(Color.GRAY);
        btnExit.setTypeface(customf);
        btnExit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                MainActivity.this.finish();

            }
        });
    }

    private int getSavedHighScore() {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.saved_high_score), Context.MODE_PRIVATE);
        int defaultValue = 0;
        int highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
        return highScore;
    }

}
