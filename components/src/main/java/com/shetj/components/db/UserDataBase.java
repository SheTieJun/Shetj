package com.shetj.components.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * <b>@packageName：</b> com.shetj.components.db<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

@Database(entities = User.class,version = 1,exportSchema = true)
public abstract class  UserDataBase  extends RoomDatabase{
	/**
	 * @return
	 */
	public abstract UserDao userDao();
}
