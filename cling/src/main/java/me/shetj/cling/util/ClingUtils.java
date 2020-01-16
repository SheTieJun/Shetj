package me.shetj.cling.util;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ServiceType;

import me.shetj.cling.control.ClingPlayControl;
import me.shetj.cling.entity.IControlPoint;
import me.shetj.cling.entity.IDevice;
import me.shetj.cling.listener.BrowseRegistryListener;
import me.shetj.cling.service.ClingUpnpService;
import me.shetj.cling.service.manager.ClingManager;
import me.shetj.cling.service.manager.DeviceManager;

/**
 * 说明：Cling 库使用工具类
 * 作者：zhouzhan
 * 日期：17/7/4 10:27
 */

public class ClingUtils {


    public static void init(Context context){

        final ClingManager clingUpnpServiceManager = ClingManager.getInstance();
        if (clingUpnpServiceManager.isInit()){
            return;
        }
        /** 用于监听发现设备 */
        final BrowseRegistryListener mBrowseRegistryListener = new BrowseRegistryListener();
        ServiceConnection mUpnpServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                Log.e("ClingUtils", "mUpnpServiceConnection onServiceConnected");
                ClingUpnpService.LocalBinder binder = (ClingUpnpService.LocalBinder) service;
                ClingUpnpService beyondUpnpService = binder.getService();
                clingUpnpServiceManager.setUpnpService(beyondUpnpService);
                clingUpnpServiceManager.setDeviceManager(new DeviceManager());

                clingUpnpServiceManager.getRegistry().addListener(mBrowseRegistryListener);
                //Search on service created.
                clingUpnpServiceManager.searchDevices();
            }

            @Override
            public void onServiceDisconnected(ComponentName className) {
                Log.e("ClingUtils", "mUpnpServiceConnection onServiceDisconnected");
                ClingManager.getInstance().setUpnpService(null);
            }
        };

        Intent upnpServiceIntent = new Intent(context, ClingUpnpService.class);
        context.bindService(upnpServiceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE);
    }


    /**
     * 通过 ServiceType 获取已选择设备的服务
     *
     * @param serviceType   服务类型
     * @return 服务
     */
    @Nullable
    public static Service findServiceFromSelectedDevice(ServiceType serviceType) {
        IDevice selectedDevice = ClingManager.getInstance().getSelectedDevice();
        if (Utils.isNull(selectedDevice)) {
            return null;
        }

        Device device = (Device) selectedDevice.getDevice();
        return device.findService(serviceType);
    }

    /**
     * 获取 device 的 avt 服务
     *
     * @param device    设备
     * @return 服务
     */
    @Nullable
    public static Service findAVTServiceByDevice(Device device) {
        return device.findService(ClingManager.AV_TRANSPORT_SERVICE);
    }

    /**
     * 获取控制点
     *
     * @return 控制点
     */
    @Nullable
    public static ControlPoint getControlPoint() {
        IControlPoint controlPoint = ClingManager.getInstance().getControlPoint();
        if (Utils.isNull(controlPoint)) {
            return null;
        }

        return (ControlPoint) controlPoint.getControlPoint();
    }

    public static ClingPlayControl getClingPlayControl(){
        return new ClingPlayControl();
    }
}
