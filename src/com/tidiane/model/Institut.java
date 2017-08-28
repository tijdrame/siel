package com.tidiane.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by tidiane on 24/08/16.
 */
@Entity
public class Institut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idInstitut;
    private String nomInstitut;
    private String telephone;
    private String email;
    private String adresse;
    private String maskTel;
    
    private Boolean deleted = false;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getIdInstitut() {
        return idInstitut;
    }

    public void setIdInstitut(Long idInstitut) {
        this.idInstitut = idInstitut;
    }

    public String getNomInstitut() {
        return nomInstitut;
    }

    public void setNomInstitut(String nomInstitut) {
        this.nomInstitut = nomInstitut;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMaskTel() {
        return maskTel;
    }

    public void setMaskTel(String maskTel) {
        this.maskTel = maskTel;
    }

    public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Institut institut = (Institut) o;

        return idInstitut.equals(institut.idInstitut);

    }

    @Override
    public int hashCode() {
        return idInstitut.hashCode();
    }
}
