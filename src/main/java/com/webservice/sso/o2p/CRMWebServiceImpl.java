
package com.webservice.sso.o2p;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "CRMWebServiceImpl", targetNamespace = "")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CRMWebServiceImpl {


    @WebMethod(operationName = "exchange", action = "")
    @WebResult(name = "exchangeReturn", targetNamespace = "")
    public String exchange(
        @WebParam(name = "parameters", targetNamespace = "http://webservice.exe.inter.crm.asiainfo.com")
        String parameters);

}
