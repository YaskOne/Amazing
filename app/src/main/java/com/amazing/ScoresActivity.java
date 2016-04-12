package com.amazing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import static com.amazing.RunActivity.filename;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScoresActivity extends Activity {

    private Typeface customf;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scores);

        getFont();

        createText();
        createList();
        createButton();

    }
    /*
     @Override
     protected void onStart() {

     }

     @Override
     protected void onRestart() {

     }

     @Override
     protected void onResume() {

     }

     @Override
     protected void onPause() {

     }

     @Override
     protected void onStop() {

     }

     @Override
     protected void onDestroy() {

     }
     */

    public void getFont() {
        String pathToFont;
        pathToFont = "fonts/slkscreb.ttf";
        try {
            this.customf = Typeface.createFromAsset(getAssets(), pathToFont);
        } catch (Exception e) {

        }

    }

    private void createText() {
        TextView title = (TextView) findViewById(R.id.scoretitle);

        if (customf != null) {
            title.setTypeface(customf);
        }
        title.setTextColor(Color.WHITE);
        title.setTextSize(40f);
    }

    private void createList() {
        String [] scores = new String[]{"12445","134"};
        if (scores == null) {
            TextView empty = (TextView) findViewById(R.id.noScore);

            if (customf != null) {
                empty.setTypeface(customf);
            }
            empty.setTextColor(Color.WHITE);
            empty.setVisibility(TextView.VISIBLE);
        } else {
            final ListView listview = (ListView) findViewById(R.id.listScore);
            

            final ArrayAdapter adapter = new MySimpleArrayAdapter(this, scores);
            listview.setAdapter(adapter);

        }
    }

    public String[] readScores() {
        
        File file = new File(this.getFilesDir(), RunActivity.filename);
        
        if (file.exists()) {
            ArrayList<String> listed=new ArrayList<String>();
            FileInputStream inputStream;

            try {
                inputStream = this.openFileInput(RunActivity.filename);
                InputStreamReader intputStreamReader = new InputStreamReader(inputStream);
                String a;
                BufferedReader bufferedReader = new BufferedReader(intputStreamReader);
                
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                //do{
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                listed.add(stringBuilder.toString());
                //}while(receiveString!=null)
                listed.add(stringBuilder.toString());
                
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String [] ret = (String []) listed.toArray();
            
            return ret;
        } else {
            return null;
        }
    }
    public void saveScore(int score) {
        File file = new File(this.getFilesDir(), filename);

        String string = "" + score + "\n";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MySimpleArrayAdapter extends ArrayAdapter<String> {

        private final Context context;
        private final String[] values;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, R.layout.listrow, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listrow, parent, false);
            TextView rowIndex = (TextView) rowView.findViewById(R.id.scoreIndex);
            TextView rowValue = (TextView) rowView.findViewById(R.id.scoreValue);
            rowIndex.setTextColor(Color.WHITE);
            rowValue.setTextColor(Color.WHITE);
            rowIndex.setTypeface(customf);
            rowValue.setTypeface(customf);
            rowIndex.setText((position + 1) + ".");
            rowValue.setText(values[position]);

            return rowView;
        }
    }

    private void createButton() {
        Button back = (Button) findViewById(R.id.back);
        back.setBackgroundColor(Color.GRAY);
        back.setTypeface(customf);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                ScoresActivity.this.finish();
            }
        });
    }

}
