package me.shetj.cling.control

import android.content.Context
import me.shetj.cling.entity.IDevice
import me.shetj.cling.service.callback.AVTransportSubscriptionCallback
import me.shetj.cling.service.callback.RenderingControlSubscriptionCallback
import me.shetj.cling.service.manager.ClingManager
import me.shetj.cling.util.ClingUtils
import me.shetj.cling.util.Utils.isNotNull
import me.shetj.cling.util.Utils.isNull
import org.fourthline.cling.model.meta.Device

/**
 * 说明：
 * 作者：zhouzhan
 * 日期：17/7/21 16:43
 */
class SubscriptionControl : ISubscriptionControl<Device<*, *, *>> {
    private var mAVTransportSubscriptionCallback: AVTransportSubscriptionCallback? = null
    private var mRenderingControlSubscriptionCallback: RenderingControlSubscriptionCallback? = null

    override fun registerAVTransport(device: IDevice<Device<*, *, *>>, context: Context) {
        if (isNotNull(mAVTransportSubscriptionCallback)) {
            mAVTransportSubscriptionCallback!!.end()
        }
        val controlPointImpl = ClingUtils.controlPoint
        if (isNull(controlPointImpl)) {
            return
        }
        mAVTransportSubscriptionCallback = AVTransportSubscriptionCallback(device.device.findService(ClingManager.AV_TRANSPORT_SERVICE), context)
        controlPointImpl!!.execute(mAVTransportSubscriptionCallback)
    }

    override fun registerRenderingControl(device: IDevice<Device<*, *, *>>, context: Context) {
        if (isNotNull(mRenderingControlSubscriptionCallback)) {
            mRenderingControlSubscriptionCallback!!.end()
        }
        val controlPointImpl = ClingUtils.controlPoint
        if (isNull(controlPointImpl)) {
            return
        }
        mRenderingControlSubscriptionCallback = RenderingControlSubscriptionCallback(device.device.findService(ClingManager.RENDERING_CONTROL_SERVICE), context)
        controlPointImpl!!.execute(mRenderingControlSubscriptionCallback)
    }

    override fun destroy() {
        if (isNotNull(mAVTransportSubscriptionCallback)) {
            mAVTransportSubscriptionCallback!!.end()
        }
        if (isNotNull(mRenderingControlSubscriptionCallback)) {
            mRenderingControlSubscriptionCallback!!.end()
        }
    }

}