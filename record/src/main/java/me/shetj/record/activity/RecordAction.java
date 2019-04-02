package me.shetj.record.activity;

import android.support.transition.AutoTransition;
import android.support.transition.ChangeBounds;
import android.support.transition.Explode;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.shetj.record.R;
import me.shetj.record.bean.Record;
import me.shetj.record.utils.Util;
import me.shetj.record.view.EasyBottomSheetDialog;

/**
 * 录音界面切换
 */
public class RecordAction {

	private FrameLayout mRoot;
	private Record record;
	private Scene scene1;	//标准界面
	private Scene  scene2;	//带播放界面
	private CompositeDisposable mCompositeDisposable;
	public RecordAction(FrameLayout mRoot, Record record) {
		this.mRoot = mRoot;
		this.record = record;
		initData(record);
	}

	private void initData(Record question) {
		scene1 =  getScene1(question,mRoot);
		scene2 =  getScene2(question,mRoot);
		TransitionManager.go(scene1,new ChangeBounds());
	}

	private Scene getScene2(Record record, FrameLayout mRoot) {
		View view = LayoutInflater.from(mRoot.getContext()).inflate(R.layout.item_view_2, null);
		TextView tvName = view.findViewById(R.id.tv_name);
		tvName.setText(record.getAudioName());
		TextView tvTime = view.findViewById(R.id.tv_time_all);
		tvTime.setText( Util.formatSeconds2(record.getAudioLength()));

		view.findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				EasyBottomSheetDialog dialog = new EasyBottomSheetDialog(mRoot.getContext(),record);
				dialog.showBottomSheet();
			}
		});

		return new Scene(mRoot,view);
	}

	private Scene getScene1(Record record, FrameLayout mRoot) {
		View view = LayoutInflater.from(mRoot.getContext()).inflate(R.layout.item_view, null);
		TextView tvName = view.findViewById(R.id.tv_name);
		tvName.setText(record.getAudioName());
		TextView tvTime = view.findViewById(R.id.tv_time);
		tvTime.setText(String.format("时长：%s", Util.formatSeconds2(record.getAudioLength())));
		return new Scene(mRoot,view);
	}

	public void startShow() {
		addDispose(Flowable.timer(100, TimeUnit.MILLISECONDS)
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(aLong -> {
							ChangeBounds transition = new ChangeBounds();
							TransitionManager.go(scene2, transition);
						} ));
	}



	public void stopAction(){
		addDispose(Flowable.timer(10, TimeUnit.MILLISECONDS)
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(aLong -> {
							ChangeBounds transition = new ChangeBounds();
							TransitionManager.go(scene1, transition);
						} ));
	}

	/**
	 * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
	 * 可在 {onDestroy() 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
	 *
	 * @param disposable
	 */
	public void addDispose(Disposable disposable) {
		if (mCompositeDisposable == null) {
			mCompositeDisposable = new CompositeDisposable();
		}
		mCompositeDisposable.add(disposable);
	}

	/**
	 * 停止集合中正在执行的 RxJava 任务
	 */
	public void unDispose() {
		if (mCompositeDisposable != null) {
			mCompositeDisposable.clear();
		}
	}


}