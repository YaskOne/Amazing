package com.amazing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazing.core.GameRuntime;

public class RunActivity extends Activity {

    private Board mainContext;
    private GameRuntime threading;
    private Thread execution;

    private LinearLayout menuPause;
    private FrameLayout topLeft;
    private LinearLayout finalMenu;

    private Typeface custom_font;

    private TextView scoreaff;
    private TextView scoreFinal;
    public static final String filename = "highscore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.board);

        mainContext = (Board) this.findViewById(R.id.surface);
        createUi();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.mainContext != null) {

            startGame();

        }
    }

    public void startGame() {
        threading = new GameRuntime(mainContext);

        threading.setAff(new LayAwayInfo());

        mainContext.setGame(threading);

        if (execution == null) {
            execution = new Thread(threading);
            execution.start();

        }
        this.updateScore(0);
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
        RunActivity.this.threading.setOnPause(true);
        togglePauseMenu();
        hideTopBar();
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

    public void togglePauseMenu() {

        RunActivity.this.menuPause.setVisibility(FrameLayout.VISIBLE);

    }

    public void hidePauseMenu() {

        RunActivity.this.menuPause.setVisibility(FrameLayout.INVISIBLE);

    }

    public void toggleTopBar() {
        this.topLeft.setVisibility(FrameLayout.VISIBLE);
    }

    public void hideTopBar() {
        this.topLeft.setVisibility(FrameLayout.INVISIBLE);
    }

    /**
     * @return the execution
     */
    public Thread getExecution() {
        return execution;
    }

    class LayAwayInfo implements InfoUpdater {

        private int current;

        public void scoreUpdate(int score) {
            if (current != score) {
                this.current = score;
                RunActivity.this.updateScore(score);
            }
        }

        public void gameOver() {
            RunActivity.this.toggleFinalScoreMenu();
        }

    }

    public void updateScore(int score) {
        String scoreFormat = new String(" " + score);
        while (scoreFormat.length() < 6) {
            scoreFormat = scoreFormat.concat(" ");
        }
        try {
            this.scoreaff.setText(scoreFormat);
        } catch (Exception ex) {

        }
    }

    private void createUi() {
        if (this.custom_font == null) {
            getFont();
        }

        findLayout();
        initScoreText();
        createPauseButton();
        createPauseMenu();
        createFinalScoreMenu();

    }

    public void getFont() {
        String pathToFont;
        pathToFont = "fonts/slkscre.ttf";
        try {
            this.custom_font = Typeface.createFromAsset(getAssets(), pathToFont);
        } catch (Exception e) {

        }

    }

    private void findLayout() {

        this.topLeft = (FrameLayout) this.findViewById(R.id.top);
        menuPause = (LinearLayout) this.findViewById(R.id.pauseMenu);

        finalMenu = (LinearLayout) this.findViewById(R.id.finalMenu);

        //menuPause.setBackgroundColor(Color.LTGRAY);
    }

    private void initScoreText() {

        this.scoreaff = (TextView) findViewById(R.id.score);

        this.scoreaff.setTypeface(custom_font);
        this.scoreaff.setTextColor(Color.BLACK);
        this.scoreaff.setTextSize(30f);

    }

    private void createPauseButton() {
        Button pause = (Button) this.findViewById(R.id.buttPause);
        //pause.setBackgroundColor(Color.GRAY);
        pause.setTextColor(Color.WHITE);
        //pause.setTypeface(custom_font);
        pause.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                RunActivity.this.threading.setOnPause(true);
                togglePauseMenu();
                hideTopBar();
            }

        });
    }

    private void createPauseMenu() {
        Button resume = (Button) this.findViewById(R.id.resume);
        //resume.setBackgroundColor(Color.GRAY);
        resume.setTextColor(Color.WHITE);
        resume.setTypeface(custom_font);
        resume.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                RunActivity.this.threading.setOnPause(false);
                hidePauseMenu();
                toggleTopBar();
            }

        });
        Button opt = (Button) this.findViewById(R.id.options);
        //opt.setBackgroundColor(Color.GRAY);
        opt.setTextColor(Color.WHITE);
        opt.setTypeface(custom_font);
        opt.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

            }

        });
        Button surrendr = (Button) this.findViewById(R.id.surrender);
        //surrendr.setBackgroundColor(Color.GRAY);
        surrendr.setTextColor(Color.WHITE);
        surrendr.setTypeface(custom_font);

        surrendr.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                RunActivity.this.finish();
            }

        });
    }

    public void hideFinalScoreMenu() {
        this.finalMenu.setVisibility(FrameLayout.INVISIBLE);
    }

    public void toggleFinalScoreMenu() {
        saveScore(this.threading.getScore());
        runOnUiThread(new Runnable() {
            public void run() {
                RunActivity.this.hidePauseMenu();
                RunActivity.this.hideTopBar();
                RunActivity.this.finalMenu.setVisibility(FrameLayout.VISIBLE);
                try {
                    RunActivity.this.scoreFinal.setText("score  "+RunActivity.this.threading.getScore());
                } catch (Exception ex) {

                }
            }
        });

    }

    private void createFinalScoreMenu() {
        this.scoreFinal = (TextView) findViewById(R.id.scoreFinal);

        this.scoreFinal.setTypeface(custom_font);
        this.scoreFinal.setTextColor(Color.WHITE);
        this.scoreFinal.setTextSize(30f);

        TextView gameOver = (TextView) findViewById(R.id.gameOver);

        gameOver.setTypeface(custom_font);
        gameOver.setTextColor(Color.WHITE);
        gameOver.setTextSize(20f);

        Button backMenu = (Button) this.findViewById(R.id.buttBack);
        //backMenu.setBackgroundColor(Color.GRAY);
        //backMenu.setTypeface(custom_font);
        backMenu.setTextColor(Color.WHITE);
        backMenu.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                RunActivity.this.finish();
            }

        });
        Button retry = (Button) this.findViewById(R.id.buttRetry);
        //retry.setBackgroundColor(Color.GRAY);
        //retry.setTypeface(custom_font);
        retry.setTextColor(Color.WHITE);
        retry.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                RunActivity.this.finish();
                RunActivity.this.startActivity(RunActivity.this.getIntent());
            }

        });

    }

    private void saveScore(int newHighScore) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.saved_high_score), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int defaultValue = 0;
        int highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
        if (newHighScore > highScore) {
            editor.putInt(getString(R.string.saved_high_score), newHighScore);
        }
        /*
         editor.putInt(getString(R.string.previous_score), newHighScore);
         */

        editor.commit();
    }

}
