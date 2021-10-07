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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.chaka.projet.entity.Utilisateur;

@Entity
public class PaiementGenere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPaiement;
	private Boolean payerON;
	private Utilisateur userPayeur;
	private Utilisateur userGenerateur;
	private Date datePayement;
	private Date datePayementEffectif;
	private Integer nbDHeureTotalPaye=0;
	private Double montantTotalPaye = 0d;
	
	private String refPaiement;
	private List<Cours> listCours;
	private Boolean cocher;
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdPaiement() {
		return idPaiement;
	}
	public void setIdPaiement(Long idPaiement) {
		this.idPaiement = idPaiement;
	}
	public Boolean getPayerON() {
		return payerON;
	}
	public void setPayerON(Boolean payerON) {
		this.payerON = payerON;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUserPayeur", nullable=true)
	public Utilisateur getUserPayeur() {
		return userPayeur;
	}
	public void setUserPayeur(Utilisateur userPayeur) {
		this.userPayeur = userPayeur;
	}
	public Date getDatePayement() {
		return datePayement;
	}
	public void setDatePayement(Date datePayement) {
		this.datePayement = datePayement;
	}
	public Double getMontantTotalPaye() {
		return montantTotalPaye;
	}
	public void setMontantTotalPaye(Double montantTotalPaye) {
		this.montantTotalPaye = montantTotalPaye;
	}
	public String getRefPaiement() {
		return refPaiement;
	}
	public void setRefPaiement(String refPaiement) {
		this.refPaiement = refPaiement;
	}
	
	@OneToMany
	@JoinColumn(name = "idPaiement", nullable=true)
	@Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	@LazyCollection(LazyCollectionOption.TRUE) 
	public List<Cours> getListCours() {
		return listCours;
	}
	public void setListCours(List<Cours> listCours) {
		this.listCours = listCours;
	}
	
	
	
	public Integer getNbDHeureTotalPaye() {
		return nbDHeureTotalPaye;
	}
	public void setNbDHeureTotalPaye(Integer nbDHeureTotalPaye) {
		this.nbDHeureTotalPaye = nbDHeureTotalPaye;
	}
	
	public Date getDatePayementEffectif() {
		return datePayementEffectif;
	}
	public void setDatePayementEffectif(Date datePayementEffectif) {
		this.datePayementEffectif = datePayementEffectif;
	}
	@Transient
	public Boolean getCocher() {
		return cocher;
	}
	public void setCocher(Boolean cocher) {
		this.cocher = cocher;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUserGen", nullable=false)
	public Utilisateur getUserGenerateur() {
		return userGenerateur;
	}
	public void setUserGenerateur(Utilisateur userGenerateur) {
		this.userGenerateur = userGenerateur;
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
		PaiementGenere other = (PaiementGenere) obj;
		if (idPaiement == null) {
			if (other.idPaiement != null)
				return false;
		} else if (!idPaiement.equals(other.idPaiement))
			return false;
		return true;
	}
	
}
