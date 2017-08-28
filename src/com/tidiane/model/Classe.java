package com.tidiane.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Classe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idClasse;

    private String libelle;

    private String libelleCourt;

    private String serieCl;

    private Cycle cycle;

    private Double fraisInscription;

    private List<LnkCoefMatiere> listLnkCoefMatieres;

    
    private List<Inscription> listInscriptions;

    private Institut institut;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(Long idClasse) {
		this.idClasse = idClasse;
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

	public String getSerieCl() {
		return serieCl;
	}

	public void setSerieCl(String serieCl) {
		this.serieCl = serieCl;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCycle", nullable=false)
	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	public Double getFraisInscription() {
		return fraisInscription;
	}

	public void setFraisInscription(Double fraisInscription) {
		this.fraisInscription = fraisInscription;
	}

	@OneToMany
    @JoinColumn(name = "idClasse", nullable=true)
    @Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @LazyCollection(LazyCollectionOption.TRUE)
	public List<LnkCoefMatiere> getListLnkCoefMatieres() {
		return listLnkCoefMatieres;
	}

	public void setListLnkCoefMatieres(List<LnkCoefMatiere> listLnkCoefMatieres) {
		this.listLnkCoefMatieres = listLnkCoefMatieres;
	}

	@OneToMany
    @JoinColumn(name = "idClasse", nullable=true)
    @Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @LazyCollection(LazyCollectionOption.TRUE)
	public List<Inscription> getListInscriptions() {
		return listInscriptions;
	}

	public void setListInscriptions(List<Inscription> listInscriptions) {
		this.listInscriptions = listInscriptions;
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
		result = prime * result + ((idClasse == null) ? 0 : idClasse.hashCode());
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
		Classe other = (Classe) obj;
		if (idClasse == null) {
			if (other.idClasse != null)
				return false;
		} else if (!idClasse.equals(other.idClasse))
			return false;
		return true;
	}
    
    

}
