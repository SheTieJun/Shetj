package me.shetj.dlna.xml

import me.shetj.dlna.DLNAManager.Companion.logD
import org.fourthline.cling.binding.staging.MutableService
import org.fourthline.cling.binding.xml.DescriptorBindingException
import org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl
import org.fourthline.cling.model.ValidationException
import org.fourthline.cling.model.meta.Service
import org.seamless.xml.SAXParser
import org.xml.sax.InputSource
import java.io.StringReader

class DLNAUDA10ServiceDescriptorBinderSAXImpl : UDA10ServiceDescriptorBinderSAXImpl() {
    @Throws(DescriptorBindingException::class, ValidationException::class)
    override fun <S : Service<*, *>?> describe(undescribedService: S, descriptorXml: String): S {
        if (descriptorXml.isEmpty()) {
            throw DescriptorBindingException("Null or empty descriptor")
        }
        return try {
            logD(TAG, "Reading service from XML descriptor, content : \n$descriptorXml")
            val parser: SAXParser = DLNASAXParser()
            val descriptor = MutableService()
            hydrateBasic(descriptor, undescribedService)
            RootHandler(descriptor, parser)
            parser.parse(
                    InputSource( // TODO: UPNP VIOLATION: Virgin Media Superhub sends trailing spaces/newlines after last XML element, need to trim()
                            StringReader(descriptorXml.trim { it <= ' ' })
                    )
            )
            // Build the immutable descriptor graph
            descriptor.build(undescribedService!!.getDevice()) as S
        } catch (ex: ValidationException) {
            throw ex
        } catch (ex: Exception) {
            throw DescriptorBindingException("Could not parse service descriptor: $ex", ex)
        }
    }

    companion object {
        private val TAG = DLNAUDA10ServiceDescriptorBinderSAXImpl::class.java.simpleName
    }
}