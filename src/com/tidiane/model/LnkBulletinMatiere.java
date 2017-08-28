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
public class LnkBulletinMatiere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idLnkBulletinMatiere;

    private Bulletin bulletin;

    private Matiere matiere;

    private Double totNote;
    private Double noteXCoef;
    private Integer coef;
    private Double moyNote = 0d;
    private Double moyenneMat = 0d;

    private Double noteExamen ;
    private String appreciation;
    private int rang;
    private Boolean deleted = false;
    private String tableauH;
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdLnkBulletinMatiere() {
		return idLnkBulletinMatiere;
	}
	public void setIdLnkBulletinMatiere(Long idLnkBulletinMatiere) {
		this.idLnkBulletinMatiere = idLnkBulletinMatiere;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBulletin", nullable=false)
	public Bulletin getBulletin() {
		return bulletin;
	}
	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMatiere", nullable=false)
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	public Double getTotNote() {
		return totNote;
	}
	public void setTotNote(Double totNote) {
		this.totNote = totNote;
	}
	public Double getNoteXCoef() {
		return noteXCoef;
	}
	public void setNoteXCoef(Double noteXCoef) {
		this.noteXCoef = noteXCoef;
	}
	public Integer getCoef() {
		return coef;
	}
	public void setCoef(Integer coef) {
		this.coef = coef;
	}
	public Double getMoyNote() {
		return moyNote;
	}
	public void setMoyNote(Double moyNote) {
		this.moyNote = moyNote;
	}
	public Double getMoyenneMat() {
		return moyenneMat;
	}
	public void setMoyenneMat(Double moyenneMat) {
		this.moyenneMat = moyenneMat;
	}
	public Double getNoteExamen() {
		return noteExamen;
	}
	public void setNoteExamen(Double noteExamen) {
		this.noteExamen = noteExamen;
	}
	public String getAppreciation() {
		return appreciation;
	}
	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}
	public int getRang() {
		return rang;
	}
	public void setRang(int rang) {
		this.rang = rang;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getTableauH() {
		return tableauH;
	}
	public void setTableauH(String tableauH) {
		this.tableauH = tableauH;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLnkBulletinMatiere == null) ? 0 : idLnkBulletinMatiere.hashCode());
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
		LnkBulletinMatiere other = (LnkBulletinMatiere) obj;
		if (idLnkBulletinMatiere == null) {
			if (other.idLnkBulletinMatiere != null)
				return false;
		} else if (!idLnkBulletinMatiere.equals(other.idLnkBulletinMatiere))
			return false;
		return true;
	}

    

}
