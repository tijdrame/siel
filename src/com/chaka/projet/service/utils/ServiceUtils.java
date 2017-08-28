package com.chaka.projet.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;

import com.aspose.words.CellVerticalAlignment;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.HeightRule;
import com.aspose.words.License;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.RowAlignment;

public class ServiceUtils {


	public static final String caractereEuro = "\u20AC";
	
	private static final String propertiesFileName = "chaka.properties";

	private static Hashtable<String, String> tableauParametres;

	public static long nbMillisecondesParJour = 86400000; 

	private static GregorianCalendar gcal;


	public static Integer nbMilliSecondParJour = 86400000;

	public static Double nbPassageHebdoVersMensuel = 13.0 / 3.0;

	public static Double nbPassageMensuelVersHebdo = 3.0 / 13.0;


	static {
		initParameters();
	}

	static {

		gcal = (GregorianCalendar) GregorianCalendar.getInstance();
		gcal.set(Calendar.SECOND, 0);
		gcal.set(Calendar.MILLISECOND, 0);

	}



	private static void initParameters() {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);

		Properties prop = new Properties();
		try {
			prop.load(is);

			for (Entry<Object, Object> entry : prop.entrySet()) {
				ajouterParametre((String) entry.getKey(), (String) entry.getValue());
			}

			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static int calculerNbMoisEntreDeuxDates(Date dateDebut, Date dateFin)
	{
		GregorianCalendar gcalDebut = (GregorianCalendar)GregorianCalendar.getInstance();
		GregorianCalendar gcalFin = (GregorianCalendar)GregorianCalendar.getInstance();


		gcalDebut.setTime(dateDebut);
		gcalFin.setTime(dateFin);

		int nbMois = 1 + ((gcalFin.get(Calendar.YEAR) - gcalDebut.get(Calendar.YEAR)) * 12) + (gcalFin.get(Calendar.MONTH) - gcalDebut.get(Calendar.MONTH)) ;

		return nbMois;
	}


	public static boolean verifierRib(String codeBanque, String codeGuichet, String numeroCompte, String cleRib)
	{
		if (StringUtils.isBlank(codeBanque))
			return false;

		if (StringUtils.isBlank(codeGuichet))
			return false;

		if (StringUtils.isBlank(numeroCompte))
			return false;

		if (StringUtils.isBlank(cleRib))
			return false;



		try
		{
			String codeBanqueFinal = codeBanque.trim();
			String codeGuichetFinal = codeGuichet.trim();
			String numeroCompteFinal = numeroCompte.trim().toUpperCase();
			String cleRibFinal = cleRib.trim();

			int codeBanqueNumerique = Integer.parseInt(codeBanqueFinal);
			int codeGuichetNumerique = Integer.parseInt(codeGuichetFinal);

			String numeroCompteFinal2 = StringUtils.replaceEach(numeroCompteFinal, 
					new String[] { "A", "J", "B", "K", "S", "C", "L", "T", "D", "M", "U", "E", "N", "V", "F", "O", "W", "G", "P", "X", "H", "Q", "Y", "I", "R", "Z" }, 
					new String[] { "1", "1", "2", "2", "2", "3", "3", "3", "4", "4", "4", "5", "5", "5", "6", "6", "6", "7", "7", "7", "8", "8", "8", "9", "9", "9" });

			long numeroCompteNumerique = Long.parseLong(numeroCompteFinal2);
			long cleRibNumerique = Long.parseLong(cleRibFinal);

			long cleAttendue = 97 - ((89 * codeBanqueNumerique + 15 * codeGuichetNumerique + 3 * numeroCompteNumerique) % 97);

			return cleRibNumerique == cleAttendue;


		} catch(NumberFormatException e)
		{
			return false;

		} catch(Throwable e)
		{
			e.printStackTrace();
			return false;
		}

	}


	public static boolean enProduction()
	{
		if ("true".equals(ServiceUtils.chargerParametre("modeProduction")))
		{
			return true;
		}

		return false;
	}

	private static void ajouterParametre(String cle, String valeur) {
		if (tableauParametres == null) {
			tableauParametres = new Hashtable<String, String>();
		}

		tableauParametres.put(cle, valeur);
	}

	public static String chargerParametre(String cle) {
		if (tableauParametres != null)
			return tableauParametres.get(cle);

		return null;
	}



	public static Double arrondir(Double nbAArrondir, int nbDecimales) {

		if (nbAArrondir == null)
			return null;

		double r = (Math.round(nbAArrondir * Math.pow(10, nbDecimales))) / (Math.pow(10, nbDecimales));
		return r;
	}

	public static int calculerNbMoisLargeEntreDeuxDates(Date dateDebut, Date dateFin)
	{
		GregorianCalendar gcalDebut = (GregorianCalendar)GregorianCalendar.getInstance();
		GregorianCalendar gcalFin = (GregorianCalendar)GregorianCalendar.getInstance();

		gcalDebut.setTime(dateDebut);
		gcalFin.setTime(dateFin);

		int nbMois = 1 + ((gcalFin.get(Calendar.YEAR) - gcalDebut.get(Calendar.YEAR)) * 12) + (gcalFin.get(Calendar.MONTH) - gcalDebut.get(Calendar.MONTH)) ;

		return nbMois;
	}

	/**
	 * 
	 * @param texte : texte &agrave; right subtringuer
	 * @param size : longueur
	 * @return : retoure la chaine de caract&egrave;re de longueur size compl&eacute;ter par des espaces si besoin
	 */
	public static String rightSubPad(String texte, int size)
	{
		if (texte == null)
			texte = "";
		String retour = StringUtils.rightPad(texte, size);
		return StringUtils.substring(retour, 0, size);
	}

	/**
	 * 
	 * @param texte : texte &agrave; right subtringuer
	 * @param size : longueur
	 * @param remplacement : caractères de remplacement
	 * @return : retoure la chaine de caract&egrave;re de longueur size compl&eacute;ter par des espaces si besoin
	 */
	public static String rightSubPad(String texte, int size, String remplacement)
	{
		if (texte == null)
			texte = "";
		String retour = StringUtils.rightPad(texte, size, remplacement);
		return StringUtils.substring(retour, 0, size);
	}


	public static Date getDateJourSansHeure() {
		GregorianCalendar gcalTemp = (GregorianCalendar) GregorianCalendar.getInstance();
		gcalTemp.set(Calendar.HOUR_OF_DAY, 0);
		gcalTemp.set(Calendar.MINUTE, 0);
		gcalTemp.set(Calendar.SECOND, 0);
		gcalTemp.set(Calendar.MILLISECOND, 0);

		return gcalTemp.getTime();
	}

	public static Double convertCentiemeToMinute(Double centiemeAConvertir) {

		if (centiemeAConvertir == null)
			return null;

		double partieEntiere = Math.floor(centiemeAConvertir);
		double partieDecimale = Math.round((centiemeAConvertir - partieEntiere) * 600 / 10);

		double result = partieEntiere + partieDecimale / 100;

		return result;
	}

	public static long calculerDureeMinutesPeriode(String heureDebut, String heureFin) {
		Date dateHeureDebut = chargerDateDepuisHeure(heureDebut);
		Date dateHeureFin = chargerDateDepuisHeure(heureFin);

		if (dateHeureFin.before(dateHeureDebut))
		{
			GregorianCalendar gcalTemp = (GregorianCalendar) GregorianCalendar.getInstance();
			gcalTemp.setTime(dateHeureFin);
			gcalTemp.add(Calendar.DATE, 1);
			dateHeureFin =  gcalTemp.getTime();
		}

		return calculerDureeMinutes(dateHeureDebut, dateHeureFin);
	}






	public static long calculerNbSemaines(Date dateDebut, Date dateFin)
	{

		long dureeMillisecondes = dateFin.getTime() - dateDebut.getTime();

		long nbJours = dureeMillisecondes /  nbMillisecondesParJour ;

		long nbSemaines = nbJours / 7;

		long nbJoursRestants = nbJours % 7;

		if (nbJoursRestants != 0)
			return nbSemaines + 1;

		return nbSemaines;


	}




	public static String calculerDureeMinutesPeriodePretty(String heureDebut, String heureFin) {

		Long dureeTotale = calculerDureeMinutesPeriode(heureDebut, heureFin);

		if (dureeTotale != null) {
			if (dureeTotale > 0L) {
				if (dureeTotale >= 60) {
					long nbHeures = dureeTotale / 60;
					long modulo = 60 * nbHeures;
					long minutes = dureeTotale % modulo;

					if (minutes > 9) {
						return nbHeures + "h" + minutes + "min";
					}
					return nbHeures + "h0" + minutes + "min";

				}
				if (dureeTotale > 9) {
					return "0h" + dureeTotale + "min";
				}
				return "0h0" + dureeTotale + "min";
			}
		}

		return "0h00min";
	}


	public static String calculerDureePrettySurDeuxDates(Date dateDebut, Date dateFin)
	{
		Long dureeTotale = calculerDureeMinutes(dateDebut, dateFin);

		if (dureeTotale != null) {
			if (dureeTotale > 0L) {
				if (dureeTotale >= 60) {
					long nbHeures = dureeTotale / 60;
					long modulo = 60 * nbHeures;
					long minutes = dureeTotale % modulo;

					if (minutes > 9) {
						return nbHeures + "h" + minutes + "min";
					}
					return nbHeures + "h0" + minutes + "min";

				}
				if (dureeTotale > 9) {
					return "0h" + dureeTotale + "min";
				}
				return "0h0" + dureeTotale + "min";
			}
		}

		return "0h00min";
	}


	public static long calculerDureeMinutes(String dureeeHeures, String dureeMinutes) {
		NumberFormat nf = new DecimalFormat();
		try {
			Long dureeTotaleMinutes = nf.parse(dureeMinutes).longValue();
			Long dureeTotaleHeure = nf.parse(dureeeHeures).longValue();

			dureeTotaleMinutes += dureeTotaleHeure * 60;

			return dureeTotaleMinutes;

		} catch (ParseException e) {
			return 0;
		}
	}



	public static long calculerDureeMinutes(Date dateDebut, Date dateFin) {
		long dureeMillisecondes = dateFin.getTime() - dateDebut.getTime();
		return dureeMillisecondes / 60000;
	}

	public static long calculerDureeJours(Date dateDebut, Date dateFin) {
		long dureeMillisecondes = dateFin.getTime() - dateDebut.getTime();
		return dureeMillisecondes / (nbMillisecondesParJour);
	}

	private static Date chargerDateDepuisHeure(String heureString) {
		gcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heureString.substring(0, 2)));
		gcal.set(Calendar.MINUTE, Integer.parseInt(heureString.substring(3)));

		return gcal.getTime();
	}

	public static boolean afficherDisponibilite()
	{
		if ("true".equals(ServiceUtils.chargerParametre("modeDisponibilite")))
		{
			return true;
		}

		return false;
	}

	

	public static String afficherDureeMinutesEnHeures(Long totalMinutes) {
		if (totalMinutes == null)
			return "0h00min";

		if (totalMinutes >= 60) {
			long nbHeures = totalMinutes / 60;
			long modulo = 60 * nbHeures;
			long minutes = totalMinutes % modulo;

			if (minutes > 9) {
				return nbHeures + "h" + minutes + "min";
			}
			return nbHeures + "h0" + minutes + "min";

		}
		if (totalMinutes > 9) {
			return "0h" + totalMinutes + "min";
		}
		return "0h0" + totalMinutes + "min";
	}

	public static String afficherDureeMinutesEnHeuresCourt(Long totalMinutes) {
		if (totalMinutes == null)
			return "00h00";

		if (totalMinutes >= 60) {
			long nbHeures = totalMinutes / 60;
			long modulo = 60 * nbHeures;
			long minutes = totalMinutes % modulo;

			if (minutes > 9) {

				if (nbHeures > 9)
					return nbHeures + "h" + minutes + "";
				else
					return "0" + nbHeures + "h" + minutes + "";
			}
			if (nbHeures > 9)
				return nbHeures + "h0" + minutes + "";
			else
				return "0" + nbHeures + "h0" + minutes + "";

		}

		if (totalMinutes > 9) {
			return "00h" + totalMinutes;
		}
		return "00h0" + totalMinutes;
	}

	public static String calculerDureeMinutesEnHeuresPourPlanification(Long totalMinutes) {
		if (totalMinutes == null)
			return "00h00";

		if (totalMinutes >= 60) {
			long nbHeures = totalMinutes / 60;
			long modulo = 60 * nbHeures;
			long minutes = totalMinutes % modulo;

			if (minutes > 9) {

				if (nbHeures>9)
					return nbHeures + "h" + minutes;
				return "0" + nbHeures + "h" + minutes;
			}

			if (nbHeures>9)
				return nbHeures + "h0" + minutes;
			return "0" + nbHeures + "h0" + minutes;

		}
		if (totalMinutes > 9) {
			return "00h" + totalMinutes;
		}
		return "00h0" + totalMinutes;
	}






	public static InternetAddress isValidEmail(String email) {
		try {
			if (StringUtils.isBlank(email)) {
				return null;
			}
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			return internetAddress;
	
		} catch (AddressException ex) {
			return null;
		}
	}


	public static double convertirMmEnPoint(Integer distance) {
		return distance * 2.8346456693;
	}


	public static DocumentBuilder initDocumentBuilderPourEtiquette(double longeurEtiquetteBeneficiaireEnPoints, double largeurEtiquetteBeneficiaireEnPoints)
			throws Exception {
		License license = new License();
		license.setLicense("Aspose.Words.Java.lic");
		Document etiquette = new Document();

		DocumentBuilder builder = new DocumentBuilder(etiquette);

		builder.getRowFormat().setAlignment(RowAlignment.CENTER);

		builder.getPageSetup().setLeftMargin(0);
		builder.getPageSetup().setRightMargin(0);
		builder.getPageSetup().setBottomMargin(0);
		builder.getPageSetup().setTopMargin(0);

		builder.getFont().setName("Times New Roman");
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		builder.getFont().setSize(14);
		builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
		builder.getCellFormat().setWidth(largeurEtiquetteBeneficiaireEnPoints);
		builder.getRowFormat().setHeightRule(HeightRule.EXACTLY);
		builder.getRowFormat().setHeight(longeurEtiquetteBeneficiaireEnPoints);

		return builder;

	}


	public static HashMap<String, Date> calculerPeriodesFacturation(Date periodeFacturation, Short premierJourFacturation)
	{
		if (periodeFacturation == null)
			return null;

		if (premierJourFacturation == null)
			premierJourFacturation = 1;


		HashMap<String, Date> retour = new HashMap<String, Date>(2);

		// On recherche le mois sélectionner :
		GregorianCalendar gcalRechercheMois = (GregorianCalendar)GregorianCalendar.getInstance();
		gcalRechercheMois.setTime(periodeFacturation);

		int moisFacturation = gcalRechercheMois.get(Calendar.MONTH);
		int anneeFacturation = gcalRechercheMois.get(Calendar.YEAR);

		GregorianCalendar gcal = (GregorianCalendar)GregorianCalendar.getInstance();
		gcal.set(Calendar.YEAR, anneeFacturation);
		gcal.set(Calendar.MONTH, moisFacturation);
		gcal.set(Calendar.HOUR_OF_DAY, 0);
		gcal.set(Calendar.MINUTE, 0);
		gcal.set(Calendar.SECOND, 0);
		gcal.set(Calendar.MILLISECOND, 0);

		Date dateDebut = null;
		Date dateFin = null;

		if (premierJourFacturation < 15)
		{
			gcal.set(Calendar.DATE, premierJourFacturation);

			dateDebut = gcal.getTime();

			gcal.add(Calendar.MONTH, 1);
			gcal.add(Calendar.DATE, -1);

			dateFin = gcal.getTime();

		} else {

			gcal.set(Calendar.DATE, premierJourFacturation);

			gcal.add(Calendar.DATE, -1);

			dateFin = gcal.getTime();


			gcal.set(Calendar.DATE, premierJourFacturation);
			gcal.set(Calendar.MONTH, moisFacturation - 1);


			dateDebut = gcal.getTime();

		}

		retour.put("dateDebut", dateDebut);
		retour.put("dateFin", dateFin);



		return retour;
	}

	

	

}
