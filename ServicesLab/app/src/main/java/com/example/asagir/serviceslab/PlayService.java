package com.example.asagir.serviceslab;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by asagir on 3/2/16.
 */
public class PlayService extends Service {

    final MediaPlayer player = new MediaPlayer();
    boolean mPause;
    int mTime;

    public void run() {

        try {
            String url = "http://download.lisztonian.com/music/download/Clair+de+Lune-113.mp3";
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
        } catch (Throwable thr) {

        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        run();
        mTime = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPause = intent.getBooleanExtra("pause", false);

        if (mPause) {
            player.pause();
            mTime = player.getCurrentPosition();
        } else if (mTime != 0) {
            player.seekTo(mTime);
            player.start();
        } else {
            player.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.pause();

    }

}
