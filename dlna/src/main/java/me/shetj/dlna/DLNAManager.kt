package me.shetj.dlna

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.ContextThemeWrapper
import me.shetj.dlna.listener.DLNARegistryListener
import me.shetj.dlna.listener.DLNAStateCallback
import org.fourthline.cling.android.AndroidUpnpService
import org.fourthline.cling.model.message.header.STAllHeader
import org.fourthline.cling.model.meta.LocalDevice
import org.fourthline.cling.model.meta.RemoteDevice
import org.fourthline.cling.model.types.UDADeviceType
import org.fourthline.cling.model.types.UDAServiceType
import org.fourthline.cling.registry.Registry
import org.fourthline.cling.registry.RegistryListener
import java.util.*


class DLNAManager private constructor() {
    private var mContext: Context? = null
    private var mUpnpService: AndroidUpnpService? = null
    private var mServiceConnection: ServiceConnection? = null
    private var mStateCallback: DLNAStateCallback? = null
    private var mRegistryListener: RegistryListener?
    private var registryListenerList: MutableList<DLNARegistryListener> = ArrayList()
    private var mHandler: Handler? = Handler(Looper.getMainLooper())

    private object DLNAManagerCreator {
        internal val manager = DLNAManager()
    }

    @JvmOverloads
    fun init(context: Context, stateCallback: DLNAStateCallback? = null) {
        if (null != mUpnpService) {
            mStateCallback?.onConnected()
            return
        }
        mContext = if (context is ContextThemeWrapper || context is ContextThemeWrapper) {
            context.getApplicationContext()
        } else {
            context
        }
        mStateCallback = stateCallback
        initConnection()
    }

    private fun initConnection() {
        if (mUpnpService == null) {
            mServiceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, service: IBinder) {
                    mUpnpService = service as AndroidUpnpService

                    mUpnpService!!.registry.addListener(mRegistryListener)
                    mUpnpService!!.controlPoint.search(STAllHeader())
                    if (null != mStateCallback) {
                        mStateCallback!!.onConnected()
                    }
                    logD("onServiceConnected")
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    mUpnpService = null
                    if (null != mStateCallback) {
                        mStateCallback!!.onDisconnected()
                    }
                    logD("onServiceDisconnected")
                }
            }
            mContext!!.bindService(Intent(mContext, DLNABrowserService::class.java),
                    mServiceConnection!!, Context.BIND_AUTO_CREATE)
        }
    }

    fun registerListener(listener: DLNARegistryListener?) {
        checkConfig()
        checkPrepared()
        if (null == listener) {
            return
        }
        registryListenerList?.add(listener)
        listener.onDeviceChanged(mUpnpService!!.registry.devices)
    }

    fun unregisterListener(listener: DLNARegistryListener?) {
        checkConfig()
        checkPrepared()
        if (null == listener) {
            return
        }
        mUpnpService!!.registry.removeListener(listener)
        registryListenerList?.remove(listener)
    }

    fun startBrowser() {
        checkConfig()
        checkPrepared()
        mUpnpService!!.registry.addListener(mRegistryListener)
        mUpnpService!!.controlPoint.search(STAllHeader())
    }

    fun stopBrowser() {
        checkConfig()
        checkPrepared()
        mUpnpService!!.registry.removeListener(mRegistryListener)
    }

    fun destroy() {
        checkConfig()
        registryListenerList.clear()
        stopBrowser()
        if (null != mUpnpService) {
            mUpnpService!!.registry.removeListener(mRegistryListener)
            mUpnpService!!.registry.shutdown()
        }
        if (null != mServiceConnection) {
            mContext!!.unbindService(mServiceConnection!!)
            mServiceConnection = null
        }
        mHandler?.removeCallbacksAndMessages(null)
        mRegistryListener = null
        mStateCallback = null
        mContext = null
    }

    private fun checkConfig() {
        checkNotNull(mContext) { "Must call init(Context context) at first" }
    }

    private fun checkPrepared() {
        checkNotNull(mUpnpService) { "Invalid AndroidUpnpService" }
    }

    companion object {
        private const val TAG = "DLNAManager"
        private var isDebugMode = true
        val instance: DLNAManager get() = DLNAManagerCreator.manager


        fun setIsDebugMode(isDebugMode: Boolean) {
            Companion.isDebugMode = isDebugMode
        }

        fun logV(content: String?) {
            logV(TAG, content)
        }

        fun logV(tag: String?, content: String?) {
            if (!isDebugMode) {
                return
            }
            Log.v(tag, content)
        }

        fun logD(content: String?) {
            logD(TAG, content)
        }

        @JvmStatic
        fun logD(tag: String?, content: String?) {
            if (!isDebugMode) {
                return
            }
            Log.d(tag, content)
        }

        fun logI(content: String?) {
            logI(TAG, content)
        }

        fun logI(tag: String?, content: String?) {
            if (!isDebugMode) {
                return
            }
            Log.i(tag, content)
        }

        fun logW(content: String?) {
            logW(TAG, content)
        }

        fun logW(tag: String?, content: String?) {
            if (!isDebugMode) {
                return
            }
            Log.w(tag, content)
        }

        fun logE(content: String?) {
            logE(TAG, content)
        }

        fun logE(content: String?, throwable: Throwable?) {
            logE(TAG, content, throwable)
        }

        @JvmOverloads
        fun logE(tag: String?, content: String?, throwable: Throwable? = null) {
            if (!isDebugMode) {
                return
            }
            if (null != throwable) {
                Log.e(tag, content, throwable)
            } else {
                Log.e(tag, content)
            }
        }
    }

    init {
        mRegistryListener = object : RegistryListener {
            override fun remoteDeviceDiscoveryStarted(registry: Registry, device: RemoteDevice) {
                mHandler?.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.remoteDeviceDiscoveryStarted(registry, device)
                        }
                    }
                }
            }

            override fun remoteDeviceDiscoveryFailed(registry: Registry, device: RemoteDevice, ex: Exception) {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.remoteDeviceDiscoveryFailed(registry, device, ex)
                        }
                    }
                }
            }

            override fun remoteDeviceAdded(registry: Registry, device: RemoteDevice) {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.remoteDeviceAdded(registry, device)
                        }
                    }
                }
            }

            override fun remoteDeviceUpdated(registry: Registry, device: RemoteDevice) {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.remoteDeviceUpdated(registry, device)
                        }
                    }
                }
            }

            override fun remoteDeviceRemoved(registry: Registry, device: RemoteDevice) {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.remoteDeviceRemoved(registry, device)
                        }
                    }
                }
            }

            override fun localDeviceAdded(registry: Registry, device: LocalDevice) {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.localDeviceAdded(registry, device)
                        }
                    }
                }
            }

            override fun localDeviceRemoved(registry: Registry, device: LocalDevice) {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.localDeviceRemoved(registry, device)
                        }
                    }
                }
            }

            override fun beforeShutdown(registry: Registry) {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.beforeShutdown(registry)
                        }
                    }
                }
            }

            override fun afterShutdown() {
                mHandler!!.post {
                    synchronized(DLNAManager::class.java) {
                        for (listener in registryListenerList) {
                            listener.afterShutdown()
                        }
                    }
                }
            }
        }
    }
}