package me.shetj.download.adapter;

import androidx.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shetj.download.R;
import me.shetj.download.base.ClassInfo;

public class ClassAdapter extends BaseQuickAdapter<ClassInfo,TaskItemViewHolder>  {

	private Map<Integer,ClassInfo> infoMap = new HashMap<>();

	private View targetView;

	public ClassAdapter(@Nullable List<ClassInfo> data,View targetView) {
		super(R.layout.item_layout_download_class_info,data);
		this.targetView = targetView;
	}

	@Override
	protected void convert(TaskItemViewHolder helper, ClassInfo item) {
		if (item.isDownload){
			helper.getView(R.id.fl_root).setAlpha(0.35f);
			helper.getView(R.id.iv_state).setAlpha(1);
			helper.getView(R.id.iv_state).setVisibility(View.VISIBLE);
		}else {
			helper.getView(R.id.fl_root).setSelected(item.isSelect);
			helper.getView(R.id.tv_title).setSelected(item.isSelect);
			helper.getView(R.id.tv_time).setSelected(item.isSelect);
			helper.getView(R.id.tv_size).setSelected(item.isSelect);
		}
		if (item.isSelect){
			infoMap.put(helper.getAdapterPosition(),item);
		}else {
			infoMap.remove(helper.getAdapterPosition());
		}
	}

	public void downloadAll(){
		for (Integer position : infoMap.keySet()){
//
//			View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_layout_download_class_info, null);
//			ClassInfo item = infoMap.get(position);
//			TaskItemViewHolder holder = new TaskItemViewHolder(inflate);
//
//				holder.getView(R.id.fl_root).setSelected(item.isSelect);
//				holder.getView(R.id.tv_title).setSelected(item.isSelect);
//				holder.getView(R.id.tv_time).setSelected(item.isSelect);
//				holder.getView(R.id.tv_size).setSelected(item.isSelect);
//			FloatingElement builder = new FloatingBuilder()
//							.anchorView(getRecyclerView().findViewHolderForLayoutPosition(position).itemView.findViewById(R.id.fl_root))
//							.targetView(inflate)
//							.offsetX(100)
//              .offsetY(0)
//							.floatingTransition(new TranslateFloatingTransition())
//							.build();
//			Floating floating = new Floating((Activity) mContext);
//			floating.startFloating(builder);
		}
	}

}
