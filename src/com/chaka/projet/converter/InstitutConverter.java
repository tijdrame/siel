package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.Institut;

@Name("institutConverter")
public class InstitutConverter implements Converter  {
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public InstitutConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		Institut institut = (Institut)this.dataSource.get(Institut.class, 
				new Long(pValeur));
		return institut;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Institut institut = (Institut)objet;
		String resultat = "Tous";
		if (institut != null) {
			resultat = institut.getIdInstitut().toString();
		}
		return resultat;
	}

}
