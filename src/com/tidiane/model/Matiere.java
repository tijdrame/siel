package com.tidiane.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Matiere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idMatiere;

    private String libelle;

    private String libelleCourt;

    
    private Double moyDevoir;

    private Double moyMatiere;
    
    private Institut institut;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdMatiere() {
		return idMatiere;
	}

	public void setIdMatiere(Long idMatiere) {
		this.idMatiere = idMatiere;
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

	@Transient
	public Double getMoyDevoir() {
		return moyDevoir;
	}

	public void setMoyDevoir(Double moyDevoir) {
		this.moyDevoir = moyDevoir;
	}

	@Transient
	public Double getMoyMatiere() {
		return moyMatiere;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstitut", nullable=true)
	public Institut getInstitut() {
		return institut;
	}

	public void setInstitut(Institut institut) {
		this.institut = institut;
	}

	public void setMoyMatiere(Double moyMatiere) {
		this.moyMatiere = moyMatiere;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMatiere == null) ? 0 : idMatiere.hashCode());
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
		Matiere other = (Matiere) obj;
		if (idMatiere == null) {
			if (other.idMatiere != null)
				return false;
		} else if (!idMatiere.equals(other.idMatiere))
			return false;
		return true;
	}
    
    
}
