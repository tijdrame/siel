package com.chaka.projet.service.utils;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.LocaleSelector;

@Name("serviceLangue")
@Scope(ScopeType.SESSION)
public class ServiceLangue implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1245752278223582069L;

	
	@In
	LocaleSelector localeSelector;
	
	
	public ServiceLangue() {
		super();
		// TODO Auto-generated constructor stub
		localeSelector.setLanguage("fr");
		//return "/pages/accueil.xhtml";
	}

	public String passerEnFrancais()
	{
		localeSelector.setLanguage("fr");
		return "/pages/accueil.xhtml";
		
	}
	
	public String passerEnAnglais()
	{
		localeSelector.setLanguage("en");
		return "/pages/accueil.xhtml";
		
	}
	
	
}
