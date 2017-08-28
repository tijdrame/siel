package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

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
import com.tidiane.model.AnneeAcademique;
import com.tidiane.model.Classe;
import com.tidiane.model.Cours;
import com.tidiane.model.Cycle;
import com.tidiane.model.Matiere;
import com.tidiane.model.Presence;
import com.tidiane.model.Semestre;

@Name("coursService")
@Scope(ScopeType.SESSION)
public class CoursService implements Serializable {

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
	
	InscriptionService inscriptionService = (InscriptionService) Component.getInstance(InscriptionService.class);
	ClasseService classeService = (ClasseService) Component.getInstance(ClasseService.class);
	MatiereService matiereService = (MatiereService) Component.getInstance(MatiereService.class);
	
	Cours cours = new Cours();
	
	List<Cours> listCours = new ArrayList<Cours>();
	private Classe filtreClasse;
	private Cycle filtreCycle;
	private AnneeAcademique filtreAcademique;
	private Semestre filtreSemestre;
	private Matiere filtreMatiere;
	private Utilisateur filtreProfesseur;
	
	private List<Classe> listClasses = new ArrayList<Classe>();
	private List<Matiere> listMatieres = new ArrayList<Matiere>();
	
	public void init() {
		cours = new Cours();
		filtreCycle = null;
		filtreClasse = null;
		filtreAcademique = null;
		filtreSemestre = null;
		filtreProfesseur = null;
		filtreMatiere = null;
		listCours = new ArrayList<Cours>();
	}
	
	public String versBulletin()
	{
    	init();
		return "/pages/parametrage/cours.xhtml";
	}
	
	public void ajout(){
        try {
            if(cours.getIdCours()==null) {
                cours.setDateSaisie(new Date());
                cours.setUserSaisie(utilisateur);
                dataSource.save(cours);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Cours enregistré avec succés!");
               
            }else {
            	cours.setDateModif(new Date());
            	cours.setUserModif(utilisateur);
            	dataSource.update(cours);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Cour modifié avec succés!");
            }
            init();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Erreur lors de l'ajout/modification de la présence!");
        }
    }
	
	public void consuler(Cours cours1){
        try {
			//filtreEtudiant = inscription1.getEtudiant().getNumEleve();
			StringBuilder hql = new StringBuilder();
			hql.append("from Cours c");
			hql.append(" inner join fetch c.matiere m");
			hql.append(" inner join fetch c.classe cl");
			hql.append(" inner join fetch c.semestre s");
			hql.append(" inner join fetch c.professeur p");
			hql.append(" inner join fetch c.academique");
			hql.append(" where c.idCours =:paramId");
			cours = (Cours) dataSource.createQuery(hql.toString())
			        .setParameter("paramId", cours1.getIdCours())
			        .uniqueResult();
			filtreClasse = cours.getClasse();
			filtreAcademique = cours.getAcademique();
			filtreCycle = cours.getClasse().getCycle();
			filtreMatiere = cours.getMatiere();
			filtreProfesseur = cours.getProfesseur();
			filtreSemestre = cours.getSemestre();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void findClasseByCycle(ValueChangeEvent e) {
		ChakaUtils.println("entrez methodeeeeeeee");
		if (e.getNewValue() != null) {
			filtreCycle = (Cycle) e.getNewValue();
			ChakaUtils.println("avec filtreeeeeeee=" + filtreCycle.getIdCycle());
			listClasses = inscriptionService.getClasseByCycle(filtreCycle);
		} else
			listClasses = classeService.allClasse();
	}

	public void findMatiereByClasse(ValueChangeEvent e) {
		if (e.getNewValue() != null) {
			ChakaUtils.println("avec filtre cl");
			filtreClasse = (Classe) e.getNewValue();
			listMatieres = matiereService.matieresByClasse(filtreClasse);
		}
	}
	
	public Cours getCours() {
		return cours;
	}
	public void setCours(Cours cours) {
		this.cours = cours;
	}
	public List<Cours> getListCours() {
		return listCours;
	}
	public void setListCours(List<Cours> listCours) {
		this.listCours = listCours;
	}
	public Classe getFiltreClasse() {
		return filtreClasse;
	}
	public void setFiltreClasse(Classe filtreClasse) {
		this.filtreClasse = filtreClasse;
	}
	public Cycle getFiltreCycle() {
		return filtreCycle;
	}
	public void setFiltreCycle(Cycle filtreCycle) {
		this.filtreCycle = filtreCycle;
	}
	public AnneeAcademique getFiltreAcademique() {
		return filtreAcademique;
	}
	public void setFiltreAcademique(AnneeAcademique filtreAcademique) {
		this.filtreAcademique = filtreAcademique;
	}
	public Semestre getFiltreSemestre() {
		return filtreSemestre;
	}
	public void setFiltreSemestre(Semestre filtreSemestre) {
		this.filtreSemestre = filtreSemestre;
	}
	public Matiere getFiltreMatiere() {
		return filtreMatiere;
	}
	public void setFiltreMatiere(Matiere filtreMatiere) {
		this.filtreMatiere = filtreMatiere;
	}
	public Utilisateur getFiltreProfesseur() {
		return filtreProfesseur;
	}
	public void setFiltreProfesseur(Utilisateur filtreProfesseur) {
		this.filtreProfesseur = filtreProfesseur;
	}

	public List<Classe> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
	}

	public List<Matiere> getListMatieres() {
		return listMatieres;
	}

	public void setListMatieres(List<Matiere> listMatieres) {
		this.listMatieres = listMatieres;
	}

}
