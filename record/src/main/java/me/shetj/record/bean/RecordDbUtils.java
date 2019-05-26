package me.shetj.record.bean;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shetj.base.tools.file.FileUtils;
import me.shetj.simxutils.DbManager;
import me.shetj.simxutils.DbUtils;
import me.shetj.simxutils.ex.DbException;

public class RecordDbUtils {
	private DbManager dbManager ;
	private static RecordDbUtils instance = null;

	private RecordDbUtils() {
		dbManager= DbUtils.getDbManager("record", 3);
	}

	public static RecordDbUtils getInstance() {
		if (instance == null) {
			synchronized (RecordDbUtils.class) {
				if (instance == null) {
					instance = new RecordDbUtils();
				}
			}
		}
		return instance;
	}


	public void save(Record recordNew){
		try {
			dbManager.save(recordNew);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新录音
	 */
	public  void update(Record message) {
		try {
			dbManager.saveOrUpdate(message);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取全部的录音
	 */
	public List<Record> getAllRecord(){
		try {
			List<Record> all = dbManager.selector(Record.class)
							.where("user_id","=", "1").orderBy("id",true)
							.findAll();
			if (all != null) {
				return all;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


	/**
	 * 获取最后录制的录音
	 */
	public Record getLastRecord(){
		try {
			Record record = dbManager.selector(Record.class)
							.where("user_id","=", "1").orderBy("id",true)
							.findFirst();
			if (record != null) {
				return record;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除
	 */
	public void del(Record record) {
		try {
			dbManager.delete(record);
			FileUtils.deleteFile(new File(record.getAudio_url()));
		}catch (DbException e){
			e.printStackTrace();
		}
	}

	public void clear() {
		try {
			dbManager.delete(Record.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
