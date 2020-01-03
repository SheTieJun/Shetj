package com.shetj.diyalbume.broadcastReceiver;

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import timber.log.Timber

/**
 * 蓝牙、耳机等断开拔掉需要关闭音乐
 */
class NOISYStateBroadcastReceive : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                //连接
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                //断开
                pauseMusicAndVideo()
            }
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)) {
                    BluetoothAdapter.STATE_OFF -> {
                        //关闭
                    }
                    BluetoothAdapter.STATE_ON -> {
                        //打开
                    }
                }
            }
            AudioManager.ACTION_AUDIO_BECOMING_NOISY-> {
                Timber.i( "ACTION_AUDIO_BECOMING_NOISY")
                pauseMusicAndVideo()
            }

        }
    }

    fun pauseMusicAndVideo() {
        if (GSYVideoManager.instance().isPlaying) {
            GSYVideoManager.instance().pause()
        }
    }

}