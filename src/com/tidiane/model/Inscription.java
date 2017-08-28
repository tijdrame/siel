package com.tidiane.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
public class Inscription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idInscription;

    private Date dateInscription;

    private Classe classe;

    private Etudiant etudiant;

    private AnneeAcademique anneeAcademique;

    private Double montant;

    private String numInscription;

    private List<Notes> listNotes;

    private Boolean redouble;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdInscription() {
		return idInscription;
	}

	public void setIdInscription(Long idInscription) {
		this.idInscription = idInscription;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
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
    @JoinColumn(name = "idEtudiant", nullable=false)
	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAnneeAc", nullable=false)
	public AnneeAcademique getAnneeAcademique() {
		return anneeAcademique;
	}

	public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
		this.anneeAcademique = anneeAcademique;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	@Column(unique=true)
	public String getNumInscription() {
		return numInscription;
	}

	public void setNumInscription(String numInscription) {
		this.numInscription = numInscription;
	}

	@OneToMany
    @JoinColumn(name = "idInscription", nullable=true)
    @Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @LazyCollection(LazyCollectionOption.TRUE)
	public List<Notes> getListNotes() {
		return listNotes;
	}

	public void setListNotes(List<Notes> listNotes) {
		this.listNotes = listNotes;
	}

	public Boolean getRedouble() {
		return redouble;
	}

	public void setRedouble(Boolean redouble) {
		this.redouble = redouble;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInscription == null) ? 0 : idInscription.hashCode());
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
		Inscription other = (Inscription) obj;
		if (idInscription == null) {
			if (other.idInscription != null)
				return false;
		} else if (!idInscription.equals(other.idInscription))
			return false;
		return true;
	}


}
