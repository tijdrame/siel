package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.DossierMedical;


@Name("dossierMedicalConverter")
public class DossierMedicalConverter implements Converter  {
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public DossierMedicalConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		DossierMedical dossierMedical = (DossierMedical)this.dataSource.get(DossierMedical.class, 
				new Long(pValeur));
		return dossierMedical;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		DossierMedical dossierMedical = (DossierMedical)objet;
		String resultat = "Tous";
		if (dossierMedical != null) {
			resultat = dossierMedical.getIdDossier().toString();
		}
		return resultat;
	}

}
