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
public class Notes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idNotes;

    private Double valNotes;

    private Matiere matiere;

    private Inscription inscription;

    private TypeNote typeNote;

    private Semestre semestre;

    private Utilisateur userSaisie;

    private Utilisateur userModif;

    private Date dateSaisie;
    private Date dateModif;
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdNotes() {
		return idNotes;
	}
	public void setIdNotes(Long idNotes) {
		this.idNotes = idNotes;
	}
	public Double getValNotes() {
		return valNotes;
	}
	public void setValNotes(Double valNotes) {
		this.valNotes = valNotes;
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
    @JoinColumn(name = "idTypeNote", nullable=false)
	public TypeNote getTypeNote() {
		return typeNote;
	}
	public void setTypeNote(TypeNote typeNote) {
		this.typeNote = typeNote;
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
	public Date getDateSaisie() {
		return dateSaisie;
	}
	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
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
		result = prime * result + ((idNotes == null) ? 0 : idNotes.hashCode());
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
		Notes other = (Notes) obj;
		if (idNotes == null) {
			if (other.idNotes != null)
				return false;
		} else if (!idNotes.equals(other.idNotes))
			return false;
		return true;
	}


}
