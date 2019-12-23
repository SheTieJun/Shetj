package com.shetj.diyalbume.playVideo.media.notifications;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import androidx.media.session.MediaButtonReceiver;

public class MusicMediaButtonReceiver extends MediaButtonReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            super.onReceive(context, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}