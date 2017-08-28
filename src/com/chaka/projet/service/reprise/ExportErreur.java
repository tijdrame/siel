package com.chaka.projet.service.reprise;

public class ExportErreur {
	
	private String description;
	
	private Integer nbLigne;
	
	private Integer nbCol;
	
	private Integer nbFeuil;
	
	public ExportErreur(String description, Integer nbFeuil, Integer nbLigne, Integer nbCol){
		this.description = description;
		this.nbLigne = nbLigne;
		this.nbCol = nbCol;
		this.nbFeuil = nbFeuil;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNbLigne() {
		return nbLigne;
	}

	public void setNbLigne(Integer nbLigne) {
		this.nbLigne = nbLigne;
	}

	public Integer getNbCol() {
		return nbCol;
	}

	public void setNbCol(Integer nbCol) {
		this.nbCol = nbCol;
	}

	public Integer getNbFeuil() {
		return nbFeuil;
	}

	public void setNbFeuil(Integer nbFeuil) {
		this.nbFeuil = nbFeuil;
	}
	
	

}
