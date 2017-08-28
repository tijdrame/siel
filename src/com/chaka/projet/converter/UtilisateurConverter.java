package com.chaka.projet.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.chaka.projet.entity.Utilisateur;



/**
 * Converter generique pour les liste d�roulantes.
 * @author Chaka
 * V 0.1 : Maj : 09/11/2007
 */

@Name("utilisateurConverter")
public class UtilisateurConverter implements Converter {
	
	/**
	 * Session Hibernate
	 */
	@In
	private Session dataSource;
	
	/**
	 * Constructeur
	 */
	public UtilisateurConverter() {
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
		try{
		Utilisateur user = (Utilisateur)this.dataSource.get(Utilisateur.class, 
				new Long(pValeur));
		return user;
		} catch(NumberFormatException nfe){
			return null;
		}
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
		Utilisateur user = (Utilisateur)objet;
		String resultat = "Tous";
		if (user != null) {
			resultat = user.getIdUtilisateur().toString();
		}
		return resultat;
	}
}
