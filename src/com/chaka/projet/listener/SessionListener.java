package com.chaka.projet.listener;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		nbSessionCourantes = 0;
		nbMaxSessionSimultanees = 0;
		
	}

	private static int nbSessionCourantes = 0;

	private static int nbMaxSessionSimultanees = 0;
	
	private static Hashtable<String, HttpSession> tableauSessions = new Hashtable<String, HttpSession>();
	
	

	public void sessionCreated(HttpSessionEvent arg0) {
		nbSessionCourantes++;
		
		tableauSessions.put(arg0.getSession().getId(), arg0.getSession());

		if (nbSessionCourantes > nbMaxSessionSimultanees) {
			nbMaxSessionSimultanees = nbSessionCourantes;
		}
	}
	
	

	public static Hashtable<String, HttpSession> getTableauSessions() {
		return tableauSessions;
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		tableauSessions.remove(arg0.getSession().getId());
		nbSessionCourantes--;
	}

	public static int getNbSessionCourante() {
		return nbSessionCourantes;
	}

	public static int getNbMaxSessionSimultanees() {
		return nbMaxSessionSimultanees;
	}

}
