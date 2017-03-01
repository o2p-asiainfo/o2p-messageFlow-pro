
package com.webservice.services.soaservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the _48._228._1._10._46501.services.soaservice package. 
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

    private final static QName _ExchangeReturn_QNAME = new QName("", "exchangeReturn");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: _48._228._1._10._46501.services.soaservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "exchangeReturn")
    public JAXBElement<String> createExchangeReturn(String value) {
        return new JAXBElement<String>(_ExchangeReturn_QNAME, String.class, null, value);
    }

}
