package me.shetj.dlna

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import me.shetj.dlna.bean.DeviceInfo
import me.shetj.dlna.bean.MediaInfo
import me.shetj.dlna.listener.DLNAControlCallback
import me.shetj.dlna.listener.DLNADeviceConnectListener
import org.fourthline.cling.android.AndroidUpnpService
import org.fourthline.cling.controlpoint.ActionCallback
import org.fourthline.cling.controlpoint.SubscriptionCallback
import org.fourthline.cling.model.action.ActionInvocation
import org.fourthline.cling.model.message.UpnpResponse
import org.fourthline.cling.model.meta.Device
import org.fourthline.cling.model.meta.Service
import org.fourthline.cling.model.types.ServiceType
import org.fourthline.cling.model.types.UDAServiceType
import org.fourthline.cling.support.avtransport.callback.*
import org.fourthline.cling.support.model.DIDLObject
import org.fourthline.cling.support.model.PositionInfo
import org.fourthline.cling.support.model.ProtocolInfo
import org.fourthline.cling.support.model.Res
import org.fourthline.cling.support.model.item.AudioItem
import org.fourthline.cling.support.model.item.ImageItem
import org.fourthline.cling.support.model.item.VideoItem
import org.fourthline.cling.support.renderingcontrol.callback.GetVolume
import org.fourthline.cling.support.renderingcontrol.callback.SetMute
import org.fourthline.cling.support.renderingcontrol.callback.SetVolume
import org.seamless.util.MimeType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 设定播放数据、播放、暂停、停止、拖动进度、静音控制、音量控制等等都在这个DLNAPlayer里
 */
class DLNAPlayer(context: Context) {
    private var currentState = UNKNOWN
    private var mDeviceInfo: DeviceInfo? = null
    private var mDevice: Device<*, *, *>? = null
    private var mMediaInfo: MediaInfo? = null
    private var mContext: Context?
    private var mServiceConnection: ServiceConnection? = null
    private var mUpnpService: AndroidUpnpService? = null
    private var connectListener: DLNADeviceConnectListener? = null
    /**
     * 连接、控制服务
     */
    private var AV_TRANSPORT_SERVICE: ServiceType?
    private var RENDERING_CONTROL_SERVICE: ServiceType?

    fun setConnectListener(connectListener: DLNADeviceConnectListener?) {
        this.connectListener = connectListener
    }

    private fun initConnection() {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                mUpnpService = service as AndroidUpnpService
                currentState = CONNECTED
                if (null != mDeviceInfo) {
                    mDeviceInfo!!.state = CONNECTED
                    mDeviceInfo!!.isConnected = true
                }
                if (null != connectListener) {
                    connectListener!!.onConnect(mDeviceInfo, DLNADeviceConnectListener.CONNECT_INFO_CONNECT_SUCCESS)
                }
            }

            override fun onServiceDisconnected(name: ComponentName) {
                currentState = DISCONNECTED
                if (null != mDeviceInfo) {
                    mDeviceInfo!!.state = DISCONNECTED
                    mDeviceInfo!!.isConnected = false
                }
                if (null != connectListener) {
                    connectListener!!.onDisconnect(mDeviceInfo, DLNADeviceConnectListener.TYPE_DLNA,
                            DLNADeviceConnectListener.CONNECT_INFO_DISCONNECT_SUCCESS)
                }
                mUpnpService = null
                connectListener = null
                mDeviceInfo = null
                mDevice = null
                mMediaInfo = null
                AV_TRANSPORT_SERVICE = null
                RENDERING_CONTROL_SERVICE = null
                mServiceConnection = null
                mContext = null
            }
        }
    }

    fun connect(deviceInfo: DeviceInfo) {
        checkConfig()
        mDeviceInfo = deviceInfo
        mDevice = mDeviceInfo!!.device
        if (null != mUpnpService) {
            currentState = CONNECTED
            if (null != connectListener) {
                connectListener!!.onConnect(mDeviceInfo, DLNADeviceConnectListener.CONNECT_INFO_CONNECT_SUCCESS)
            }
            return
        }
        mContext!!.bindService(Intent(mContext, DLNABrowserService::class.java),
                mServiceConnection!!, Context.BIND_AUTO_CREATE)
    }

    fun disconnect() {
        checkConfig()
        try {
            if (null != mUpnpService && null != mServiceConnection) {
                mContext!!.unbindService(mServiceConnection!!)
            }
        } catch (e: Exception) {
            DLNAManager.logE("DLNAPlayer disconnect UPnpService error.", e)
        }
    }

    private fun checkPrepared() {
        checkNotNull(mUpnpService) { "Invalid AndroidUPnpService" }
    }

    private fun checkConfig() {
        checkNotNull(mContext) { "Invalid context" }
    }

    private fun execute(actionCallback: ActionCallback) {
        checkPrepared()
        mUpnpService!!.controlPoint.execute(actionCallback)
    }

    private fun execute(subscriptionCallback: SubscriptionCallback) {
        checkPrepared()
        mUpnpService!!.controlPoint.execute(subscriptionCallback)
    }

    fun play(callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(AV_TRANSPORT_SERVICE)
        if (checkErrorBeforeExecute(PLAY, avtService, callback)) {
            return
        }
        execute(object : Play(avtService) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                currentState = PLAY
                callback.onSuccess(invocation)
                mDeviceInfo!!.state = PLAY
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }
        })
    }

    fun pause(callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(AV_TRANSPORT_SERVICE)
        if (checkErrorBeforeExecute(PAUSE, avtService, callback)) {
            return
        }
        execute(object : Pause(avtService) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                currentState = PAUSE
                callback.onSuccess(invocation)
                mDeviceInfo!!.state = PAUSE
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }
        })
    }

    fun stop(callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(AV_TRANSPORT_SERVICE)
        if (checkErrorBeforeExecute(STOP, avtService, callback)) {
            return
        }
        execute(object : Stop(avtService) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                currentState = STOP
                callback.onSuccess(invocation)
                mDeviceInfo!!.state = STOP
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }
        })
    }

    fun seekTo(time: String?, callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(AV_TRANSPORT_SERVICE)
        if (checkErrorBeforeExecute(avtService, callback)) {
            return
        }
        execute(object : Seek(avtService, time) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                callback.onSuccess(invocation)
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }
        })
    }

    fun setVolume(volume: Long, callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(RENDERING_CONTROL_SERVICE)
        if (checkErrorBeforeExecute(avtService, callback)) {
            return
        }
        execute(object : SetVolume(avtService, volume) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                callback.onSuccess(invocation)
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }
        })
    }

    fun mute(desiredMute: Boolean, callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(RENDERING_CONTROL_SERVICE)
        if (checkErrorBeforeExecute(avtService, callback)) {
            return
        }
        execute(object : SetMute(avtService, desiredMute) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                callback.onSuccess(invocation)
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }
        })
    }

    fun getPositionInfo(callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(AV_TRANSPORT_SERVICE)
        if (checkErrorBeforeExecute(avtService, callback)) {
            return
        }
        val getPositionInfo: GetPositionInfo = object : GetPositionInfo(avtService) {
            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }

            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                callback.onSuccess(invocation)
            }

            override fun received(invocation: ActionInvocation<*>?, info: PositionInfo) {
                callback.onReceived(invocation, info)
            }
        }
        execute(getPositionInfo)
    }

    fun getVolume(callback: DLNAControlCallback) {
        val avtService = mDevice!!.findService(AV_TRANSPORT_SERVICE)
        if (checkErrorBeforeExecute(avtService, callback)) {
            return
        }
        val getVolume: GetVolume = object : GetVolume(avtService) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                callback.onSuccess(invocation)
            }

            override fun received(invocation: ActionInvocation<*>?, currentVolume: Int) {
                callback.onReceived(invocation, currentVolume)
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                currentState = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
                mDeviceInfo!!.state = ERROR
            }
        }
        execute(getVolume)
    }

    fun setDataSource(mediaInfo: MediaInfo) {
        mMediaInfo = mediaInfo
    }

    fun start(callback: DLNAControlCallback) {
        mDeviceInfo!!.mediaID = mMediaInfo!!.mediaId
        val metadata = pushMediaToRender(mMediaInfo!!)
        val avtService = mDevice!!.findService(AV_TRANSPORT_SERVICE)
        if (null == avtService) {
            callback.onFailure(null, DLNAControlCallback.ERROR_CODE_SERVICE_ERROR, null)
            return
        }
        execute(object : SetAVTransportURI(avtService, mMediaInfo!!.uri, metadata) {
            override fun success(invocation: ActionInvocation<*>?) {
                super.success(invocation)
                play(callback)
            }

            override fun failure(invocation: ActionInvocation<*>?, operation: UpnpResponse, defaultMsg: String) {
                DLNAManager.logE("play error:$defaultMsg")
                currentState = ERROR
                mDeviceInfo!!.state = ERROR
                callback.onFailure(invocation, DLNAControlCallback.ERROR_CODE_DLNA_ERROR, defaultMsg)
            }
        })
    }

    private fun pushMediaToRender(mediaInfo: MediaInfo): String {
        return pushMediaToRender(mediaInfo.uri, mediaInfo.mediaId, mediaInfo.mediaName,
                mediaInfo.mediaType)
    }

    private fun pushMediaToRender(url: String?, id: String?, name: String?, ItemType: Int): String {
        val size: Long = 0
        val res = Res(MimeType(ProtocolInfo.WILDCARD, ProtocolInfo.WILDCARD), size, url)
        val creator = "unknow"
        val parentId = "0"
        val metadata: String
        metadata = when (ItemType) {
            MediaInfo.TYPE_IMAGE -> {
                val imageItem = ImageItem(id, parentId, name, creator, res)
                createItemMetadata(imageItem)
            }
            MediaInfo.TYPE_VIDEO -> {
                val videoItem = VideoItem(id, parentId, name, creator, res)
                createItemMetadata(videoItem)
            }
            MediaInfo.TYPE_AUDIO -> {
                val audioItem = AudioItem(id, parentId, name, creator, res)
                createItemMetadata(audioItem)
            }
            else -> throw IllegalArgumentException("UNKNOWN MEDIA TYPE")
        }
        DLNAManager.logE("metadata: $metadata")
        return metadata
    }

    /**
     * 创建投屏的参数
     *
     * @param item
     * @return
     */
    private fun createItemMetadata(item: DIDLObject): String {
        val metadata = StringBuilder()
        metadata.append(DIDL_LITE_HEADER)
        metadata.append(String.format("<item id=\"%s\" parentID=\"%s\" restricted=\"%s\">", item.id, item.parentID, if (item.isRestricted) "1" else "0"))
        metadata.append(String.format("<dc:title>%s</dc:title>", item.title))
        var creator = item.creator
        if (creator != null) {
            creator = creator.replace("<".toRegex(), "_")
            creator = creator.replace(">".toRegex(), "_")
        }
        metadata.append(String.format("<upnp:artist>%s</upnp:artist>", creator))
        metadata.append(String.format("<upnp:class>%s</upnp:class>", item.clazz.value))
        val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA)
        val now = Date()
        val time = sdf.format(now)
        metadata.append(String.format("<dc:date>%s</dc:date>", time))
        val res = item.firstResource
        if (res != null) { // protocol info
            var protocolinfo = ""
            val pi = res.protocolInfo
            if (pi != null) {
                protocolinfo = String.format("protocolInfo=\"%s:%s:%s:%s\"", pi.protocol, pi.network, pi.contentFormatMimeType, pi
                        .additionalInfo)
            }
            DLNAManager.logE("protocolinfo: $protocolinfo")
            // resolution, extra info, not adding yet
            var resolution = ""
            if (res.resolution != null && res.resolution.isNotEmpty()) {
                resolution = String.format("resolution=\"%s\"", res.resolution)
            }
            // duration
            var duration = ""
            if (res.duration != null && res.duration.isNotEmpty()) {
                duration = String.format("duration=\"%s\"", res.duration)
            }
            // res begin
//            metadata.append(String.format("<res %s>", protocolinfo)); // no resolution & duration yet
            metadata.append(String.format("<res %s %s %s>", protocolinfo, resolution, duration))
            // url
            val url = res.value
            metadata.append(url)
            // res end
            metadata.append("</res>")
        }
        metadata.append("</item>")
        metadata.append(DIDL_LITE_FOOTER)
        return metadata.toString()
    }

    private fun checkErrorBeforeExecute(expectState: Int, avtService: Service<*, *>, callback: DLNAControlCallback): Boolean {
        if (currentState == expectState) {
            callback.onSuccess(null)
            return true
        }
        return checkErrorBeforeExecute(avtService, callback)
    }

    private fun checkErrorBeforeExecute(avtService: Service<*, *>?, callback: DLNAControlCallback): Boolean {
        if (currentState == UNKNOWN) {
            callback.onFailure(null, DLNAControlCallback.ERROR_CODE_NOT_READY, null)
            return true
        }
        if (null == avtService) {
            callback.onFailure(null, DLNAControlCallback.ERROR_CODE_SERVICE_ERROR, null)
            return true
        }
        return false
    }

    companion object {
        private const val DIDL_LITE_FOOTER = "</DIDL-Lite>"
        private const val DIDL_LITE_HEADER = ("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>"
                + "<DIDL-Lite "
                + "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" "
                + "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" "
                + "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" "
                + "xmlns:dlna=\"urn:schemas-dlna-org:metadata-1-0/\">")
        /**
         * 未知状态
         */
        const val UNKNOWN = -1
        /**
         * 已连接状态
         */
        const val CONNECTED = 0
        /**
         * 播放状态
         */
        const val PLAY = 1
        /**
         * 暂停状态
         */
        const val PAUSE = 2
        /**
         * 停止状态
         */
        const val STOP = 3
        /**
         * 转菊花状态
         */
        const val BUFFER = 4
        /**
         * 投放失败
         */
        const val ERROR = 5
        /**
         * 已断开状态
         */
        const val DISCONNECTED = 6
    }

    init {
        mContext = context
        AV_TRANSPORT_SERVICE = UDAServiceType("AVTransport")
        RENDERING_CONTROL_SERVICE = UDAServiceType("RenderingControl")
        initConnection()
    }
}