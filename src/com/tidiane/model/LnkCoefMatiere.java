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
public class LnkCoefMatiere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Long idLnkCoef;

	 private Integer valCoef;

	 
	 private Classe classe;

	 /*@ManyToOne(fetch = FetchType.LAZY)
    	@JoinColumn(name = "idAnneeAc", nullable=false)
    	private AnneeAcademique anneeAcademique;*/  //pour historiser dans le cas ou les coefs peuvent varier
	 
	private Matiere matiere;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdLnkCoef() {
		return idLnkCoef;
	}

	public void setIdLnkCoef(Long idLnkCoef) {
		this.idLnkCoef = idLnkCoef;
	}

	public Integer getValCoef() {
		return valCoef;
	}

	public void setValCoef(Integer valCoef) {
		this.valCoef = valCoef;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idClasse", nullable=false)
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMatiere", nullable=false)
	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLnkCoef == null) ? 0 : idLnkCoef.hashCode());
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
		LnkCoefMatiere other = (LnkCoefMatiere) obj;
		if (idLnkCoef == null) {
			if (other.idLnkCoef != null)
				return false;
		} else if (!idLnkCoef.equals(other.idLnkCoef))
			return false;
		return true;
	}
	 

}
