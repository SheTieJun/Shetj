package me.shetj.record.bean;


import me.shetj.simxutils.db.annotation.Column;
import me.shetj.simxutils.db.annotation.Table;

/**
 * 类名称：Record<br>
 * 内容摘要： 推送过来的录音<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2017/1/11 14:48 <br>
 * 公司：深圳市华移科技股份有限公司<br>
 *
 * @author shetj<br>
 */

@Table(name = "Record")
public class Record {



	//id
	@Column(name = "id",isId = true,autoGen = true)
	private int id;
	@Column(name = "user_id")
	private String user_id;
	@Column(name = "audio_url")
	private String audio_url;
	@Column(name = "audio_name")
	private String audioName;
	@Column(name = "audio_length")
	private int audioLength;
	@Column(name = "audio_content")
	private String audioContent;

	public Record() {

	}

	public Record(String user_id, String audio_url, String audioName, int audioLength,String content) {
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

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
