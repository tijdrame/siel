package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.common.utils.ChakaUtils;
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.DossierMedical;

@Name("dossierMedicalService")
@Scope(ScopeType.SESSION)
public class DossierMedicalService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@In
	private Session dataSource;
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	@In 
	FacesMessages facesMessages;
	
	EtudiantService etudiantService = (EtudiantService)Component.getInstance(EtudiantService.class);
	InscriptionService inscriptionService = (InscriptionService)Component.getInstance(InscriptionService.class);
	
	private DossierMedical dossierMedical = new DossierMedical();
	private List<DossierMedical> listDossierMedicaux = new ArrayList<DossierMedical>();
	private String filtreEtudiant;
	
	public void init(){
		dossierMedical = new DossierMedical();
		listDossierMedicaux = new ArrayList<DossierMedical>();
		filtreEtudiant="";
	}
	
	public String versDossier()
	{
    	init();
		return "/pages/parametrage/dossierMedical.xhtml";
	}
	
	public void ajout(){
        try {
        	if(inscriptionService.getEtudiant()==null || inscriptionService.getEtudiant().getIdEtudiant()==null){
        		facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        				"Elève obligatoire!");
        		return;
        	}
        	ChakaUtils.println("etud = "+inscriptionService.getEtudiant().getNom());
            if(dossierMedical.getIdDossier()==null) {
            	dossierMedical.setEtudiant(inscriptionService.getEtudiant());
            	dataSource.save(dossierMedical);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Dossier médical ajouté avec succés!");
            }else {
            	dataSource.update(dossierMedical);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Dossier médical modifié avec succés!");
            }
            inscriptionService.init();
            init();
            //listMaladies = allMaladies();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout du dossier médical!");
        }
    }
	
	
	public DossierMedical getDossierMedical() {
		return dossierMedical;
	}
	public void setDossierMedical(DossierMedical dossierMedical) {
		this.dossierMedical = dossierMedical;
	}
	public List<DossierMedical> getListDossierMedicaux() {
		return listDossierMedicaux;
	}
	public void setListDossierMedicaux(List<DossierMedical> listDossierMedicaux) {
		this.listDossierMedicaux = listDossierMedicaux;
	}

	public String getFiltreEtudiant() {
		return filtreEtudiant;
	}

	public void setFiltreEtudiant(String filtreEtudiant) {
		this.filtreEtudiant = filtreEtudiant;
	}
	

}
