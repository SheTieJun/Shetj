package com.shetj.diyalbume.playVideo.video

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shetj.diyalbume.R
import me.shetj.dlna.bean.DeviceInfo
import org.fourthline.cling.model.meta.Device


/**
 * **@packageName：** com.shetj.diyalbume.playVideo<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2017/12/12<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

class AutoRecycleViewAdapter(data: MutableList<DeviceInfo>?) : BaseQuickAdapter<DeviceInfo, BaseViewHolder>(R.layout.item_recycle_string, data) {

    private var playPosition = -1

    private var oldPosition = -1

    var i = 0

    override fun convert(helper: BaseViewHolder, item: DeviceInfo?) {
        item?.let {
            if (helper.layoutPosition == playPosition) {
                helper.setText(R.id.tv_string, "播放" + item.name)
            } else {
                helper.setText(R.id.tv_string, item.name)
            }
        }
    }

    fun setPlay(i: Int) {
        if (playPosition != i) {
            playPosition = i
            if (oldPosition != -1) {
                notifyItemChanged(oldPosition)
            }
            oldPosition = playPosition
            notifyItemChanged(playPosition)
        }
    }

    fun removeDevice(device: Device<*, *, *>?) {
        data.find {
            it.device?.equals(device) ?: false
        }?.apply {
            remove(this)
        }
    }
}
