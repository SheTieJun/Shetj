package com.shetj.components.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * <b>@packageName：</b> com.shetj.components.db<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
@Entity(tableName = "User")
public class User {

	@PrimaryKey(autoGenerate = true)
	public int id;
	public String name;
	public String portrait;

	@Ignore
	public User() {
	}

	public User(int id, String name, String portrait) {
		this.id = id;
		this.name = name;
		this.portrait = portrait;
	}
}
