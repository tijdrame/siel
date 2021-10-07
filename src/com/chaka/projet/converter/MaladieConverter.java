package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.Maladie;


@Name("maladieConverter")
public class MaladieConverter implements Converter  {
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public MaladieConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		Maladie maladie = (Maladie)this.dataSource.get(Maladie.class, 
				new Long(pValeur));
		return maladie;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Maladie maladie = (Maladie)objet;
		String resultat = "Tous";
		if (maladie != null) {
			resultat = maladie.getIdMaladie().toString();
		}
		return resultat;
	}

}
