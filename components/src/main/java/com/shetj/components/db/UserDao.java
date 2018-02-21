package com.shetj.components.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.annotations.NonNull;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * <b>@packageName：</b> com.shetj.components.db<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
@Dao
public interface UserDao {

	@Insert(onConflict = REPLACE)
	void insert(User user);

	@NonNull
	@Query(" SELECT * FROM User WHERE id = :userId LIMIT 1")
	User queryUser(String userId);

	@Update (onConflict = REPLACE)
	void updateUser(User user);

	@NonNull
	@Delete
	void deletUser(User user);

	@Query("SELECT * FROM User")
	List<User> getAll();

	@Query("SELECT * FROM User WHERE id IN (:userIds)")
	List<User> loadAllByIds(int[] userIds);

	@Insert
	void insertAll(User... users);

}
