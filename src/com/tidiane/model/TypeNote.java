package com.tidiane.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TypeNote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idTypeNote;

    private String libelle;

    private String libelleCourt;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdTypeNote() {
		return idTypeNote;
	}

	public void setIdTypeNote(Long idTypeNote) {
		this.idTypeNote = idTypeNote;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTypeNote == null) ? 0 : idTypeNote.hashCode());
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
		TypeNote other = (TypeNote) obj;
		if (idTypeNote == null) {
			if (other.idTypeNote != null)
				return false;
		} else if (!idTypeNote.equals(other.idTypeNote))
			return false;
		return true;
	}
	

}
