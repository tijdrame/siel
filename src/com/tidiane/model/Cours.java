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
import javax.persistence.Transient;

import com.chaka.common.utils.ChakaUtils;
import com.chaka.projet.entity.Utilisateur;

@Entity
public class Cours implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idCours;
	
	private Utilisateur professeur, userSaisie, userModif;
	private Classe classe;
	private Matiere matiere;
	private Semestre semestre;
	private AnneeAcademique academique;
	private Date dateCours, dateSaisie, dateModif;
	private Integer heureDebut, minDebut, heureFin, minFin, duree, nbH,nbMn;
	private String commentaires;
	private Boolean payer;
	private Boolean cocher;
	private PaiementGenere PaiementGenere;
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdCours() {
		return idCours;
	}
	public void setIdCours(Long idCours) {
		this.idCours = idCours;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProf", nullable=false)
	public Utilisateur getProfesseur() {
		return professeur;
	}
	public void setProfesseur(Utilisateur professeur) {
		this.professeur = professeur;
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
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSemestre", nullable=false)
	public Semestre getSemestre() {
		return semestre;
	}
	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAnneeAc", nullable=false)
	public AnneeAcademique getAcademique() {
		return academique;
	}
	public void setAcademique(AnneeAcademique academique) {
		this.academique = academique;
	}
	public Date getDateCours() {
		return dateCours;
	}
	public void setDateCours(Date dateCours) {
		this.dateCours = dateCours;
	}
	public Integer getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(Integer heureDebut) {
		this.heureDebut = heureDebut;
	}
	public Integer getMinDebut() {
		return minDebut;
	}
	public void setMinDebut(Integer minDebut) {
		this.minDebut = minDebut;
	}
	public Integer getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(Integer heureFin) {
		this.heureFin = heureFin;
	}
	public Integer getMinFin() {
		return minFin;
	}
	public void setMinFin(Integer minFin) {
		this.minFin = minFin;
	}
	@Transient
	public Integer getDuree() {
		/*Integer dureeEnMn = ChakaUtils.dureeEntre2Heures(heureDebut,minDebut,heureFin,minFin);
		Integer nbHe = dureeEnMn / 60;
		Integer nbMn = dureeEnMn % 60;*/
		return duree=ChakaUtils.dureeEntre2Heures(heureDebut,minDebut,heureFin,minFin);
	}
	public void setDuree(Integer duree) {
		this.duree = duree;
	}
	
	@Transient
	public Integer getNbH() {
		return nbH=duree / 60;
	}
	public void setNbH(Integer nbH) {
		this.nbH = nbH;
	}
	@Transient
	public Integer getNbMn() {
		return nbMn = duree % 60;
	}
	public void setNbMn(Integer nbMn) {
		this.nbMn = nbMn;
	}
	public String getCommentaires() {
		return commentaires;
	}
	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
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
	
	public Boolean getPayer() {
		return payer;
	}
	public void setPayer(Boolean payer) {
		this.payer = payer;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaiement", nullable=true)
	public PaiementGenere getPaiementGenere() {
		return PaiementGenere;
	}
	public void setPaiementGenere(PaiementGenere paiementGenere) {
		PaiementGenere = paiementGenere;
	}
	@Transient
	public Boolean getCocher() {
		return cocher;
	}
	public void setCocher(Boolean cocher) {
		this.cocher = cocher;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCours == null) ? 0 : idCours.hashCode());
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
		Cours other = (Cours) obj;
		if (idCours == null) {
			if (other.idCours != null)
				return false;
		} else if (!idCours.equals(other.idCours))
			return false;
		return true;
	}

}
