package cn.shetj.base.db;

import android.support.annotation.Keep;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * @author shetj<br>
 */

@Keep
public class DbUtils {
	static DbManager.DaoConfig daoConfig;
	/**
	 * 获取DaoConfig
	 * @return
	 */
	private static DbManager.DaoConfig getDaoConfig(){
//		File file=new File(SDCardUtils.getPath(ConfigsSavePath.dbPath));
		if(daoConfig==null){
			daoConfig=new DbManager.DaoConfig()
							.setDbName("MoFang.db")
//							.setDbDir(file)
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
		return daoConfig;
	}
	/**
	 * dbManager
	 */
	public static DbManager getDbManager()
	{
		if (daoConfig!=null) {
			return x.getDb(daoConfig);
		}else{
			return x.getDb(getDaoConfig());
		}
	}
}
