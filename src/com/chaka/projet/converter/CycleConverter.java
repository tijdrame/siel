package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.Cycle;

@Name("cycleConverter")
public class CycleConverter implements Converter{
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public CycleConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		Cycle cycle = (Cycle)this.dataSource.get(Cycle.class, 
				new Long(pValeur));
		return cycle;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Cycle cycle = (Cycle)objet;
		String resultat = "";
		if (cycle != null && cycle.getIdCycle()!=null) {
			resultat = cycle.getIdCycle().toString();
		}
		return resultat;
	}

}
