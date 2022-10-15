package com.example.mosquitosapp;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        music = MediaPlayer.create(this,
                R.raw.sound);

    }

      public void playMusic(View v) {

           music.start();

        }

      public void pauseMusic(View v) {
            music.pause();
        }



}