package com.salty.followthearrow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.play);

        Button instructions = findViewById(R.id.instructions);


        Intent intent = getIntent();
        String message = intent.getStringExtra(GamePlay.EXTRA_MESSAGE);


        if(message != null) {
            ((TextView) findViewById(R.id.score)).setText(message);
            findViewById(R.id.score).setVisibility(View.VISIBLE);
            findViewById(R.id.top).setVisibility(View.VISIBLE);

            setHigh(Integer.parseInt(message));
        }



        ((TextView) findViewById(R.id.score2)).setText(String.valueOf(getHigh()));
        
        instructions.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), instructions.class);
                startActivity(intent);
            }
        });

        play.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GamePlay.class);
                startActivity(intent);

                finish();
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }

    public void setHigh(int a) {
        if(a > getHigh()) {
            SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", a);
            editor.commit();
        }
    }

    public int getHigh() {
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int score = prefs.getInt("key", 0);

        return score;
    }

}
