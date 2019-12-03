package com.shetj.diyalbume.bluetooth

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.clj.fastble.data.BleDevice
import com.shetj.diyalbume.R

/**
 *
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/7/23<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>  <br>
 */
class BluetoothListAdapter(strings: MutableList<BleDevice>) : BaseQuickAdapter<BleDevice,BaseViewHolder>(R.layout.item_bluetooth,strings){

    override fun convert(helper: BaseViewHolder, item: BleDevice) {
        helper.setText(R.id.tv_msg,"mac = ${item?.mac}")
        ?.setText(R.id.tv_name,"name = ${item?.name}")
        ?.setText(R.id.tv_key,"key = ${item?.key}")
    }

}