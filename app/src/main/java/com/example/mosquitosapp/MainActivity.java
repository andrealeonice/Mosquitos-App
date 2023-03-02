package com.example.mosquitosapp;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.rtp.AudioStream;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText txt, txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (EditText) findViewById(R.id.editTextNumber);
        txt2 = (EditText) findViewById(R.id.editTextNumber2);
        final Button bPlay = findViewById(R.id.button3);
        final Button bPause = findViewById(R.id.button);

    }
        private boolean b = false;
        public void frequency(View v) {
            int duration = Integer.parseInt(txt2.getText().toString()); // duration of sound in seconds
            int sampleRate = 44100;//44100; 12001// Hz (maximum frequency is 7902.13Hz (B8))
            int bufferSize = (sampleRate * 1 * 16 * duration) / 8;

                AudioTrack audioTrack  = new AudioTrack(AudioManager.STREAM_MUSIC,
                        sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
            if (!b) {
                double samples = 0;
                b=true;
                final short buffer[] = new short[bufferSize / 2];
                final double freq = Double.parseDouble(txt.getText().toString()); //Hz
                for (int i = 0; i < buffer.length; ++i) {
                    samples += 2 * Math.PI * freq / sampleRate; // Sine wave
                    buffer[i] = (short) (Math.sin(samples) * Short.MAX_VALUE);  // Higher amplitude increases volume
                }

                audioTrack.play(); // Inicia a reprodução
                audioTrack.write(buffer, 0, buffer.length); // Escreve o buffer de amostras no AudioTrack
            } else {
                b=false;
                audioTrack.stop();
                audioTrack.release();
            }
        }





    }

