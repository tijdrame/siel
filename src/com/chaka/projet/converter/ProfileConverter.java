package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.chaka.projet.entity.Profile;

/**
 * Converter JSF. Permet d'afficher un profile En fonction de sa valeur 0,1,2,3,4...
 * @author Chaka
 * V 0.1 : Maj : 09/11/2007
 */

@Name("profileConverter")
public class ProfileConverter implements Converter {
	
	/**
	 * Constructeur
	 */
	public ProfileConverter() {
		super();
	}
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Transforme la string passée en parametre en objet.
	 * @param myContextFaces le contexte courant.
	 * @param pComponent le composant lié à la transformation.
	 * @param pValeur la valeure de l'objet à transformer.
	 * @return l'objet transformé.
	 * @throws ConverterException
	 * 
	 */
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		Profile profile = (Profile)this.dataSource.get(Profile.class, 
				new Long(pValeur));
		return profile;
	}
	
	/**
	 * Transforme l'objet passé en parametre en string.
	 * @param myContextFaces le contexte courant.
	 * @param uiComponent le composant lié à la transformation.
	 * @param objet la valeure de l'objet à transformer.
	 * @return l'objet transformé.
	 * @throws ConverterException
	 * 
	 */
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Profile profile = (Profile)objet;
		String resultat = "";
		if (profile != null) {
			resultat = profile.getIdProfile().toString();
		}
		return resultat;
	}
}
