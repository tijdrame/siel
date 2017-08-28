
package com.chaka.projet.server;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SoapFilterHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public void close(MessageContext arg0) {
		
	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext soapContext) {
		
		Boolean outbound = (Boolean) soapContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (!outbound) {  //if incoming request
            HttpServletRequest req = (HttpServletRequest) soapContext.get(MessageContext.SERVLET_REQUEST);
            System.err.println(req.getRequestURI());
            String ipaddress = req.getRemoteAddr();
            System.err.println(ipaddress);
        }
        
        return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
