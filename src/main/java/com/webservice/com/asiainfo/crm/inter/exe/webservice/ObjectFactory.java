
package com.webservice.com.asiainfo.crm.inter.exe.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.asiainfo.crm.inter.exe.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Parameters_QNAME = new QName("http://webservice.exe.inter.crm.asiainfo.com", "parameters");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.asiainfo.crm.inter.exe.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.exe.inter.crm.asiainfo.com", name = "parameters")
    public JAXBElement<String> createParameters(String value) {
        return new JAXBElement<String>(_Parameters_QNAME, String.class, null, value);
    }

}
