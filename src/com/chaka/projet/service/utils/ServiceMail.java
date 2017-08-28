package com.chaka.projet.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;


public class ServiceMail {
	
	private static final String propertiesFileName = "mail.properties";
	
	private static Hashtable<String, String> tableauParametres;
	
	static {
		initParameters();
	}
	
	private static void initParameters()
	{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);
		
		Properties prop = new Properties();
		try {
			prop.load(is);
			
			
			for (Entry<Object, Object> entry : prop.entrySet())
			{
				ajouterParametre((String)entry.getKey(), (String)entry.getValue());
			}
			
			is.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void ajouterParametre(String cle, String valeur)
	{
		if (tableauParametres == null)
		{
			tableauParametres = new Hashtable<String, String>();
		}
		
		tableauParametres.put(cle, valeur);
	}
	
	public static String chargerParametre(String cle)
	{
		if (tableauParametres != null)
			return tableauParametres.get(cle);
		
		return null;
	}
	

	public static void envoyerMail(String objet, String contenu, String expediteur)
	{
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", chargerParametre("smtp.serveur"));
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
	    props.setProperty("mail.smtp.user", chargerParametre("mail.user"));
	    props.setProperty("mail.smtp.debug", "false");
	    props.setProperty("mail.smtp.password", chargerParametre("mail.password"));
	    props.setProperty("mail.smtp.port", chargerParametre("smtp.port"));

		Session session = Session.getDefaultInstance(props,null);
		session.setDebug(false);
		try {
			Transport tr = session.getTransport("smtp");
			tr.connect(chargerParametre("smtp.serveur"),Integer.parseInt(chargerParametre("smtp.port")), chargerParametre("mail.user"), chargerParametre("mail.password"));
			
			MimeMessage message = new MimeMessage(session);
			message.setSubject(objet, "UTF-8");
			message.setFrom(new InternetAddress(chargerParametre("mail.user")));
			
			if (StringUtils.isNotBlank(expediteur))
			{
				InternetAddress[] expediteurs = {new InternetAddress(expediteur)};
				message.setReplyTo(expediteurs);	
			}
			
			
			InternetAddress[] destinataires = {new InternetAddress(chargerParametre("mail.support"))};
			message.setRecipients(RecipientType.TO, destinataires);
			
			InternetAddress[] destinatairesCC = {new InternetAddress(chargerParametre("mail.supportCC"))};
			message.setRecipients(RecipientType.BCC, destinatairesCC);
			
			BodyPart mbp = new MimeBodyPart();
			mbp.setContent(contenu, "text/html");
			mbp.setContent(contenu, "text/html;charset=\"utf-8\"");
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);
			
			message.setContent(mp);
			
			
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void envoyerMailEvolution(String objet, String contenu, String expediteur)
	{
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", chargerParametre("smtp.serveur"));
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
	    props.setProperty("mail.smtp.user", chargerParametre("mail.user"));
	    props.setProperty("mail.smtp.debug", "false");
	    props.setProperty("mail.smtp.password", chargerParametre("mail.password"));
	    props.setProperty("mail.smtp.port", chargerParametre("smtp.port"));

		Session session = Session.getDefaultInstance(props,null);
		session.setDebug(false);
		try {
			Transport tr = session.getTransport("smtp");
			tr.connect(chargerParametre("smtp.serveur"),Integer.parseInt(chargerParametre("smtp.port")), chargerParametre("mail.user"), chargerParametre("mail.password"));
			
			MimeMessage message = new MimeMessage(session);
			message.setSubject(objet, "UTF-8");
			message.setFrom(new InternetAddress(chargerParametre("mail.user")));
			
			if (StringUtils.isNotBlank(expediteur))
			{
				InternetAddress[] expediteurs = {new InternetAddress(expediteur)};
				message.setReplyTo(expediteurs);	
			}
			
			
			InternetAddress[] destinataires = {new InternetAddress(chargerParametre("mail.supportevolution"))};
			message.setRecipients(RecipientType.TO, destinataires);
			
			InternetAddress[] destinatairesCC = {new InternetAddress(chargerParametre("mail.supportCCevolution"))};
			message.setRecipients(RecipientType.BCC, destinatairesCC);
			
			BodyPart mbp = new MimeBodyPart();
			mbp.setContent(contenu, "text/html");
			mbp.setContent(contenu, "text/html;charset=\"utf-8\"");
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);
			
			message.setContent(mp);
			
			
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void envoyerMailSansPieceJointe(String objet, String contenu, InternetAddress[] destinataires, InternetAddress fromAdress, InternetAddress[] replyTo, InternetAddress[] enCopie,
			Map<String, DataSource> listePiecesJointes)
	{
		// traitement des sauts de lignes :
		if (contenu != null)
			contenu = StringUtils.replaceEach(contenu, new String[]{"\r", "\n"}, new String[]{" ", "<br/>"});
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", chargerParametre("smtp.serveur"));
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
	    props.setProperty("mail.smtp.user", chargerParametre("mail.user"));
	    props.setProperty("mail.smtp.debug", "false");
	    props.setProperty("mail.smtp.password", chargerParametre("mail.password"));
	    props.setProperty("mail.smtp.port", chargerParametre("smtp.port"));

		Session session = Session.getDefaultInstance(props,null);
		session.setDebug(false);
		try {
			Transport tr = session.getTransport("smtp");
			tr.connect(chargerParametre("smtp.serveur"),Integer.parseInt(chargerParametre("smtp.port")), chargerParametre("mail.user"), chargerParametre("mail.password"));
			MimeMessage message = new MimeMessage(session);
			message.setSubject(objet);
			message.setFrom(fromAdress);
			
			
			if (replyTo == null || replyTo.length == 0)
			{
				InternetAddress[] expediteurs = {fromAdress};
				message.setReplyTo(expediteurs);
			} else {
				message.setReplyTo(replyTo);
			}
			
			if (enCopie != null && enCopie.length > 0)
			{
				message.addRecipients(RecipientType.CC, enCopie);
			}
			
			
			
			if (ServiceUtils.enProduction())
			{
				if (destinataires != null)
				{
					if (destinataires.length > 1)
					{
						message.addRecipients(RecipientType.BCC, destinataires);		
					} else {
						message.addRecipients(RecipientType.TO, destinataires);
					}
					
				}
				
			} else {
				//bouchon d'envoi d'email
				InternetAddress[] destinataireUnique = {new InternetAddress("support@chaka.fr")};
				message.addRecipients(RecipientType.TO, destinataireUnique);
			}
			
			
			BodyPart mbp = new MimeBodyPart();
			mbp.setContent(contenu, "text/html");
			mbp.setContent(contenu, "text/html;charset=\"utf-8\"");
			
		    
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);
			
			if (listePiecesJointes != null && listePiecesJointes.size() > 0 )
			{
				for (String nomPieceJointe : listePiecesJointes.keySet())
				{
					MimeBodyPart pieceJointe = new MimeBodyPart();
					pieceJointe.setFileName(nomPieceJointe);
					pieceJointe.setDataHandler(new DataHandler(listePiecesJointes.get(nomPieceJointe)));
					mp.addBodyPart(pieceJointe);
				}
				
			}
			
			message.setContent(mp);
			
			
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

	/**
	 * Envoyer des invitations
	 * 
	 */
	public void test()
	{
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "auth.smtp.1and1.fr");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
	    props.setProperty("mail.smtp.user", "mailauto@chaka.fr");
	    props.setProperty("mail.smtp.debug", "false");
	    props.setProperty("mail.smtp.password", "mailchaka");
	    props.setProperty("mail.smtp.port", "25");

		Session session = Session.getDefaultInstance(props,null);
		session.setDebug(false);
		
		//register the text/calendar mime type
	    MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap)MimetypesFileTypeMap.getDefaultFileTypeMap();
	    mimetypes.addMimeTypes("text/calendar ics ICS");
		
	    //register the handling of text/calendar mime type
	    MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
	    mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");

	    
	    try
	    {
	    	MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress("admin@chaka.fr"));
		    message.setSubject("test sujet");
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress("admin@chaka.fr"));
		    
		    
		    // Create an alternative Multipart
		    Multipart multipart = new MimeMultipart("alternative");
		    
		    //part 1, html text
		    BodyPart messageBodyPart = buildHtmlTextPart();
		    multipart.addBodyPart(messageBodyPart);
		    
		    
		    // Add part two, the calendar
		    BodyPart calendarPart = buildCalendarPart();
		    multipart.addBodyPart(calendarPart);
		    
		  //	Put the multipart in message 
		    message.setContent(multipart);
		 
		    // send the message
		    Transport transport = session.getTransport("smtp");
		    transport.connect("auth.smtp.1and1.fr",25, "mailauto@chaka.fr", "mailchaka");
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		    
		    
		    
		    
	    } catch(Throwable t)
	    {
	    	t.printStackTrace();
	    }
	    
	    

	    
	    
	    
		
		
	}
	
	
	
	private BodyPart buildHtmlTextPart() throws MessagingException {
		 
        MimeBodyPart descriptionPart = new MimeBodyPart();
 
        //Note: even if the content is spcified as being text/html, outlook won't read correctly tables at all
        // and only some properties from div:s. Thus, try to avoid too fancy content
        String content = "<font size=\"2\">ceci est une invitation</font>";
        descriptionPart.setContent(content, "text/html; charset=utf-8");
 
        return descriptionPart;
    }
	
	
	//define somewhere the icalendar date format
    private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
 
    private BodyPart buildCalendarPart() throws Exception {
 
        BodyPart calendarPart = new MimeBodyPart();
 
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date start = cal.getTime();
        cal.add(Calendar.HOUR_OF_DAY, 3);
        Date end = cal.getTime();
 
        //check the icalendar spec in order to build a more complicated meeting request
        String calendarContent =
                "BEGIN:VCALENDAR\n" +
                        "METHOD:REQUEST\n" +
                        "PRODID: PROGIS@P - Rendez-Vous\n" +
                        "VERSION:2.0\n" +
                        "BEGIN:VEVENT\n" +
                        "DTSTAMP:" + iCalendarDateFormat.format(start) + "\n" +
                        "DTSTART:" + iCalendarDateFormat.format(start)+ "\n" +
                        "DTEND:"  + iCalendarDateFormat.format(end)+ "\n" +
                        "SUMMARY:test request\n" +
                        "UID:324\n" +
                        "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE:MAILTO:organizer@yahoo.com\n" +
                        "ORGANIZER:MAILTO:organizer@yahoo.com\n" +
                        "LOCATION:on the net\n" +
                        "DESCRIPTION:learn some stuff\n" +
                        "SEQUENCE:0\n" +
                        "PRIORITY:5\n" +
                        "CLASS:PUBLIC\n" +
                        "STATUS:CONFIRMED\n" +
                        "TRANSP:OPAQUE\n" +
                        "BEGIN:VALARM\n" +
                        "ACTION:DISPLAY\n" +
                        "DESCRIPTION:REMINDER\n" +
                        "TRIGGER;RELATED=START:-PT00H15M00S\n" +
                        "END:VALARM\n" +
                        "END:VEVENT\n" +
                        "END:VCALENDAR";
 
        calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
        calendarPart.setContent(calendarContent, "text/calendar;method=CANCEL");
 
        return calendarPart;
    }
	

}
