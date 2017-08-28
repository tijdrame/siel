package com.chaka.projet.service.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Name("serviceUtilsSms")
@Scope(ScopeType.STATELESS)
public class ServiceUtilsSms implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8960908235069432624L;

	public String formaterTextSms(String texte)
	{
		String retour = "";
		
		if(StringUtils.isNotBlank(texte))
		{
			retour = texte;
			retour = StringUtils.replaceEach(retour, new String[]{"\n"}, new String[]{"<br/>"});
		}
		
		return retour;
	}

	
	
	public void envoyerUnSms(String texte, String numero, String alias)
	{
		
		URL smsURL;
		
		try {

			String texteSms = URLEncoder.encode(texte, "UTF-8");
			
			String data = "text=" + texteSms + "&recipients=" + numero + "&sendername=" + alias + "&longmessageallowed=1";
			
			smsURL = new URL("https://www.envoyersmspro.com/api/message/send");
			
			String xmlString = "";
			
	        HttpURLConnection connection = (HttpURLConnection)smsURL.openConnection();
	        String auth = new String("33612191234:9999");
	        connection.setRequestProperty ("Authorization", "Basic " + Base64.encodeBase64String(auth.getBytes()));
	        
	        
	        connection.setDoOutput(true);
	        
	        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        

	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) 
	        	xmlString += inputLine;
	        in.close();
	        
	        XPathFactory factory = XPathFactory.newInstance();

	        XPath xpath = factory.newXPath();

	        InputSource is = new InputSource(new ByteArrayInputStream(xmlString.getBytes()));
        	Node statusNode = (Node) xpath.evaluate("//status", is, XPathConstants.NODE);

        	is = new InputSource(new ByteArrayInputStream(xmlString.getBytes()));
        	@SuppressWarnings("unused")
			Node msgIdNode = (Node) xpath.evaluate("//message_id", is, XPathConstants.NODE);
        	
        	is = new InputSource(new ByteArrayInputStream(xmlString.getBytes()));
        	@SuppressWarnings("unused")
			Node nbSmsSentNode = (Node) xpath.evaluate("//sms_sent", is, XPathConstants.NODE);
        	
			if ("success".equalsIgnoreCase(statusNode.getTextContent()))
			{
				//TODO:
				
			} else {
				// y a une erreur quelque part
				//TODO:
				
				is = new InputSource(new ByteArrayInputStream(xmlString.getBytes()));
	        	@SuppressWarnings("unused")
				Node nbErrorMsgNode = (Node) xpath.evaluate("//error_message", is, XPathConstants.NODE);
			}
			
		
		
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
			
			if (e.getMessage().contains("401"))
			{
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public void statSms(String messageId)
	{
		
		URL smsURL;
		
		try {

			String data = "messageid=" + messageId;
			
			smsURL = new URL("https://www.envoyersmspro.com/api/message/stats");
			
			String xmlString = "";
			
	        HttpURLConnection connection = (HttpURLConnection)smsURL.openConnection();
	        String auth = new String("33612191234:9999");
	        connection.setRequestProperty ("Authorization", "Basic " + Base64.encodeBase64String(auth.getBytes()));
	        
	        
	        connection.setDoOutput(true);
	        
	        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        

	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) 
	        	xmlString += inputLine;
	        in.close();
	        
	        XPathFactory factory = XPathFactory.newInstance();

	        XPath xpath = factory.newXPath();

	        InputSource is = new InputSource(new ByteArrayInputStream(xmlString.getBytes()));
        	NodeList listeNode = (NodeList) xpath.evaluate("//recipient", is, XPathConstants.NODESET);

        	
        	
        	for (int i = 0; i<listeNode.getLength(); i++)
        	{
        		Node noeud = listeNode.item(i);
        		@SuppressWarnings("unused")
				String statusCode = ((Node)xpath.evaluate("status_id", noeud, XPathConstants.NODE)).getTextContent(); 
        		@SuppressWarnings("unused")
				String mobileNumber = ((Node)xpath.evaluate("mobile_number", noeud, XPathConstants.NODE)).getTextContent();
        		@SuppressWarnings("unused")
				String delivered_date = ((Node)xpath.evaluate("delivered_date", noeud, XPathConstants.NODE)).getTextContent();
        		
        		//TODO: traiter l'info
        		
        	}
        	
		
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
			
			if (e.getMessage().contains("401"))
			{
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			
		}
		
		
	}

}
