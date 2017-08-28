package com.chaka.parametrage.converter.generaux;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.chaka.parametrage.entity.generaux.Langues;


/**
 * Converter generique pour les liste d�roulantes.
 * @author Senef
 * V 0.1 : Maj : 10/03/2010
 */

@Name("langueConverter")
public class LangueConverter implements Converter {
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public LangueConverter() {
		super();
	}
	
	/**
	 * Transforme la string pass�e en parametre en objet.
	 * 
	 * @param myContextFaces
	 *            le contexte courant.
	 * @param pComponent
	 *            le composant li� � la transformation.
	 * @param pValeur
	 *            la valeur de l'objet � transformer.
	 * @return l'objet transform�.
	 * @throws ConverterException
	 * 
	 */
	public Object getAsObject(FacesContext myContextFaces, UIComponent pComponent, String pValeur) {
		Langues langue = (Langues)this.dataSource.get(Langues.class, 
				new Long(pValeur));
		return langue;
	}
	/**
	 * Transforme l'objet pass� en parametre en string.
	 * 
	 * @param myContextFaces
	 *            le contexte courant.
	 * @param uiComponent
	 *            le composant li� � la transformation.
	 * @param objet
	 *            la valeure de l'objet � transformer.
	 * @return l'objet transform�.
	 * 
	 */
	public String getAsString(FacesContext myContextFaces, UIComponent uiComponent, Object objet) {
		Langues langue = (Langues)objet;
		String resultat = "";
		if (langue != null) {
			resultat = langue.getIdLangue().toString();
		}
		return resultat;
	}
}
