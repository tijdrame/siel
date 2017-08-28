package com.tidiane.model;

import java.io.Serializable;
import java.util.Date;
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

import com.chaka.projet.entity.Utilisateur;

@Entity
public class Bulletin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idBulletin;

    private Inscription inscription;

    private Semestre semestre;

    
    private List<LnkBulletinMatiere> listLnkBulletinMatieres;

    private Double moyGen;
    private Double moySemestre;

    private Boolean deleted = false;
    
    private Boolean editer = false;

    private Integer rangB =0;
    private Integer nbRetard =0;
    private Integer nbAbscence =0;
    private String appreciation;
    private Double moyPremierSemestre;

    private Utilisateur userSaisie;

    private Date dateEdition;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdBulletin() {
		return idBulletin;
	}

	public void setIdBulletin(Long idBulletin) {
		this.idBulletin = idBulletin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInscription", nullable=false)
	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSemestre", nullable=false)
	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	@OneToMany
    @JoinColumn(name = "idBulletin", nullable=true)
    @Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @LazyCollection(LazyCollectionOption.TRUE)
	public List<LnkBulletinMatiere> getListLnkBulletinMatieres() {
		return listLnkBulletinMatieres;
	}

	public void setListLnkBulletinMatieres(List<LnkBulletinMatiere> listLnkBulletinMatieres) {
		this.listLnkBulletinMatieres = listLnkBulletinMatieres;
	}

	public Double getMoyGen() {
		return moyGen;
	}

	public void setMoyGen(Double moyGen) {
		this.moyGen = moyGen;
	}

	public Double getMoySemestre() {
		return moySemestre;
	}

	public void setMoySemestre(Double moySemestre) {
		this.moySemestre = moySemestre;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getRangB() {
		return rangB;
	}

	public void setRangB(Integer rangB) {
		this.rangB = rangB;
	}

	public Integer getNbRetard() {
		return nbRetard;
	}

	public void setNbRetard(Integer nbRetard) {
		this.nbRetard = nbRetard;
	}

	public Integer getNbAbscence() {
		return nbAbscence;
	}

	public void setNbAbscence(Integer nbAbscence) {
		this.nbAbscence = nbAbscence;
	}

	public String getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}

	public Double getMoyPremierSemestre() {
		return moyPremierSemestre;
	}

	public void setMoyPremierSemestre(Double moyPremierSemestre) {
		this.moyPremierSemestre = moyPremierSemestre;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUserSaisie", nullable=false)
	public Utilisateur getUserSaisie() {
		return userSaisie;
	}

	public void setUserSaisie(Utilisateur userSaisie) {
		this.userSaisie = userSaisie;
	}

	public Date getDateEdition() {
		return dateEdition;
	}

	public void setDateEdition(Date dateEdition) {
		this.dateEdition = dateEdition;
	}

	public Boolean getEditer() {
		return editer;
	}

	public void setEditer(Boolean editer) {
		this.editer = editer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBulletin == null) ? 0 : idBulletin.hashCode());
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
		Bulletin other = (Bulletin) obj;
		if (idBulletin == null) {
			if (other.idBulletin != null)
				return false;
		} else if (!idBulletin.equals(other.idBulletin))
			return false;
		return true;
	}

	
}
