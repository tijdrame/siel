package com.tidiane.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by tidiane on 29/08/16.
 */
@Entity
public class Fonction implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idFonction;
    private String libelle;
    private String libelleCourt;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(Long idFonction) {
        this.idFonction = idFonction;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelleCourt() {
        return libelleCourt;
    }

    public void setLibelleCourt(String libelleCourt) {
        this.libelleCourt = libelleCourt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fonction fonction = (Fonction) o;

        return idFonction.equals(fonction.idFonction);

    }

    @Override
    public int hashCode() {
        return idFonction.hashCode();
    }
}
