package com.chaka.projet.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Classe de données representant un utilisateur dans l'application. Cette
 * classe est persistante via Hibernate. Elle est liée à la table UTILISATEUR.
 *
 * @author Chaka V 0.1 : Maj : 09/11/2007
 */
@Entity
@Table(name = "utilisateur")
public class UtilisateurSecurise implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3134839623246609834L;

	private Long idUtilisateur;
	
	private String login;
	
	private byte[] securePassword;
	
	private byte[] salt;


	/**
	 * Constructeur
	 *
	 */
	public UtilisateurSecurise() {
		super();
	}

	@Id
	@GeneratedValue
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	/**
	 * Numero d'identification gendarmerie.
	 *
	 * @param idUtilisateur
	 *            parametre
	 */
	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	/**
	 * Surcharge de la méthode equals de Object. Définie deux objet qui ont le
	 * même idUtilisateur comme étant égaux.
	 *
	 *
	 * L'objet à comparer.
	 *
	 * @return resultat de la comparaison.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (idUtilisateur != null) {
			result = idUtilisateur.hashCode();
		}
		return result;
	}

	/**
	 * Surcharge de la méthode hashCode de Object.
	 *
	 * @param obj
	 *            parametre
	 * @return le hashCode généré.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UtilisateurSecurise other = (UtilisateurSecurise) obj;
		if (idUtilisateur == null) {
			if (other.idUtilisateur != null) {
				return false;
			}
		} else if (!idUtilisateur.equals(other.idUtilisateur)) {
			return false;
		}
		return true;
	}

	@Lob
	public byte[] getSecurePassword() {
		return securePassword;
	}

	public void setSecurePassword(byte[] securePassword) {
		this.securePassword = securePassword;
	}

	@Lob
	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Column(unique=true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	

	
	


	
}
