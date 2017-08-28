package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.Semestre;


@Name("semestreConverter")
public class SemestreConverter implements Converter{
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public SemestreConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		Semestre semestre = (Semestre)this.dataSource.get(Semestre.class, 
				new Long(pValeur));
		return semestre;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Semestre semestre = (Semestre)objet;
		String resultat = "";
		if (semestre != null) {
			resultat = semestre.getIdSemestre().toString();
		}
		return resultat;
	}

}
