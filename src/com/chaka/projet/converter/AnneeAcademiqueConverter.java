package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.AnneeAcademique;


@Name("anneeAcademiqueConverter")
public class AnneeAcademiqueConverter implements Converter{
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public AnneeAcademiqueConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		AnneeAcademique academique = (AnneeAcademique)this.dataSource.get(AnneeAcademique.class, 
				new Long(pValeur));
		return academique;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		AnneeAcademique academique = (AnneeAcademique)objet;
		String resultat = "";
		if (academique != null) {
			resultat = academique.getIdAnneeAc().toString();
		}
		return resultat;
	}

}
