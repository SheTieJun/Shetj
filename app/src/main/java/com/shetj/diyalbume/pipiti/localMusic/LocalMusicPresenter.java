package com.shetj.diyalbume.pipiti.localMusic;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.shetj.base.base.BasePresenter;
import me.shetj.base.base.IView;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.view.LoadingDialog;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.pipiti.localMusic<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/10/15 0015<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>本地音乐<br>
 */
class LocalMusicPresenter extends BasePresenter<LocalModel> {
	private ArrayList<Music> musicList;
	public LocalMusicPresenter(IView view) {
		super(view);
		model = new LocalModel();
	}

	public void initMusic() {
		ArmsUtils.getRxPermissions(view.getRxContext()).request(Manifest.permission.READ_EXTERNAL_STORAGE)
						.subscribe(new Consumer<Boolean>() {
							@Override
							public void accept(Boolean aBoolean) throws Exception {
								if (aBoolean){
									loadFileData();
								}
							}
						});


	}

	/**
	 * 查询本地的音乐文件
	 */
	private void loadFileData() {
		LoadingDialog.showLoading(view.getRxContext());
		Disposable disposable = Flowable.create(new FlowableOnSubscribe<List<Music>>() {
			@Override
			public void subscribe(FlowableEmitter<List<Music>> emitter) throws Exception {
				ContentResolver resolver = view.getRxContext().getContentResolver();
				Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
								null, null, null, null);
				cursor.moveToFirst();
				musicList = new ArrayList();
				if (cursor.moveToFirst()) {
					do {
						String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
						long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
						String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
						long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
						String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
						if (duration >= 1000 && duration <= 2000000) {
							Music music = new Music();
							music.setName(title);
							music.setSize(size);
							music.setUrl(url);
							music.setDuration(duration);
							musicList.add(music);
						}
					} while (cursor.moveToNext());
				}
				cursor.close();
				LoadingDialog.hideLoading();
				if (musicList.size() > 0) {
					emitter.onNext(musicList);
				} else {
					emitter.onError(new Throwable("本地没有音乐~"));
				}
			}
		}, BackpressureStrategy.BUFFER)
						.subscribeOn(Schedulers.io())
						.compose(view.getRxContext().bindToLifecycle())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<List<Music>>() {
							@Override
							public void accept(List<Music> music) throws Exception {
								view.updateView(getMessage(1,music));
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) throws Exception {
								ArmsUtils.makeText(throwable.getMessage());
							}
						});
		addDispose(disposable);
	}
}
