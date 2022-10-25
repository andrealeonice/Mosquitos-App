package com.example.mosquitosapp;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText txt;
    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        music = MediaPlayer.create(this,
                R.raw.sound);
        txt = (EditText)findViewById(R.id.editTextNumber);

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
    public void freqSound(View v){
    final int duration = 10; // duration of sound in seconds
    final int sampleRate = 8000; // Hz (maximum frequency is 7902.13Hz (B8))
    final int numSamples = duration * sampleRate;
    final double samples[] = new double[numSamples];
    final short buffer[] = new short[numSamples];
    final double freq = Double.parseDouble(txt.getText().toString()); //Hz
    for (int i = 0; i < numSamples; ++i) {
        samples[i] = Math.sin(2 * Math.PI * i / (sampleRate / freq)); // Sine wave
        buffer[i] = (short) (samples[i] * Short.MAX_VALUE);  // Higher amplitude increases volume
    }

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, buffer.length, AudioTrack.MODE_STATIC);
        audioTrack.write(buffer, 0, buffer.length);
        audioTrack.play();


    }


}
