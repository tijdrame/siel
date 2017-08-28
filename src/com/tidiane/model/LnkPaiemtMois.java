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
public class LnkPaiemtMois implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idLnk;
	
	private MoisConcerne moisConcerne;
	
	private Paiement paiement;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdLnk() {
		return idLnk;
	}

	public void setIdLnk(Long idLnk) {
		this.idLnk = idLnk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMois")
	public MoisConcerne getMoisConcerne() {
		return moisConcerne;
	}

	public void setMoisConcerne(MoisConcerne moisConcerne) {
		this.moisConcerne = moisConcerne;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaiement")
	public Paiement getPaiement() {
		return paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLnk == null) ? 0 : idLnk.hashCode());
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
		LnkPaiemtMois other = (LnkPaiemtMois) obj;
		if (idLnk == null) {
			if (other.idLnk != null)
				return false;
		} else if (!idLnk.equals(other.idLnk))
			return false;
		return true;
	}
	
	
}
