package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.Matiere;


@Name("matiereConverter")
public class MatiereConverter implements Converter{
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public MatiereConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		Matiere matiere = (Matiere)this.dataSource.get(Matiere.class, 
				new Long(pValeur));
		return matiere;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Matiere matiere = (Matiere)objet;
		String resultat = "";
		if (matiere != null) {
			resultat = matiere.getIdMatiere().toString();
		}
		return resultat;
	}

}
