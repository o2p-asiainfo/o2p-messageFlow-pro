
package com.webservice.sso.o2p;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.transport.TransportManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.asiainfo.foundation.common.ExceptionCommon;
import com.asiainfo.foundation.exception.BusinessException;
import com.asiainfo.foundation.log.LogModel;
import com.asiainfo.foundation.log.Logger;

public class CRMWebServiceImplServiceClient {
	
	private static final Logger log = Logger.getLog(CRMWebServiceImplServiceClient.class);
    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private List<String> list = new ArrayList<String>();
    private Service service0;
    private  String address = "http://10.1.228.48:46501/services/SOAService";

    public CRMWebServiceImplServiceClient(){
    	 create0();
         Endpoint CRMWebServiceImplLocalEndpointEP = service0 .addEndpoint(new QName(address, "CRMWebServiceImplLocalEndpoint"), new QName(address, "CRMWebServiceImplLocalBinding"), "xfire.local://CRMWebServiceImplService");
         endpoints.put(new QName(address, "CRMWebServiceImplLocalEndpoint"), CRMWebServiceImplLocalEndpointEP);
         Endpoint SOAServiceEP = service0 .addEndpoint(new QName(address, "SOAService"), new QName(address, "SOAServiceSoapBinding"), address);
         endpoints.put(new QName(address, "SOAService"), SOAServiceEP);
    }
    public CRMWebServiceImplServiceClient(String commonAddress) {
    	address = commonAddress;
        create0();
        Endpoint CRMWebServiceImplLocalEndpointEP = service0 .addEndpoint(new QName(address, "CRMWebServiceImplLocalEndpoint"), new QName(address, "CRMWebServiceImplLocalBinding"), "xfire.local://CRMWebServiceImplService");
        endpoints.put(new QName(address, "CRMWebServiceImplLocalEndpoint"), CRMWebServiceImplLocalEndpointEP);
        Endpoint SOAServiceEP = service0 .addEndpoint(new QName(address, "SOAService"), new QName(address, "SOAServiceSoapBinding"), address);
        endpoints.put(new QName(address, "SOAService"), SOAServiceEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((com.webservice.sso.o2p.CRMWebServiceImpl.class), props);
        {
            asf.createSoap11Binding(service0, new QName(address, "CRMWebServiceImplLocalBinding"), "urn:xfire:transport:local");
        }
        {
             asf.createSoap11Binding(service0, new QName(address, "SOAServiceSoapBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public CRMWebServiceImpl getCRMWebServiceImplLocalEndpoint() {
        return ((CRMWebServiceImpl)(this).getEndpoint(new QName(address, "CRMWebServiceImplLocalEndpoint")));
    }

    public CRMWebServiceImpl getCRMWebServiceImplLocalEndpoint(String url) {
        CRMWebServiceImpl var = getCRMWebServiceImplLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public CRMWebServiceImpl getSOAService() {
        return ((CRMWebServiceImpl)(this).getEndpoint(new QName(address, "SOAService")));
    }

    public CRMWebServiceImpl getSOAService(String url) {
        CRMWebServiceImpl var = getSOAService();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }
    
    
    public List<String> getOperatorMenu(String soap){
    	CRMWebServiceImplServiceClient obj = new CRMWebServiceImplServiceClient();
    	CRMWebServiceImpl crm = obj.getSOAService();
    	List<String> menuList = null;
    	Document doc = null;
    	try {
			doc = DocumentHelper.parseText(crm.exchange(soap));
			menuList = obj.readXml(doc);
		} catch (DocumentException e) {
			log.error(LogModel.EVENT_APP_EXCPT, new BusinessException(ExceptionCommon.WebExceptionCode,e.getMessage(),null));
		}
    	return menuList;
    }
    private List<String> readXml(Document doc) {
		Element root = doc.getRootElement();
		return iteratorJsonXml(root);
	}
    private List<String> iteratorJsonXml(Element element){
		for(Iterator<Element> sonElement = element.elementIterator();sonElement.hasNext();){
			 Element son = sonElement.next();
			 String nodeName = son.getQualifiedName();
			 if("ViewName".equals(nodeName)){
				 String path = son.getText();
				 if(null != path && !"".equals(path)){
					 list.add(path);
				 }
			 }
			 iteratorJsonXml(son);
		 }
		return list;
	}
}
