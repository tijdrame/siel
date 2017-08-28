package com.chaka.projet.print;


import static org.jboss.seam.ScopeType.APPLICATION;
import static org.jboss.seam.annotations.Install.BUILT_IN;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.Filter;
import org.jboss.seam.web.AbstractFilter;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.simple.Graphics2DRenderer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;


@SuppressWarnings("deprecation")
@Scope(APPLICATION)
@Install(precedence = BUILT_IN, classDependencies="javax.faces.context.FacesContext")
@BypassInterceptors
@Filter(within="org.jboss.seam.web.ajax4jsfFilter")
@Name("com.halesconsulting.web.filter.RendererFilter")

public class RendererFilter extends AbstractFilter {

   FilterConfig config;
   private DocumentBuilder documentBuilder;
   private static String CLASS_NAME = RendererFilter.class.getName();
   Logger log = Logger.getLogger(CLASS_NAME);

   public void init(FilterConfig config) throws ServletException {
      try {
         this.config = config;
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         documentBuilder = factory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
         throw new ServletException(e);
      }
   }


   public void doFilter(final ServletRequest request, final ServletResponse response,
                        final FilterChain chain) throws IOException, ServletException {

      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse resp = (HttpServletResponse) response;

      //Check to see if this filter should apply.
      String renderType = request.getParameter("RenderOutputType");
      String fileName = request.getParameter("FileName");
      
      
      if (renderType != null) {
         //Capture the content for this request
         ContentCaptureServletResponse capContent = new ContentCaptureServletResponse(resp);
         chain.doFilter(request, capContent);

         try {
            //Parse the XHTML content to a document that is readable by the XHTML renderer.
            StringReader contentReader = null;
            try {
               contentReader = new StringReader(capContent.getContent());
            } catch (TransformerException e) {
               e.printStackTrace();
            }
            InputSource source = new InputSource(contentReader);
            Document xhtmlContent = documentBuilder.parse(source);

            if (renderType.equals("pdf")) {
               ITextRenderer renderer = new ITextRenderer();

               String baseURL = (HttpUtils.getRequestURL(req)).toString();
               String self = baseURL.substring(0, baseURL.lastIndexOf("/") + 1);
               
               if (log.isDebugEnabled())
               {
            	   log.debug("doFilter() - pdf baseURL=[" + baseURL + "]");
                   log.debug("doFilter() - pdf self=[" + self + "]");
               }
               
               
               renderer.setDocument(xhtmlContent, self);
               renderer.layout();

               if (StringUtils.isNotBlank(fileName))
               {
            	   resp.addHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");   
               }
               
               
               response.setContentType("application/pdf");
               OutputStream browserStream = response.getOutputStream();
               renderer.createPDF(browserStream);
               
               return;
            }

            //For the other formats, you might need to specify a width and a height.
            int width = 1024;
            int height = 800;

            try{
               if(request.getParameter("width") != null) width = Integer.parseInt(request.getParameter("width"));
               if(request.getParameter("height") != null) height = Integer.parseInt(request.getParameter("height"));
            }catch(NumberFormatException ne){ /*Nothing much to do here*/}

            if (renderType.equals("image")) {
               Graphics2DRenderer renderer = new Graphics2DRenderer();

               String baseURL = (HttpUtils.getRequestURL(req)).toString();
               String self = baseURL.substring(0, baseURL.lastIndexOf("/") + 1);
               //for https behind bigip
               //self = self.replaceFirst("^https?://[a-zA-Z]*(.someurl.com)?/", "http://localhost:7000/");
               log.debug("doFilter() - pdf baseURL=[" + baseURL + "]");
               log.debug("doFilter() - pdf self=[" + self + "]");

               renderer.setDocument(xhtmlContent, self);

               BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
               Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
               imageGraphics.setColor(Color.white);
               imageGraphics.fillRect(0, 0, width, height);
               renderer.layout(imageGraphics, new Dimension(width, height));
               renderer.render(imageGraphics);
               //Now output the image to PNG using the ImageIO libraries.
               OutputStream browserStream = response.getOutputStream();
               response.setContentType("image/png");
               ImageIO.write(image, "png", browserStream);
               return;
            }

         } catch (SAXException e) {
            throw new ServletException(e);
         } catch (DocumentException e) {
            throw new ServletException(e);
         }
	       catch (Exception e) {
	          e.printStackTrace();
	     }


      } else {
         //Normal processing
         chain.doFilter(request, response);
      }

   }

   public void destroy() {
   }
}

