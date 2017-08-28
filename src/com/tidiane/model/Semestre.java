package com.tidiane.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Semestre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idSemestre;

    private String libelle;

    private String libelleCourt;
    
    private Institut institut;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdSemestre() {
		return idSemestre;
	}

	public void setIdSemestre(Long idSemestre) {
		this.idSemestre = idSemestre;
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

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstitut", nullable=true)
	public Institut getInstitut() {
		return institut;
	}

	public void setInstitut(Institut institut) {
		this.institut = institut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSemestre == null) ? 0 : idSemestre.hashCode());
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
		Semestre other = (Semestre) obj;
		if (idSemestre == null) {
			if (other.idSemestre != null)
				return false;
		} else if (!idSemestre.equals(other.idSemestre))
			return false;
		return true;
	}
    

}
