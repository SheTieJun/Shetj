package me.shetj.record.bean;


import me.shetj.simxutils.db.annotation.Column;
import me.shetj.simxutils.db.annotation.Table;

/**
 *  录音
 */
@Table(name = "record")
public class Record {

	@Column(name = "id",isId = true,autoGen = true)
	private int id;
	@Column(name = "user_id")
	private String user_id;//是否绑定用户，默认不绑定用户
	@Column(name = "audio_url")
	private String audio_url;//保存的路径
	@Column(name = "audio_name")
	private String audioName;//录音的名称
	@Column(name = "audio_length")
	private int audioLength;//长度
	@Column(name = "audio_content")
	private String audioContent;//内容
	@Column(name = "otherInfo")
	private String otherInfo;// 预览信息

	public Record() {

	}

	public Record(String user_id, String audio_url, String audioName, int audioLength, String content) {
		this.user_id = user_id;
		this.audio_url = audio_url;
		this.audioName = audioName;
		this.audioLength = audioLength;
		this.audioContent = content;
	}


	public String getAudioContent() {
		return audioContent;
	}

	public void setAudioContent(String audioContent) {
		this.audioContent = audioContent;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAudio_url() {
		return audio_url;
	}

	public void setAudio_url(String audio_url) {
		this.audio_url = audio_url;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public int getAudioLength() {
		return audioLength;
	}

	public void setAudioLength(int audioLength) {
		this.audioLength = audioLength;
	}
}
