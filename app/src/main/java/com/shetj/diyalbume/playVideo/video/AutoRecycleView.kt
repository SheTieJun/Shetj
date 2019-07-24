package com.shetj.diyalbume.playVideo.video

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shetj.diyalbume.R



/**
 * **@packageName：** com.shetj.diyalbume.playVideo<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2017/12/12<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

class AutoRecycleView(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycle_string, data) {

    var playPostin = -1

    var oldPosition = -1

    var i = 0

    override fun convert(helper: BaseViewHolder, item: String) {
        if (helper.layoutPosition == playPostin) {
            helper.setText(R.id.tv_string, "播放" + helper.adapterPosition)
        } else {
            helper.setText(R.id.tv_string, item + helper.adapterPosition)
        }
    }

    fun setPlay(i: Int) {
        if (playPostin != i) {
            playPostin = i
            if (oldPosition != -1) {
                notifyItemChanged(oldPosition)
            }
            oldPosition = playPostin
            notifyItemChanged(playPostin)
        }


    }

    fun isStop(firstVisibleItemPosition: Int, lastVisibleItemPosition: Int) {
        if (firstVisibleItemPosition > playPostin || lastVisibleItemPosition < playPostin) {
            if (oldPosition != -1) {
                notifyItemChanged(oldPosition)
            }
            oldPosition = -1
            setPlay(-1)
        }
    }
}
