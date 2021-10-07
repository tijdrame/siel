package com.tidiane.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Maladie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idMaladie;
	
	private String libelle, libelleCourt, commentaires;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdMaladie() {
		return idMaladie;
	}

	public void setIdMaladie(Long idMaladie) {
		this.idMaladie = idMaladie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelleCourt() {
		return libelleCourt;
	}

	public void setLibelleCourt(String libelleCourt) {
		this.libelleCourt = libelleCourt;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMaladie == null) ? 0 : idMaladie.hashCode());
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
		Maladie other = (Maladie) obj;
		if (idMaladie == null) {
			if (other.idMaladie != null)
				return false;
		} else if (!idMaladie.equals(other.idMaladie))
			return false;
		return true;
	}
	

}
