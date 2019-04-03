package me.shetj.simxutils;

import android.support.annotation.Keep;

import me.shetj.simxutils.db.DbManagerImpl;
import me.shetj.simxutils.ex.DbException;

/**
 * xutils 的DB 模块
 * 注意点：表对象必须存在空参数的构造函数
 *
 * @author shetj<br>
 */

@Keep
public class DbUtils {
	private static DbManager.DaoConfig getDaoConfig(String  dbName,int version){

		return new DbManager.DaoConfig()
						.setDbName(dbName+".db")
						.setDbVersion(version)
						.setAllowTransaction(true)
						.setDbOpenListener(new DbManager.DbOpenListener() {
							@Override
							public void onDbOpened(DbManager db) {
								// 开启WAL, 对写入加速提升巨大
								db.getDatabase().enableWriteAheadLogging();
							}
						})
						.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
							@Override
							public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
								try {
									db.dropDb();
								} catch (DbException e) {
									e.printStackTrace();
								}
							}
						});
	}


	/**
	 * 最好不同用户的信息，创建不同的db,
	 * @param dbName
	 * @return DbManager
	 */
	public static DbManager getDbManager(String dbName,int version)
	{
			return DbManagerImpl.getInstance(getDaoConfig(dbName,version));
	}

	public static void delDbManager(String dbName,int version){
		try {
			DbManagerImpl.getInstance(getDaoConfig(dbName,version)).dropDb();
		} catch (DbException e) {
			e.printStackTrace();
		}
	}


}
