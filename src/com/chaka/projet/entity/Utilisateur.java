package com.chaka.projet.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Email;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;

import com.tidiane.model.Institut;


/**
 * Classe de donn�es representant un utilisateur dans l'application. Cette
 * classe est persistante via Hibernate. Elle est li�e � la table UTILISATEUR.
 *
 * @author Chaka V 0.1 : Maj : 09/11/2007
 */
@Entity
@Name("utilisateur")
@Table(name = "utilisateur")
@Scope(ScopeType.SESSION)
@Role(name = "user", scope = ScopeType.CONVERSATION)
public class Utilisateur implements Serializable {
	
	private static final long serialVersionUID = 3006466330479401097L;
	
	

	public static final int SADMIN = 1;
	public static final int ADMIN = 2;
	
	public static final int SURVEILLANT = 3;
	public static final int SECRETAIRE = 4;
	public static final int PROFESSEUR = 5;
	public static final int CAISSIER = 6;
	public static final int DIR_GEN = 7;
	public static final int PARENT = 8;


	
	/**
	 * Constante representant le role administrateur
	 */
	public static final String R_SADMIN = "sAdmin";
	public static final String R_ADMIN = "admin";
	public static final String R_SURVEILLANT = "surveillant";
	public static final String R_SECRETAIRE = "secretaire";
	public static final String R_PROFESSEUR = "professeur";
	public static final String R_CAISSIER = "caissier";
	public static final String R_DIR_GEN = "dirGen";
	public static final String R_PARENT = "parent";
	
	
	
	
	private Long idUtilisateur;

	private Date dateCreation;

	private Date dateMaj;
	
	private String login;
	
	private String password;
	
	private String password2;
	
	private String nom;
	
	private String prenom;
	
	private String email;
	
	private String tel, adresse;
	
	private Profile profile;
	
	private boolean actif;
	
	private Institut institut;
	

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	
	@ManyToOne
	@JoinColumn(name = "idProfile")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}


	

	@Column(unique=true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Transient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
	@Transient
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Email(message="X")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	public String getNomCompletCourt() {
		return prenom +  " " + nom;
	}

	public void setNomCompletCourt(String nomCompletCourt) {
	}
	

	@Transient
	public String getNomComplet() {
		return prenom +  " " + nom;
	}

	public void setNomComplet(String nomComplet) {
	}


	
	
	

	


	/**
	 * Constructeur
	 *
	 */
	public Utilisateur() {
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



	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstitut", nullable=true)
	public Institut getInstitut() {
		return institut;
	}

	public void setInstitut(Institut institut) {
		this.institut = institut;
	}

	/**
	 * Surcharge de la m�thode equals de Object. D�finie deux objet qui ont le
	 * m�me idUtilisateur comme �tant �gaux.
	 *
	 *
	 * L'objet � comparer.
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
	 * Surcharge de la m�thode hashCode de Object.
	 *
	 * @param obj
	 *            parametre
	 * @return le hashCode g�n�r�.
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
		final Utilisateur other = (Utilisateur) obj;
		if (idUtilisateur == null) {
			if (other.idUtilisateur != null) {
				return false;
			}
		} else if (!idUtilisateur.equals(other.idUtilisateur)) {
			return false;
		}
		return true;
	}

	

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	

	/**
	 * Conversion en string de l'utilisateur
	 * @return string l'utilisateur
	 */
	public String toString() {
		String str =  this.nom + " " + this.prenom;
		return str;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
}
