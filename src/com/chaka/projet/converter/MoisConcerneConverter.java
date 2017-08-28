package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.MoisConcerne;


@Name("moisConcerneConverter")
public class MoisConcerneConverter implements Converter{
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public MoisConcerneConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		MoisConcerne moisConcerne = (MoisConcerne)this.dataSource.get(MoisConcerne.class, 
				new Long(pValeur));
		return moisConcerne;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		MoisConcerne moisConcerne = (MoisConcerne)objet;
		String resultat = "";
		if (moisConcerne != null) {
			resultat = moisConcerne.getIdMois().toString();
		}
		return resultat;
	}

}
