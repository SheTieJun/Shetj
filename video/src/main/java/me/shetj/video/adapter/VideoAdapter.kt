package me.shetj.video.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shetj.video.R
import me.shetj.video.bean.Video

class VideoAdapter(video: List<Video>) :
        BaseQuickAdapter<Video,BaseViewHolder>(R.layout.item_video_info,video){

    override fun convert(helper: BaseViewHolder?, item: Video?) {
        item?.let {
            helper?.setText(R.id.tv_title,it.videoName)
                    ?.setText(R.id.tv_time,"时长${it.duration}")
        }
    }
}