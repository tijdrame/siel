package com.chaka.projet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.chaka.projet.entity.Utilisateur;


/**
 * Simple Servlet Filter that sets the Log4J MDC to the currently logged-in
 * user. This filter is intended to be used with JBoss&acute;s http-invoker web
 * application to identify/separate parallel fat client RMI requests.
 * 
 * $id: $
 * 
 * @author Matthias G&auml;rtner
 * 
 */
public class MDCUserServletFilter implements Filter {
	
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;

		
		boolean bUserAdded = false;
		if (httprequest.getSession(false) != null) {
			String sessionId = httprequest.getSession(false).getId();
			if (sessionId != null && sessionId.length() > 0) {
				// Put the principal's name into the message diagnostic
				// context. May be shown using %X{USER_LOGIN} in the layout
				// pattern.

				// On récupère le login du user en session :

				MDC.put("USER_IP", httprequest.getRemoteHost());

				HttpSession sessionHttp = httprequest.getSession(false);

				if (sessionHttp != null) {
					Utilisateur userEnSession = (Utilisateur) sessionHttp.getAttribute("utilisateur");
					if (userEnSession != null && userEnSession.getLogin() != null) {
						MDC.put("USER_LOGIN", userEnSession.getLogin());
						bUserAdded = true;
					}

				}
			}
		}

		try {
			
			
			
			//long a = System.currentTimeMillis();
			
			// Continue processing the rest of the filter chain.
			chain.doFilter(request, response);
			
			//long b = System.currentTimeMillis();
			
			//System.err.println(httprequest.getRequestURI() + " " + (b-a) + "ms");
			
			
		} finally {
			if (bUserAdded) {
				try {
					// Remove the added element again - only if added.
					MDC.remove("USER_IP");
					MDC.remove("USER_LOGIN");
				} catch (Exception e) {

				}
			}
		}
	}

	public void destroy() {
	}
}