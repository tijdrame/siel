package com.chaka.projet.service;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.chaka.projet.entity.Profile;

/**
 * Classe de données representant un type de permis dans l'application.
 * Cette classe est persistante via Hibernate. Elle est liée à la table
 * CIVILITE.
 * @author Chaka
 * V 0.1 : Maj : 10/03/2010
 */
@Scope(ScopeType.APPLICATION)
@Name("profilesList")
public class ProfileList {

	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;

	/**
	 * Logger
	 */
	@Logger
	private Log log;

	/**
	 * Liste des types de permis
	 */
	private List<Profile> lstProfiles;

	private List<Profile> lstProfilesAgence;


	/**
	 * Constructeur
	 */
	public ProfileList() {
		super();
	}



	


	public List<Profile> getLstProfiles() {
		return lstProfiles;
	}

	public void setLstProfiles(List<Profile> lstProfiles) {
		this.lstProfiles = lstProfiles;
	}

	public List<Profile> getLstProfilesAgence() {
		return lstProfilesAgence;
	}

	public void setLstProfilesAgence(List<Profile> lstProfilesAgence) {
		this.lstProfilesAgence = lstProfilesAgence;
	}



	@SuppressWarnings("unchecked")
	@Create
	public void load() {
		try {
			lstProfiles = dataSource.createQuery("from Profile where idProfile != 10 ").list();
			lstProfilesAgence = dataSource.createQuery("from Profile where idProfile != 3 and idProfile != 4 and idProfile != 10  and idProfile != 11  and idProfile != 12 ").list();
		} catch (HibernateException he) {
			log.error("load", he);
		}

	}





}