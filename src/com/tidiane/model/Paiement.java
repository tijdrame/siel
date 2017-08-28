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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.chaka.projet.entity.Utilisateur;

@Entity
public class Paiement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPaiement;

    private Date datePaiement;

    private Double montant;

    private String commentaires;

    private Inscription inscription;

    private List<LnkPaiemtMois>  listMoisConcerne;

    private Utilisateur userSaisie;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdPaiement() {
		return idPaiement;
	}

	public void setIdPaiement(Long idPaiement) {
		this.idPaiement = idPaiement;
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInscription", nullable=false)
	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}

	/*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lnkPaiemtMois", joinColumns = {
            @JoinColumn(name = "idPaiement", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "idMois",
                    nullable = false, updatable = false) })*/
	@OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "idPaiement", nullable=true)
    @Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @LazyCollection(LazyCollectionOption.TRUE)
	public List<LnkPaiemtMois> getListMoisConcerne() {
		return listMoisConcerne;
	}

	public void setListMoisConcerne(List<LnkPaiemtMois> listMoisConcerne) {
		this.listMoisConcerne = listMoisConcerne;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUserSaisie", nullable=false)
	public Utilisateur getUserSaisie() {
		return userSaisie;
	}

	public void setUserSaisie(Utilisateur userSaisie) {
		this.userSaisie = userSaisie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPaiement == null) ? 0 : idPaiement.hashCode());
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
		Paiement other = (Paiement) obj;
		if (idPaiement == null) {
			if (other.idPaiement != null)
				return false;
		} else if (!idPaiement.equals(other.idPaiement))
			return false;
		return true;
	}

}
