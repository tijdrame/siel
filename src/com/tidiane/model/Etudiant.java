package com.tidiane.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.chaka.projet.entity.Utilisateur;

@Entity
public class Etudiant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idEtudiant;
    private String prenom;
    private String nom;
    private String adresse;
    private String telephone;
    private String lieuNaiss;
    private Date dateNaissance;
    private String email;
   
    private byte[] photo;

    
    private String numEleve;
    
    private Institut institut;
    
    private Utilisateur parent;
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getIdEtudiant() {
		return idEtudiant;
	}
	public void setIdEtudiant(Long idEtudiant) {
		this.idEtudiant = idEtudiant;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getLieuNaiss() {
		return lieuNaiss;
	}
	public void setLieuNaiss(String lieuNaiss) {
		this.lieuNaiss = lieuNaiss;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic(fetch=FetchType.EAGER)
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	@Column(unique=true)
	public String getNumEleve() {
		return numEleve;
	}
	public void setNumEleve(String numEleve) {
		this.numEleve = numEleve;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstitut", nullable=true)
	public Institut getInstitut() {
		return institut;
	}
	public void setInstitut(Institut institut) {
		this.institut = institut;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idParent", nullable=false)
	public Utilisateur getParent() {
		return parent;
	}
	public void setParent(Utilisateur parent) {
		this.parent = parent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEtudiant == null) ? 0 : idEtudiant.hashCode());
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
		Etudiant other = (Etudiant) obj;
		if (idEtudiant == null) {
			if (other.idEtudiant != null)
				return false;
		} else if (!idEtudiant.equals(other.idEtudiant))
			return false;
		return true;
	}
    

}
