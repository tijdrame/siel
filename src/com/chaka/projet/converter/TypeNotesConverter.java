package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.tidiane.model.TypeNote;



@Name("typeNotesConverter")
public class TypeNotesConverter implements Converter{
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public TypeNotesConverter() {
		super();
	}
	
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		try{
		TypeNote typeNote = (TypeNote)this.dataSource.get(TypeNote.class, 
				new Long(pValeur));
		return typeNote;
		} catch(NumberFormatException nfe){
			return null;
		}
	}
	
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		TypeNote typeNote = (TypeNote)objet;
		String resultat = "";
		if (typeNote != null) {
			resultat = typeNote.getIdTypeNote().toString();
		}
		return resultat;
	}

}
