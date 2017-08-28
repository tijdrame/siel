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

import com.chaka.projet.entity.Utilisateur;

@Entity
public class Presence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPresence;

    private Date dateAbsRetard;

    private String typeAbsence="Retard";

    private Matiere matiere;

    private Inscription inscription;

    private Semestre semestre;
    
    private Utilisateur userSaisie;

    private Utilisateur userModif;
    
    private Date dateModif;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdPresence() {
		return idPresence;
	}

	public void setIdPresence(Long idPresence) {
		this.idPresence = idPresence;
	}

	public Date getDateAbsRetard() {
		return dateAbsRetard;
	}

	public void setDateAbsRetard(Date dateAbsRetard) {
		this.dateAbsRetard = dateAbsRetard;
	}

	public String getTypeAbsence() {
		return typeAbsence;
	}

	public void setTypeAbsence(String typeAbsence) {
		this.typeAbsence = typeAbsence;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMatiere", nullable=false)
	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
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

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUserSaisie", nullable=true)
	public Utilisateur getUserSaisie() {
		return userSaisie;
	}

	public void setUserSaisie(Utilisateur userSaisie) {
		this.userSaisie = userSaisie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUserModif", nullable=true)
	public Utilisateur getUserModif() {
		return userModif;
	}

	public void setUserModif(Utilisateur userModif) {
		this.userModif = userModif;
	}

	public Date getDateModif() {
		return dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPresence == null) ? 0 : idPresence.hashCode());
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
		Presence other = (Presence) obj;
		if (idPresence == null) {
			if (other.idPresence != null)
				return false;
		} else if (!idPresence.equals(other.idPresence))
			return false;
		return true;
	}
	
    
    
}
