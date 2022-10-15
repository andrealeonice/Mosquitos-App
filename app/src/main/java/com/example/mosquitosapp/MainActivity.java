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
    Boolean b = false;

      public void playMusic(View v) {
          if (!b) {
              music.start();
              b=true;
          } else {
              music.pause();
              b=false;
          }
      }

        }
