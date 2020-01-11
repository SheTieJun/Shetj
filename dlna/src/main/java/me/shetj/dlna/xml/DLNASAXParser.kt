package me.shetj.dlna.xml

import org.seamless.xml.SAXParser
import org.xml.sax.XMLReader
import javax.xml.parsers.SAXParserFactory

/**
 * Description：DLNASAXParser
 * <BR></BR>
 * Creator：yankebin
 * <BR></BR>
 * CreatedAt：2019-07-10
 */
class DLNASAXParser : SAXParser() {
    override fun create(): XMLReader {
        return try {
            val factory = SAXParserFactory.newInstance()
            // Configure factory to prevent XXE attacks
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            //解决创建解析器报错的问题
//            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
//            //fix bug .see https://stackoverflow.com/questions/10837706/solve-security-issue-parsing-xml-using-sax-parser
//            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//            factory.setXIncludeAware(false);
//
//            factory.setNamespaceAware(true);
            if (schemaSources != null) {
                factory.schema = createSchema(schemaSources)
            }
            val xmlReader = factory.newSAXParser().xmlReader
            xmlReader.errorHandler = errorHandler
            xmlReader
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}