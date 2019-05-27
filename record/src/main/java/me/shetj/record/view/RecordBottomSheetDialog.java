package me.shetj.record.view;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import org.simple.eventbus.EventBus;

import me.shetj.record.R;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;
import me.shetj.record.utils.ActionCallback;
import me.shetj.record.utils.MainThreadEvent;


/**
 * 录音更多菜单
 */
public class RecordBottomSheetDialog implements View.OnClickListener {
	private BottomSheetDialog easyBottomSheetDialog;
	private Record record;
	private Context context ;
	private ActionCallback callback;
	private int position = -1;
	private BaseViewHolder baseViewHolder;

	public RecordBottomSheetDialog(Context context, int position, Record record, BaseViewHolder viewHolder, ActionCallback callback) {
		this.record = record;
		this.position = position;
		this.context = context;
		this.callback = callback;
		this.baseViewHolder = viewHolder;
		this.easyBottomSheetDialog = buildBottomSheetDialog(context);
	}

	private BottomSheetDialog buildBottomSheetDialog(Context context) {
		BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
		View rootView = LayoutInflater.from(context).inflate(R.layout.dailog_record_case, null);
		bottomSheetDialog.setContentView(rootView);
		rootView.findViewById(R.id.tv_record).setOnClickListener(this);
		rootView.findViewById(R.id.tv_edit_name).setOnClickListener(this);
		rootView.findViewById(R.id.tv_del).setOnClickListener(this);
		rootView.findViewById(R.id.tv_cancel).setOnClickListener(this);

		//对于时间已经大于60 分钟的 不显示继续录制
		rootView.findViewById(R.id.tv_record).setVisibility(record.getAudioLength() > 3599 ? View.GONE: View.VISIBLE);
		return bottomSheetDialog;
	}


	public void showBottomSheet() {
		if (easyBottomSheetDialog != null) easyBottomSheetDialog.show();
	}

	public void dismissBottomSheet() {
		if (easyBottomSheetDialog != null) easyBottomSheetDialog.dismiss();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_record:
				callback.onEvent(2);
				dismissBottomSheet();
				break;
			case R.id.tv_del:
				RecordDbUtils.getInstance().del(record);
				EventBus.getDefault().post(new MainThreadEvent<>(MainThreadEvent.RECORD_REFRESH_DEL, position));
				dismissBottomSheet();
				break;
			case R.id.tv_cancel:
				dismissBottomSheet();
				break;
			case R.id.tv_edit_name:
				dismissBottomSheet();
				break;
			default:
				break;
		}
	}


}
