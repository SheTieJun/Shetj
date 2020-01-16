package me.shetj.dlna.listener

import me.shetj.dlna.bean.DeviceInfo
import org.fourthline.cling.model.meta.Device
import org.fourthline.cling.model.meta.LocalDevice
import org.fourthline.cling.model.meta.RemoteDevice
import org.fourthline.cling.model.types.DeviceType
import org.fourthline.cling.model.types.UDADeviceType
import org.fourthline.cling.registry.Registry
import org.fourthline.cling.registry.RegistryListener
import java.util.*

abstract class DLNARegistryListener : RegistryListener {
    private val DMR_DEVICE_TYPE: DeviceType = UDADeviceType("MediaRenderer")
    override fun remoteDeviceDiscoveryStarted(registry: Registry, device: RemoteDevice) {}
    override fun remoteDeviceDiscoveryFailed(registry: Registry, device: RemoteDevice, ex: Exception) {}
    /**
     * Calls the [.onDeviceChanged] method.
     *
     * @param registry The Cling registry of all devices and services know to the local UPnP stack.
     * @param device   A validated and hydrated device metadata graph, with complete service metadata.
     */
    override fun remoteDeviceAdded(registry: Registry, device: RemoteDevice) {
        onDeviceChanged(build(registry.devices))
        onDeviceAdded(registry, device)
    }

    override fun remoteDeviceUpdated(registry: Registry, device: RemoteDevice) {}
    /**
     * Calls the [.onDeviceChanged] method.
     *
     * @param registry The Cling registry of all devices and services know to the local UPnP stack.
     * @param device   A validated and hydrated device metadata graph, with complete service metadata.
     */
    override fun remoteDeviceRemoved(registry: Registry, device: RemoteDevice) {
        onDeviceChanged(build(registry.devices))
        onDeviceRemoved(registry, device)
    }

    /**
     * Calls the [.onDeviceChanged] method.
     *
     * @param registry The Cling registry of all devices and services know to the local UPnP stack.
     * @param device   The local device added to the [Registry].
     */
    override fun localDeviceAdded(registry: Registry, device: LocalDevice) {
        onDeviceChanged(build(registry.devices))
        onDeviceAdded(registry, device)
    }

    /**
     * Calls the [.onDeviceChanged] method.
     *
     * @param registry The Cling registry of all devices and services know to the local UPnP stack.
     * @param device   The local device removed from the [Registry].
     */
    override fun localDeviceRemoved(registry: Registry, device: LocalDevice) {
        onDeviceChanged(build(registry.devices))
        onDeviceRemoved(registry, device)
    }

    override fun beforeShutdown(registry: Registry) {}
    override fun afterShutdown() {}
    fun onDeviceChanged(deviceInfoList: Collection<Device<*, *, *>>) {
        onDeviceChanged(build(deviceInfoList))
    }

    abstract fun onDeviceChanged(deviceInfoList: List<DeviceInfo>?)

    open fun onDeviceAdded(registry: Registry?, device: Device<*, *, *>?) {}

    open fun onDeviceRemoved(registry: Registry?, device: Device<*, *, *>?) {}

    private fun build(deviceList: Collection<Device<*, *, *>>): List<DeviceInfo> {
        val deviceInfoList: MutableList<DeviceInfo> = ArrayList()
        for (device in deviceList) { //过滤不支持投屏渲染的设备
            if (null == device.findDevices(DMR_DEVICE_TYPE)) {
                continue
            }
            val deviceInfo = DeviceInfo(device, getDeviceName(device))
            deviceInfoList.add(deviceInfo)
        }
        return deviceInfoList
    }

    private fun getDeviceName(device: Device<*, *, *>): String {
        var name = ""
        name = if (device.details != null && device.details.friendlyName != null) {
            device.details.friendlyName
        } else {
            device.displayString
        }
        return name
    }
}