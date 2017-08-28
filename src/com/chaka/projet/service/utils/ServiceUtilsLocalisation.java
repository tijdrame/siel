package com.chaka.projet.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ServiceUtilsLocalisation {

	public static int calculerDistanceEnMetres(String origine,
			String destination) {
		if (StringUtils.isBlank(origine) || StringUtils.isBlank(destination))
			return 0;

		URL google;

		try {
			origine = URLEncoder.encode(origine, "UTF-8");
			destination = URLEncoder.encode(destination, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}

		String url = "origins=" + origine + "&destinations=" + destination
				+ "&mode=driving&language=fr-FR&sensor=false";

		try {
			google = new URL("http://maps.googleapis.com/maps/api/distancematrix/xml?" + url);

			URLConnection gc = google.openConnection();

			InputStream is = gc.getInputStream();

			XPathFactory factory = XPathFactory.newInstance();

			XPath xpath = factory.newXPath();

			InputSource inputXml = new InputSource(is);

			NodeList nodes = (NodeList) xpath.evaluate("DistanceMatrixResponse//distance//value", inputXml, XPathConstants.NODESET);

			// We can then iterate over the NodeList and extract the content via
			// getTextContent().
			// NOTE: this will only return text for element nodes at the
			// returned context.
			//for (int i = 0, n = nodes.getLength(); i < n;) {
				String nodeString = nodes.item(0).getTextContent();

				is.close();
				
				if (StringUtils.isBlank(nodeString))
					return 0;

				else {
					return Integer.parseInt(nodeString);
				}

			//}

			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return 0;

	}
	
public static double[] getLatitudeLongitude(String adresse) {
		
		if (StringUtils.isBlank(adresse))
			return new double[]{0,0};

		URL google;

		try {
			adresse = URLEncoder.encode(adresse, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new double[]{0,0};
		}

		String url = "&address=" + adresse;

		try {
			google = new URL(
					"http://maps.googleapis.com/maps/api/geocode/xml?sensor=false&"
							+ url);

			URLConnection gc = google.openConnection();

			InputStream is = gc.getInputStream();
			
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder;
			Document doc = null;
			XPathExpression expr = null;
			builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
			
			double latitude = 0;
			double longitude = 0;
			
			// Create a XPathFactory
     		XPathFactory xFactory = XPathFactory.newInstance();

     		// Create a XPath object
     		XPath xpath = xFactory.newXPath();
			
     		expr = xpath.compile("//GeocodeResponse/result/geometry/location/lat/text()");
     		
     		NodeList nodeLatitude = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
     		if (nodeLatitude != null && nodeLatitude.getLength() > 0)
     		{
     			String nodeString = nodeLatitude.item(0).getTextContent();
     			
     			if (StringUtils.isNotBlank(nodeString))
     				latitude = Double.parseDouble(nodeString);
     		}
     		
     		
     		expr = xpath.compile("//GeocodeResponse/result/geometry/location/lng/text()");
     		
     		NodeList nodeLongitude = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
     		if (nodeLongitude != null && nodeLongitude.getLength() > 0)
     		{
     			String nodeString = nodeLongitude.item(0).getTextContent();
     			
     			if (StringUtils.isNotBlank(nodeString))
     				longitude = Double.parseDouble(nodeString);
     		}
			
     		is.close();
     		
     		return new double[]{latitude,longitude};
     		

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return new double[]{0,0};

	}

}
