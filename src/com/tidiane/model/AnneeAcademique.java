package com.tidiane.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class AnneeAcademique implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idAnneeAc;

    private Integer anneeDebut;

    private  Integer anneeFin;

    
    private String anneeAc;
    
    private Institut institut;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdAnneeAc() {
		return idAnneeAc;
	}

	public void setIdAnneeAc(Long idAnneeAc) {
		this.idAnneeAc = idAnneeAc;
	}

	@Column(unique=true)
	public Integer getAnneeDebut() {
		return anneeDebut;
	}

	public void setAnneeDebut(Integer anneeDebut) {
		this.anneeDebut = anneeDebut;
	}

	public Integer getAnneeFin() {
		return anneeFin;
	}

	public void setAnneeFin(Integer anneeFin) {
		this.anneeFin = anneeFin;
	}

	@Transient
	public String getAnneeAc() {
		return anneeAc = anneeDebut +" - "+anneeFin;
	}

	public void setAnneeAc(String anneeAc) {
		this.anneeAc = anneeAc;
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
		result = prime * result + ((idAnneeAc == null) ? 0 : idAnneeAc.hashCode());
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
		AnneeAcademique other = (AnneeAcademique) obj;
		if (idAnneeAc == null) {
			if (other.idAnneeAc != null)
				return false;
		} else if (!idAnneeAc.equals(other.idAnneeAc))
			return false;
		return true;
	}  
    
}
