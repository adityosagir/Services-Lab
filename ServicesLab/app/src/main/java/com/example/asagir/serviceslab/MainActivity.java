package com.example.asagir.serviceslab;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mFabPlay;
    FloatingActionButton mFabPause;
    FloatingActionButton mFabStop;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.textView);
        mFabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        mFabPause = (FloatingActionButton) findViewById(R.id.fabPause);
        mFabStop = (FloatingActionButton) findViewById(R.id.fabStop);


        mFabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playIntent = new Intent(MainActivity.this, PlayService.class);
                playIntent.setAction("play");
                startService(playIntent);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                builder.setSmallIcon(android.R.drawable.ic_media_play);
                builder.setContentTitle("Clair de Lune");
                builder.setContentText("Alexis Weissenberg");

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, (int) System.currentTimeMillis(), intent, 0);

                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);

                builder.addAction(R.drawable.ic_stop_24dp, "stop", pendingIntent);
                builder.addAction(android.R.drawable.ic_media_play, "play", pendingIntent);

                Notification notification = builder.build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(1, notification);
            }
        });

        mFabStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(MainActivity.this, PlayService.class);
                stopService(stopIntent);
            }
        });

        mFabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pauseIntent = new Intent(MainActivity.this, PlayService.class);
                pauseIntent.putExtra("pause", true);
                startService(pauseIntent);
            }
        });

    }

}
