package me.shetj.audiomix.playaudio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import me.shetj.audiomix.R;
import me.shetj.audiomix.playaudio.audio.AudioEncoder;
import me.shetj.audiomix.playaudio.audio.PlayBackMusic;
import me.shetj.audiomix.playaudio.media.MediaMixAudio;
import me.shetj.audiomix.playaudio.permission.PermissionsManager;
import me.shetj.audiomix.playaudio.permission.PermissionsResultAction;
import me.shetj.audiomix.playaudio.util.BytesTransUtil;
import me.shetj.audiomix.playaudio.util.FileUtil;
import me.shetj.audiomix.playaudio.video.VideoDecoder;
import me.shetj.audiomix.playaudio.video.VideoEncodeDecode;


/**
 * @author slack
 * @time 17/2/6 下午1:47
 */
public class MainActivity extends AppCompatActivity {

    private String mp3FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp3";
    private String mp4FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4";
    private File medicCodecFile = null;
    private Button mediaPlayerBtn, audioTrackBtn, recodeAudioBtn, playRecodeAudioBtn,
            mediaCodecBtn, playMediaCodecBtn, recodeMixBtn, playNeedMixedBtn, playMixBtn,
            videoAudioWithPlayBtn, videoAudioWithoutPlayBtn,videoMixAudio;
    private RecordMixTask mRecordMixTask;
    private File mAudioFile = null;
    private boolean mIsRecording = false, mIsPlaying = false;
    private int mFrequence = 44100;
    private int mChannelConfig = AudioFormat.CHANNEL_IN_MONO;//单音轨 保证能在所有设备上工作
    private int mChannelStereo = AudioFormat.CHANNEL_IN_STEREO;
    private int mPlayChannelConfig = AudioFormat.CHANNEL_OUT_STEREO;
    private int mAudioEncoding = AudioFormat.ENCODING_PCM_16BIT;//一个采样点16比特-2个字节

    private AudioEncoder mAudioEncoder;


    private PlayBackMusic mPlayBackMusic = new PlayBackMusic(mp3FilePath);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();

        mediaPlayerBtn = (Button) findViewById(R.id.media_player);
        audioTrackBtn = (Button) findViewById(R.id.audio_track);
        recodeAudioBtn = (Button) findViewById(R.id.recode_audio);
        playRecodeAudioBtn = (Button) findViewById(R.id.play_recode_audio);
        mediaCodecBtn = (Button) findViewById(R.id.recode_audio_mediacodec);
        playMediaCodecBtn = (Button) findViewById(R.id.play_audio_mediacodec);
        recodeMixBtn = (Button) findViewById(R.id.recode_mix_audio);
        playNeedMixedBtn = (Button) findViewById(R.id.play_bg_audio);
        playMixBtn = (Button) findViewById(R.id.play_mix_audio);
        videoAudioWithPlayBtn = (Button) findViewById(R.id.mix_audio_in_video_with_play);
        videoAudioWithoutPlayBtn = (Button) findViewById(R.id.mix_audio_in_video_without_play);
        videoMixAudio = (Button) findViewById(R.id.video_mix_audio);

        medicCodecFile = new File(Environment.getExternalStorageDirectory(), "test_media_audio.mp3"); // m4a");
    }

    private void initPermission() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "All permissions have been granted.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                String message = "Permission " + permission + " has been denied.";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void playNeedMixedAudio(View view) {
        if (playNeedMixedBtn.getTag() == null) {
            playNeedMixedBtn.setText("stop");
            playNeedMixedBtn.setTag(this);
            mPlayBackMusic.release();
            mPlayBackMusic.startPlayBackMusic();
        } else {
            playNeedMixedBtn.setText("play");
            playNeedMixedBtn.setTag(null);
            mPlayBackMusic.stop();
        }
    }


    /**
     * 混合音频
     */
    public void recodeMixAudio(View view) {
        if (recodeMixBtn.getTag() == null) {
            recodeMixBtn.setText("stop");
            recodeMixBtn.setTag(this);
            mAudioEncoder = new AudioEncoder(medicCodecFile.getAbsolutePath());
            mAudioEncoder.prepareEncoder();
            mAudioEncoder.setAudioEncodeCallback(mAudioEncoderCallback);
//            // 测试 发现直接写入 播放的数据不清晰 猜测是 MediaFormat
//            if(mPCMData.getMediaFormat() == null){
//                // 先进行录制 再背景音乐播放
//                mAudioEncoder.prepareEncoder();
//            }else {
//                // just test mPCMData.getMediaFormat() may null  背景音乐先播放 再进行录制
//                // 录制的速率 太快
//                mAudioEncoder.prepareEncoder(mPCMData.getMediaFormat());
//            }
            mRecordMixTask = new RecordMixTask();
            mRecordMixTask.execute();
            if (mPlayBackMusic != null) {
                mPlayBackMusic.setNeedRecodeDataEnable(true);
            }
        } else {
            recodeMixBtn.setText("recode");
            recodeMixBtn.setTag(null);
            if (mPlayBackMusic != null) {
                mPlayBackMusic.setNeedRecodeDataEnable(false);
            }
            // feed all data done code in RecordMixTask
            mIsRecording = false;
        }
    }

    private AudioEncoder.Callback mAudioEncoderCallback = new AudioEncoder.Callback() {
        @Override
        public void onDecodeFinish() {
            Log.i("slack","decode finish...");
        }

        @Override
        public void onProgress(int current, int total) {
            Log.i("slack","decode onProgress " + current + " / " + total);
        }
    };






//
//    /**
//     * 录制 声音小 杂音大
//     */
//    class RecordTask extends AsyncTask<Void, Integer, Void> {
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            mIsRecording = true;
//            try {
//                // 开通输出流到指定的文件
//                DataOutputStream dos = new DataOutputStream(
//                        new BufferedOutputStream(
//                                new FileOutputStream(mAudioFile)));
//                // 根据定义好的几个配置，来获取合适的缓冲大小
//                int bufferSize = AudioRecord.getMinBufferSize(mFrequence,
//                        mChannelStereo, mAudioEncoding);
//                // 实例化AudioRecord
////                AudioRecord record = findAudioRecord();
//                AudioRecord record = new AudioRecord(
//                        MediaRecorder.AudioSource.MIC, mFrequence,
//                        mChannelConfig, mAudioEncoding, bufferSize);
//                // 定义缓冲
//                short[] buffer = new short[bufferSize];
//
//                // 开始录制
//                record.startRecording();
//
//
//                int r = 0; // 存储录制进度
//                // 定义循环，根据isRecording的值来判断是否继续录制
//                while (mIsRecording) {
//                    // 从bufferSize中读取字节，返回读取的short个数
//                    int bufferReadResult = record
//                            .read(buffer, 0, buffer.length);
//                    // try 提高音量 但是会加入噪音
////                    buffer = BytesTransUtil.INSTANCE.adjustVoice(buffer,5);
//                    // try 消除噪音 ，貌似是有用的，但是音量更低了
////                    BytesTransUtil.INSTANCE.noiseClear(buffer,0,bufferReadResult);
//                    // 循环将buffer中的音频数据写入到OutputStream中
//                    for (int i = 0; i < bufferReadResult; i++) {
//                        dos.writeShort(buffer[i]);
//                    }
//                    publishProgress(r); // 向UI线程报告当前进度
//                    r++; // 自增进度值
//                }
//                // 录制结束
//                record.stop();
//                Log.i("slack", "::" + mAudioFile.length());
//                dos.close();
//            } catch (Exception e) {
//                // TODO: handle exception
//                Log.e("slack", "::" + e.getMessage());
//            }
//            return null;
//        }
//
//
//        // 当在上面方法中调用publishProgress时，该方法触发,该方法在UI线程中被执行
//        protected void onProgressUpdate(Integer... progress) {
//            Log.i("slack", "onProgressUpdate:" + progress[0]);
//        }
//
//
//        protected void onPostExecute(Void result) {
//
//        }
//
//    }



    class RecordMixTask extends AsyncTask<Void, Integer, Void> {

        AudioRecord audioRecord;
        int bufferReadResult = 0;
        long audioPresentationTimeNs; //音频时间戳 pts

        public RecordMixTask() {
            // 根据定义好的几个配置，来获取合适的缓冲大小
            int bufferSize = AudioRecord.getMinBufferSize(mFrequence,
                    mChannelConfig, mAudioEncoding);
            // 实例化AudioRecord
            audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC, mFrequence,
                    mChannelConfig, mAudioEncoding, bufferSize * 4);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.i("thread", "RecordMixTask: " + Thread.currentThread().getId());
            mIsRecording = true;

            try {

                // 开始录制
                audioRecord.startRecording();

                while (mIsRecording) {
                    writeMixData();
                }
                // 录制结束 停止接收麦克风数据
                audioRecord.stop();
                audioRecord.release();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("slack", "::" + e.getMessage());
            }finally {
                feedAllData();
                mAudioEncoder.stop();
            }
            return null;
        }

        private void writeMixData(){
            audioPresentationTimeNs = System.nanoTime();

            int samples_per_frame = mPlayBackMusic.getBufferSize(); // 这里需要与 背景音乐读取出来的数据长度 一样
            byte[] buffer = new byte[samples_per_frame];
            //从缓冲区中读取数据，存入到buffer字节数组数组中
            bufferReadResult = audioRecord.read(buffer, 0, buffer.length);
            //判断是否读取成功
            if (bufferReadResult < 0)
                Log.e("slack", "Read error");
            if (mAudioEncoder != null) {
                buffer = mixBuffer(buffer);
                //将音频数据发送给AudioEncoder类进行编码
                mAudioEncoder.offerAudioEncoder(buffer, audioPresentationTimeNs);
            }
        }

        /**
         *  这部分写入的 有问题，播放时 太快
         *  解决 ： 我靠 sleep 一下 就好了  20ms is OK
         *  录制出来的 时间 短 ，用户 录制 10 s，实际上写入的数据不足10秒
         * mAudioEncoder.stop(); 之前  应该先把需要写入的数据消费完
         */
        private void feedAllData() {
            int total = mPlayBackMusic.frameBytesSize();
            while (mAudioEncoder != null && mPlayBackMusic.hasFrameBytes()){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                audioPresentationTimeNs = System.nanoTime();
                Log.e("slack", "feedAllData ... " + audioPresentationTimeNs);
                mAudioEncoder.offerAudioEncoder(mPlayBackMusic.getBackGroundBytes(), audioPresentationTimeNs);
                mAudioEncoderCallback.onProgress(total - mPlayBackMusic.frameBytesSize(),total);
            }
        }


        /**
         * 混合 音频
         */
        private byte[] mixBuffer(byte[] buffer) {
//            return mPlayBackMusic.getBackGroundBytes(); // 直接写入背景音乐数据
            if (mPlayBackMusic.hasFrameBytes()) {
                return BytesTransUtil.INSTANCE.averageMix(buffer, mPlayBackMusic.getBackGroundBytes());
            }
            return buffer;
        }

        // 当在上面方法中调用publishProgress时，该方法触发,该方法在UI线程中被执行
        protected void onProgressUpdate(Integer... progress) {
            //
        }


        protected void onPostExecute(Void result) {

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsPlaying = false;
        mIsRecording = false;
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
