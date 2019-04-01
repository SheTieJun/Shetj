package me.shetj.record.utils;

import android.os.Handler;
import android.os.Message;

import com.czt.mp3recorder.MP3Recorder;

import java.io.File;
import java.io.IOException;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.file.FileUtils;

/**
 * Created by shetj on 2017/6/9.
 * 抽象方法
 * 公共方法
 * 私有方法
 */


public class RecordUtils {
	public static final int
					NORMAL = 1,//正常
					RECORD_ING = 2,//正在录音
					RECORD_PAUSE = 3;//暂停
	private MP3Recorder mRecorder;
	private String saveFile = "";
	private int momentState = NORMAL;

	/**
	 * 开始录音
	 */
	public void  startFullRecord(String saveFile) {
		if (momentState == NORMAL) {
			this.saveFile = saveFile;
			mRecorder = new MP3Recorder(new File(saveFile));
			mRecorder.setErrorHandler(new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if (msg.what == MP3Recorder.ERROR_TYPE) {
						ArmsUtils.makeText("没有麦克风权限");
						resolveError();
					}
				}
			});
			try {
				mRecorder.start();
			} catch (IOException e) {
				e.printStackTrace();
				ArmsUtils.makeText("录音出现异常");
				resolveError();
				momentState = NORMAL;
			}
		}else if (momentState == RECORD_PAUSE){
			if (mRecorder != null&&mRecorder.isPause()) {
				mRecorder.setPause(false);
			}
		}
		momentState = RECORD_ING;
	}


	/**
	 * 录音异常
	 */
	public void resolveError() {
		FileUtils.deleteFile(new File(saveFile));
		if (mRecorder != null && mRecorder.isRecording()) {
			mRecorder.stop();
		}
		momentState = NORMAL;
	}

	/**
	 * 停止录音
	 */
	public void stopFullRecord() {
		if (mRecorder != null && mRecorder.isRecording()) {
			mRecorder.setPause(false);
			mRecorder.stop();
		}
		momentState = NORMAL;
	}


	/**
	 * 暂停录音
	 */
	public void  pauseFullRecord() {
		if (momentState == RECORD_ING) {
			if (mRecorder != null && !mRecorder.isPause()) {
				mRecorder.setPause(true);
			}
			momentState = RECORD_PAUSE;
		}
	}

	/**
	 * 继续录音
	 */
	public void  onResumeFullRecord() {
		if (momentState == RECORD_PAUSE) {
			if (mRecorder != null && mRecorder.isPause()) {
				mRecorder.setPause(false);
			}
			momentState = RECORD_ING;
		}
	}


	public int getState(){
		return momentState;
	}
}
