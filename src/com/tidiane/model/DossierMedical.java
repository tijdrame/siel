package com.tidiane.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DossierMedical implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idDossier;
	private Etudiant etudiant;
	private Maladie maladie;
	private Date dateDebMaladie, dateGuerison;
	private Boolean hospitaliser, operer; 
	private String commentaires;
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdDossier() {
		return idDossier;
	}
	public void setIdDossier(Long idDossier) {
		this.idDossier = idDossier;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEtudiant", nullable=false)
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMaladie", nullable=false)
	public Maladie getMaladie() {
		return maladie;
	}
	public void setMaladie(Maladie maladie) {
		this.maladie = maladie;
	}
	public Date getDateDebMaladie() {
		return dateDebMaladie;
	}
	public void setDateDebMaladie(Date dateDebMaladie) {
		this.dateDebMaladie = dateDebMaladie;
	}
	public Date getDateGuerison() {
		return dateGuerison;
	}
	public void setDateGuerison(Date dateGuerison) {
		this.dateGuerison = dateGuerison;
	}
	public Boolean getHospitaliser() {
		return hospitaliser;
	}
	public void setHospitaliser(Boolean hospitaliser) {
		this.hospitaliser = hospitaliser;
	}
	public Boolean getOperer() {
		return operer;
	}
	public void setOperer(Boolean operer) {
		this.operer = operer;
	}
	public String getCommentaires() {
		return commentaires;
	}
	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDossier == null) ? 0 : idDossier.hashCode());
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
		DossierMedical other = (DossierMedical) obj;
		if (idDossier == null) {
			if (other.idDossier != null)
				return false;
		} else if (!idDossier.equals(other.idDossier))
			return false;
		return true;
	}
	

}
