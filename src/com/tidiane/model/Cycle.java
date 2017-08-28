package com.tidiane.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cycle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idCycle;

    private String libelle;

    private String libelleCourt;

    private Integer nombreAnnees;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdCycle() {
		return idCycle;
	}

	public void setIdCycle(Long idCycle) {
		this.idCycle = idCycle;
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

	public Integer getNombreAnnees() {
		return nombreAnnees;
	}

	public void setNombreAnnees(Integer nombreAnnees) {
		this.nombreAnnees = nombreAnnees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCycle == null) ? 0 : idCycle.hashCode());
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
		Cycle other = (Cycle) obj;
		if (idCycle == null) {
			if (other.idCycle != null)
				return false;
		} else if (!idCycle.equals(other.idCycle))
			return false;
		return true;
	}

}
