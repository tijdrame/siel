package com.chaka.projet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chaka.projet.entity.Utilisateur;
import com.chaka.projet.listener.SessionListener;

public class InfoServlet extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7065598255517738503L;

	private static final int coefficient = 1048576;

	private static final int coefficient2 = 1000000;

	private PrintWriter pw = null;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	private void printEntete() {
		pw.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		pw.write("<title>Chaka - Informations</title><br/>");
	}

	private void beginTableau() {
		pw.write("<table border='1' >");
	}

	private void endTableau() {
		pw.write("</table>");
	}

	private void printTitle(String titre) {
		pw.write("<tr><td colspan='3' bgcolor='#EDF2EF' align='center' >" + titre + "</td></tr>");
	}

	private void printLine(String titre, String donnee) {
		pw.write("<tr><td>" + titre + "</td><td colspan='2' >" + donnee + "</td></tr>");
	}
	
	

	private void printLine(String titre, int donnee) {
		printLine(titre, "" + donnee);
	}
	
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		pw = response.getWriter();
		
		
		
		if ("/info".equals(request.getServletPath()))
		{
			printEntete();
			beginTableau();

			printTitle("Informations de sessions");
			
			int nbSessionsActives = SessionListener.getNbSessionCourante();

			if (nbSessionsActives == 0)
				printLine(" Nombre de session(s) courante(s)", "Aucune");
			else {
				printLine(" Nombre de session(s) courante(s)", "<a href=\"" + request.getContextPath() + "/detailSessions\" >" + nbSessionsActives + "</a>");
			}
			printLine(" Nombre maximum de session(s) simultanée(s)", SessionListener.getNbMaxSessionSimultanees());

			printTitle("Informations jvm");

			RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

			printLine(" Nom jvm", runtimeMXBean.getVmName());
			printLine(" Fournisseur jvm", runtimeMXBean.getVmVendor());
			printLine(" Version jvm", runtimeMXBean.getVmVersion());

			MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();

			printLine(" Mémoire utilisée jvm", memoryMxBean.getHeapMemoryUsage().getUsed() / coefficient + " (Mo)");
			printLine(" Mémoire libre jvm", (memoryMxBean.getHeapMemoryUsage().getMax() - memoryMxBean.getHeapMemoryUsage().getUsed()) / coefficient + " (Mo)");
			printLine(" Mémoire total jvm", memoryMxBean.getHeapMemoryUsage().getMax() / coefficient + " (Mo)");

			printTitle("Informations système");

			OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

			printLine(" Architecture", operatingSystemMXBean.getArch());
			printLine(" Système d'exploitation", operatingSystemMXBean.getName());
			printLine(" Version du système d'exploitation", operatingSystemMXBean.getVersion());
			printLine(" Nombre de processeurs disponibles (pour la jvm)", operatingSystemMXBean.getAvailableProcessors());

			ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

			printLine(" Nombre de threads alloués", threadMXBean.getThreadCount());
			printLine(" Nombre max de threads alloués", threadMXBean.getPeakThreadCount());
			

			
			

			String showParameter = request.getParameter("show");

			String unitParameter = request.getParameter("unit");

			if (showParameter != null) {

				printTitle("Liste des threads");
				long[] listeThreadIds = threadMXBean.getAllThreadIds();

				if (unitParameter != null && "status".equalsIgnoreCase(unitParameter)) {
					if ("all".equalsIgnoreCase(showParameter)) {
						for (int i = 0; i < listeThreadIds.length; i++) {
							ThreadInfo ti = threadMXBean.getThreadInfo(listeThreadIds[i]);
							printLine(ti.getThreadName(), ti.getThreadState().toString());
						}
					} else {
						for (int i = 0; i < listeThreadIds.length; i++) {
							ThreadInfo ti = threadMXBean.getThreadInfo(listeThreadIds[i]);
							if (ti.getThreadName().toLowerCase().contains(showParameter.toLowerCase())) {
								printLine(ti.getThreadName(), ti.getThreadState().toString());
							}
						}
					}
				} else {
					if ("all".equalsIgnoreCase(showParameter)) {
						for (int i = 0; i < listeThreadIds.length; i++) {
							ThreadInfo ti = threadMXBean.getThreadInfo(listeThreadIds[i]);
							printLine(ti.getThreadName(), threadMXBean.getThreadCpuTime(listeThreadIds[i]) / coefficient2 + "ms");
						}
					} else {
						for (int i = 0; i < listeThreadIds.length; i++) {
							ThreadInfo ti = threadMXBean.getThreadInfo(listeThreadIds[i]);
							if (ti.getThreadName().toLowerCase().contains(showParameter.toLowerCase())) {
								printLine(ti.getThreadName(), threadMXBean.getThreadCpuTime(listeThreadIds[i]) / coefficient2 + "ms");
							}
						}
					}
				}
			}
			
			
			endTableau();
		} else  if ("/detailSessions".equals(request.getServletPath())) {
			// Affichage des informations de sessions détaillées :
			pw.write("<title>Chaka - Informations de Sessions</title><br/>");
			
			beginTableau();
			
			pw.write("<tr><td colspan='5' bgcolor='#EDF2EF' align='center' >Liste des Sessions Actives</td></tr>");
			pw.write("<tr>");
			pw.write("<td align='center' >Identifiant</td>");
			pw.write("<td align='center' >Utilisateur</td>");
			pw.write("<td align='center' >Date de création</td>");
			pw.write("<td align='center' >Date du dernier accès</td>");
			pw.write("<td align='center' >Invalider la session</td>");
			pw.write("</tr>");
			
			GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
			
			for (HttpSession session : SessionListener.getTableauSessions().values())
			{
				String identifiant = session.getId();
				String utilisateur =  "Inconnu";
				
				if (session.getAttribute("utilisateur") != null)
					utilisateur = ((Utilisateur)session.getAttribute("utilisateur")).getNomCompletCourt();
				
				gcal.setTimeInMillis(session.getCreationTime());
				String dateCreation = sdf.format(gcal.getTime());
				
				gcal.setTimeInMillis(session.getLastAccessedTime());
				String dateDernierAccess = sdf.format(gcal.getTime());
				
				
				
				pw.write("<tr>");
				pw.write("<td align='center' >" + identifiant + "</td>");
				pw.write("<td align='center' >" + utilisateur + "</td>");
				pw.write("<td align='center' >" + dateCreation + "</td>");
				pw.write("<td align='center' >" + dateDernierAccess +"</td>");
				pw.write("<td align='center' ><a href=\""+ request.getContextPath() + "/destroySession?id=" + identifiant + "\"" + ">Invalider</a></td>");
				pw.write("</tr>");
				
			}
			
			endTableau();
			
		} else  if ("/destroySession".equals(request.getServletPath())) {
			
			String sessionId = request.getParameter("id");
			
			if (sessionId != null)
			{
				for (HttpSession session : SessionListener.getTableauSessions().values())
				{
					if (sessionId.equals(session.getId()))
					{
						session.invalidate();
						break;
					}
				}
				
			}
			response.sendRedirect(request.getContextPath() + "/detailSessions");
			
		}
		

		

		pw.close();
		pw = null;
		response.flushBuffer();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

}
