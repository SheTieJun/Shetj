package com.shetj.diyalbume.pipiti.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 播放pcm 文件
 */
public class AudioTrackManager {
    private static final String TAG = AudioTrack.class.getSimpleName();
    private  String PATH ;
    private static AudioTrackManager instance;
    private AudioTrack mAudioTrack;
    private int bufferSize;
    private AudioManager mAudioManager;
    private FileInputStream fileInputStream;
    private boolean startPlay = false;
    private AudioTrackManager() {
    }
    public static AudioTrackManager getInstance() {
        if (instance == null) {
            instance = new AudioTrackManager();
        }
        return instance;
    }

    public void setContext(Context context,String path) {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.PATH = path;
        Log.i(TAG, "setContext: path = " +path);
        init();
    }

    private void init() {
        int audioFormatEncode = AudioFormat.ENCODING_PCM_16BIT;
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        int mSampleRate = 44100;

        bufferSize = AudioTrack.getMinBufferSize(mSampleRate, channelConfig, audioFormatEncode);
        int sessionId = mAudioManager.generateAudioSessionId();
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        AudioFormat audioFormat = new AudioFormat.Builder()
                .setSampleRate(mSampleRate)
                .setEncoding(audioFormatEncode)
                .setChannelMask(channelConfig)
                .build();
        mAudioTrack = new AudioTrack(audioAttributes, audioFormat, bufferSize * 2, AudioTrack.MODE_STREAM, sessionId);
        try {
            fileInputStream = new FileInputStream(PATH);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "init: " + e);
        }
    }

    public boolean isStartPlay() {
        return startPlay;
    }

    public void startThread() {
        startPlay = true;
        new PlayThread().start();
    }

    public void stopThread() {
        startPlay = false;
        mAudioTrack.stop();
        mAudioTrack.release();
    }

    class PlayThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (startPlay) {
                byte[] buffer = new byte[bufferSize];
                mAudioTrack.play();
                try {
                    while (fileInputStream.read(buffer) > 0 && startPlay) {
                        mAudioTrack.write(buffer, 0, buffer.length);
                    }
                    stopThread();
                } catch (IOException e) {
                    Log.e(TAG, "IOException " + e);
                }
            }
        }
    }

}