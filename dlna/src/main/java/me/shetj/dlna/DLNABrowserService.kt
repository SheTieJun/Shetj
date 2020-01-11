package me.shetj.dlna

import me.shetj.dlna.xml.DLNAUDA10ServiceDescriptorBinderSAXImpl
import org.fourthline.cling.UpnpServiceConfiguration
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration
import org.fourthline.cling.android.AndroidUpnpServiceImpl
import org.fourthline.cling.binding.xml.ServiceDescriptorBinder

class DLNABrowserService : AndroidUpnpServiceImpl() {
    override fun createConfiguration(): UpnpServiceConfiguration {
        return object : AndroidUpnpServiceConfiguration() {
            public override fun createServiceDescriptorBinderUDA10(): ServiceDescriptorBinder {
                return DLNAUDA10ServiceDescriptorBinderSAXImpl()
            }
        }
    }
}