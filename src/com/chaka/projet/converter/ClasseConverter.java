package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.Classe;


@Name("classeConverter")
public class ClasseConverter implements Converter{
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public ClasseConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		Classe classe = (Classe)this.dataSource.get(Classe.class, 
				new Long(pValeur));
		return classe;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Classe classe = (Classe)objet;
		String resultat = "";
		if (classe != null) {
			resultat = classe.getIdClasse().toString();
		}
		return resultat;
	}

}
