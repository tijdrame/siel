package com.chaka.projet.service.utils;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("serviceUtilsSeam")
@Scope(ScopeType.SESSION)
public class ServiceUtilsSeam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1718853248826383105L;
	
	/**
	 * Session Hibernate
	 */
	@SuppressWarnings("unused")
	@In
	private Session dataSource;
	
	
	public Double arrondir(Double nbAArrondir, int nbDecimales) 
	{
		return ServiceUtils.arrondir(nbAArrondir, nbDecimales);
	}
	
	public boolean afficherMenuUlysse()
	{
		if ("true".equals(ServiceUtils.chargerParametre("ulysse.actif")))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean afficherDisponibilite()
	{
		return ServiceUtils.afficherDisponibilite();
	}
	
	
	

	public  String afficherDureeMinutesEnHeures(Long totalMinutes) {
		return ServiceUtils.afficherDureeMinutesEnHeures(totalMinutes);
	}
	
	public Date now()
	{
		return new Date();
	}

	
}
