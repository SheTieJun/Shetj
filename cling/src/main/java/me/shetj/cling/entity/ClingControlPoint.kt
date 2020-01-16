package me.shetj.cling.entity

import org.fourthline.cling.controlpoint.ControlPoint

/**
 * 说明：cling 控制点实现类
 * 作者：zhouzhan
 * 日期：17/6/28 17:03
 */
class ClingControlPoint private constructor() : IControlPoint<ControlPoint> {
    private var mControlPoint: ControlPoint? = null
    override fun getControlPoint(): ControlPoint {
        return mControlPoint!!
    }

    override fun setControlPoint(controlPoint: ControlPoint) {
        mControlPoint = controlPoint
    }

    override fun destroy() {
        mControlPoint = null
        INSTANCE = null
    }

    companion object {
        private var INSTANCE: ClingControlPoint? = null
        @JvmStatic
        val instance: ClingControlPoint?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = ClingControlPoint()
                }
                return INSTANCE
            }
    }
}