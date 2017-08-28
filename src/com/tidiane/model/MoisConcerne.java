package com.tidiane.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MoisConcerne implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idMois;

    private String moisConcerne;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdMois() {
		return idMois;
	}

	public void setIdMois(Long idMois) {
		this.idMois = idMois;
	}

	public String getMoisConcerne() {
		return moisConcerne;
	}

	public void setMoisConcerne(String moisConcerne) {
		this.moisConcerne = moisConcerne;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMois == null) ? 0 : idMois.hashCode());
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
		MoisConcerne other = (MoisConcerne) obj;
		if (idMois == null) {
			if (other.idMois != null)
				return false;
		} else if (!idMois.equals(other.idMois))
			return false;
		return true;
	}
    
}
