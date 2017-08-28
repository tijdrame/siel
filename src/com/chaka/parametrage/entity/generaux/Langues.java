/**
 * @author Dell
 *
 */
package com.chaka.parametrage.entity.generaux;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;

import com.chaka.constantes.Constantes;
import com.chaka.projet.entity.Utilisateur;

/**
 * 
 * @author Souané
 *
 */
@Entity
@Name("langues")
@Table(name="lst_langues")
public class Langues implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3463892148915777622L;

	/**
	 * identifiant Taxes.
	 */
	private Long idLangue;
	/**
	 * code de la Langue.
	 */
	@Length(max = 2)
	private Integer codeLangue;
	/**
	 * libellé.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE, message="X")
	private String libelle;
	/**
	 * libellé court.
	 */
	@Length(max = Constantes.LONGUEUR_LIBELLE_COURT, message="X")
	private String libelle_court;
	/**
	 * Date de dernière creation Langue.
	 */
	private Date dateCreation;

	/**
	 * Date de dernière modification Langue.
	 */
	private Date dateMaj;
	/**
	 * utilisateur qui a saisit.
	 */
	private Utilisateur utilisateur;
	
	/**
	 * methode construteur de la classe Langues.
	 */
	public Langues()
	{
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idLangue == null) ? 0 : idLangue.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Langues other = (Langues) obj;
		if (idLangue == null) {
			if (other.idLangue != null)
				return false;
		} else if (!idLangue.equals(other.idLangue))
			return false;
		return true;
	}



	/**
	 * @return the idLangue
	 */
	@Id
	@GeneratedValue
	public Long getIdLangue() {
		return idLangue;
	}


	/**
	 * @param idLangue the idLangue to set
	 */
	public void setIdLangue(Long idLangue) {
		this.idLangue = idLangue;
	}


	/**
	 * @return the codeLangue
	 */
	public Integer getCodeLangue() {
		return codeLangue;
	}


	/**
	 * @param codeLangue the codeLangue to set
	 */
	public void setCodeLangue(Integer codeLangue) {
		this.codeLangue = codeLangue;
	}


	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}


	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	/**
	 * @return the libelle_court
	 */
	public String getLibelle_court() {
		return libelle_court;
	}


	/**
	 * @param libelle_court the libelle_court to set
	 */
	public void setLibelle_court(String libelle_court) {
		this.libelle_court = libelle_court;
	}


	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}


	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	/**
	 * @return the dateMaj
	 */
	public Date getDateMaj() {
		return dateMaj;
	}


	/**
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}
	
	/**
	 * méthode pour créér le doce de la langue. 
	 */
	


	/**
	 * @return the utilisateur
	 */
	@ManyToOne
	@JoinColumn(name = "idUtilisateur", nullable = false)
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}



	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	
	
}
