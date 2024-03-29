package com.example.mosquitosapp;

import android.content.DialogInterface;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    EditText txt, txt2;
    AudioTrack audioTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (EditText) findViewById(R.id.editTextFreq);
        txt2 = (EditText) findViewById(R.id.editTextTime);
        final Button bPause = findViewById(R.id.button);

        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioTrack != null && audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                    audioTrack.stop();
                }
            }
        });
    }
    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erro");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void frequency(View v) {
        int sampleRate = 44100;
        int duration;
        double freq;


        try {
            duration = Integer.parseInt(txt2.getText().toString()); // duration of sound in seconds
            freq = Double.parseDouble(txt.getText().toString());
        } catch (NumberFormatException e) {
            showErrorDialog("Por favor, insira valores válidos para frequência e duração.");
            return;
        }

        int bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);

        final short[] buffer = new short[bufferSize / 2];

        // Inicia a reprodução
        if (audioTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING) {
            audioTrack.play();
        }

        Thread audioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int totalSamples = sampleRate * duration;
                int samplesWritten = 0;
                double samples = 0;

                while (samplesWritten < totalSamples) {
                    for (int i = 0; i < buffer.length; ++i) {
                        samples += 2 * Math.PI * freq / sampleRate; // Sine wave
                        buffer[i] = (short) (Math.sin(samples) * Short.MAX_VALUE);  // Higher amplitude increases volume
                    }

                    audioTrack.write(buffer, 0, buffer.length);
                    samplesWritten += buffer.length;
                }

                audioTrack.stop();
                audioTrack.release();
            }
        });

        audioThread.start();
    }
    }



