package com.chaka.jollyday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

import de.jollyday.Manager;

public class HolidayUtil {
	
	private static Manager m;
	
	public static boolean isJourFerie(Date dateAVerifier)
	{
		try
		{
			GregorianCalendar gcal = (GregorianCalendar)GregorianCalendar.getInstance();
			gcal.setTime(dateAVerifier);
			if (m == null)
				m = Manager.getInstance("fr");
			
			return m.isHoliday(gcal, "jf");
			
		} catch(Exception e)
		{
			return false;
		}
		
	}
	
	
	public static List<Date> chargerJoursFeries(int annee)
	{
		try
		{
			if (m == null)
				m = Manager.getInstance("fr");
			
			Set<LocalDate> joursFeries = m.getHolidays(annee, "jf");
			
			if (joursFeries != null)
			{
				List<Date> listeRetour = new ArrayList<Date>();
				
				GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
				gcal.set(Calendar.HOUR_OF_DAY, 0);
				gcal.set(Calendar.MINUTE, 0);
				gcal.set(Calendar.SECOND, 0);
				gcal.set(Calendar.MILLISECOND, 0);
				
				for (LocalDate localDate : joursFeries)
				{
					
					gcal.set(Calendar.YEAR,  localDate.getYear());
					gcal.set(Calendar.MONTH,  localDate.getMonthOfYear());
					gcal.set(Calendar.DAY_OF_MONTH,  localDate.getDayOfMonth());
					
					listeRetour.add(gcal.getTime());
				}
				
				return listeRetour;
			}
			
			return null;
		} catch(Exception e)
		{
			return null;
		}
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// To get the US holidays for the state New York:
		try {
			
			
			chargerJoursFeries(2010);
			
			
			Manager m = Manager.getInstance("fr");
			Set<LocalDate> joursFeries = m.getHolidays(2010, "jf");
			Set<LocalDate> vacancesZoneA = m.getHolidays(2010, "za");
			System.err.println(joursFeries.size());
			System.err.println(vacancesZoneA.size());
			
			//Manager m = Manager.getInstance("us");
			//Set<LocalDate> holidays = m.getHolidays(2010, "ny");

			// To get the US holidays for New York City in the state New York:

			//Manager m2 = Manager.getInstance("us");
			//Set<LocalDate> holidays2 = m2.getHolidays(2010, "ny", "nyc");
			System.err.println("test");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
