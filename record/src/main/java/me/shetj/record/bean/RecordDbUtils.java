package me.shetj.record.bean;

import java.util.ArrayList;
import java.util.List;

import me.shetj.simxutils.xutils.DbManager;
import me.shetj.simxutils.xutils.DbUtils;
import me.shetj.simxutils.xutils.ex.DbException;

public class RecordDbUtils {
	private DbManager dbManager ;
	private static RecordDbUtils instance = null;

	private RecordDbUtils() {
		dbManager= DbUtils.getDbManager("record", 1);
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


	public  void update(Record message) {
		try {
			dbManager.saveOrUpdate(message);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public  List<Record> getAllRecord(){
		try {
			List<Record> all = dbManager.selector(Record.class)
							.where("user_id","=", "1")
							.findAll();
			if (all != null) {
				return all;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}
