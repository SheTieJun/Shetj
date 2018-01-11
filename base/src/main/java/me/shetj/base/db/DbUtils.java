package me.shetj.base.db;

import android.support.annotation.Keep;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * @author shetj<br>
 */

@Keep
public class DbUtils {
	private static DbManager.DaoConfig getDaoConfig(String  dbName){
		return new DbManager.DaoConfig()
						.setDbName(dbName+".db")
						.setDbVersion(1)
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
							}
						});
	}

	public static DbManager getDbManager(String dbName)
	{
			return x.getDb(getDaoConfig(dbName));
	}
}
