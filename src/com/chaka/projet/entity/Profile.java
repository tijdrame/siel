package com.chaka.projet.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "lstprofile")
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Profile implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5768081131650846552L;

	/**
	 * Cl� primaire
	 */
	private Long idProfile;

	/**
	 * Libelle
	 */
	private String libelle;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Constructeur
	 */
	public Profile() {
		super();
	}

	/**
	 * Accesseur de la variable idCivilite de type Long
	 *
	 * @return le idCivilite
	 */
	@Id
	@GeneratedValue
	public Long getIdProfile() {
		return this.idProfile;
	}

	/**
	 * Modificateur de la variable idProfile de type Long
	 *
	 * @param idCategorieAgent
	 *            idProfile � fixer.
	 */
	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	

	/**
	 * Surcharge de la m�thode hashCode de Object.
	 *
	 * @return le hashCode g�n�r�.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (!(this.idProfile == null)) {
			result = result + this.idProfile.hashCode();
		}
		return result;
	}

	/**
	 * Surcharge de la m�thode equals de Object. D�finie deux objet qui ont le
	 * m�me idSexe comme �tant �gaux.
	 *
	 * @param obj
	 *            L'objet � comparer.
	 * @return resultat de la comparaison.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Profile other = (Profile) obj;
		if (this.idProfile == null) {
			if (other.idProfile != null) {
				return false;
			}
		} else if (!this.idProfile.equals(other.idProfile)) {
			return false;
		}
		return true;
	}

}
