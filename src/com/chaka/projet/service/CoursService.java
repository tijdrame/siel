package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.common.utils.ChakaUtils;
import com.chaka.constantes.Constantes;
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.AnneeAcademique;
import com.tidiane.model.Classe;
import com.tidiane.model.Cours;
import com.tidiane.model.Cycle;
import com.tidiane.model.Matiere;
import com.tidiane.model.PaiementGenere;
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
	@SuppressWarnings("unused")
	private List<Utilisateur> listProfs = new ArrayList<Utilisateur>();
	private String selectionTous;
	
	public void init() {
		cours = new Cours();
		filtreCycle = null;
		filtreClasse = null;
		filtreAcademique = null;
		filtreSemestre = null;
		filtreProfesseur = null;
		filtreMatiere = null;
		listCours = new ArrayList<Cours>();
		this.selectionTous="cocherTous";
	}
	
	public String versCours()
	{
    	init();
		return "/pages/parametrage/cours.xhtml";
	}
	
	public void ajout(){
        try {
            if(cours.getIdCours()==null) {
            	cours.setPayer(false);
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
	
	@SuppressWarnings("unchecked")
	public void findNotes(){
		if (filtreAcademique != null && filtreSemestre != null
				&& filtreProfesseur!=null) {
			listCours = new ArrayList<Cours>();
			StringBuilder hql = new StringBuilder();
			hql.append("from Cours c");
			hql.append(" inner join fetch c.matiere m");
			hql.append(" inner join fetch c.classe cl");
			hql.append(" inner join fetch c.semestre s");
			hql.append(" inner join fetch c.professeur p");
			hql.append(" inner join fetch c.academique a");
			hql.append(" inner join fetch c.userSaisie u");
			hql.append(" inner join fetch u.institut i");
			hql.append(" where p.idUtilisateur =:paramID");
			hql.append(" and a.idAnneeAc =:paramAcad");
			hql.append(" and s.idSemestre =:paramSemestre");
			hql.append(" and i =:paramInst");
			
			if(filtreClasse!=null)
				hql.append(" and cl.idClasse =:paramClasse");
			if(filtreMatiere!=null)
				hql.append(" and m.idMatiere =:paramMat");
			//if(filtreProfesseur!=null)
				//hql.append(" and p.idUtilisateur =:paramID");
			hql.append(" order by p.nom asc, p.prenom asc");
			Query q = dataSource.createQuery(hql.toString());
			q.setParameter("paramSemestre", filtreSemestre.getIdSemestre());
			q.setParameter("paramAcad", filtreAcademique.getIdAnneeAc());
			
			if(filtreClasse!=null)
				q.setParameter("paramClasse", filtreClasse.getIdClasse());
			//if(filtreProfesseur!=null)
				q.setParameter("paramID", filtreProfesseur.getIdUtilisateur());
			q.setParameter("paramInst", utilisateur.getInstitut());
			if(filtreMatiere!=null)
				q.setParameter("paramMat", filtreMatiere.getIdMatiere());
			listCours = q.list();
		} else {
			facesMessages.addToControlFromResourceBundle("erreurGenerique",
					"Tous les critères de recherche sont obligatoires!");
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
	
	@SuppressWarnings("unchecked")
	public List<Utilisateur> allProfs(){
		StringBuilder hql = new StringBuilder();
		hql.append("from Utilisateur u");
		hql.append(" inner join fetch u.institut i");
		hql.append(" inner join fetch u.profile p");
		hql.append(" where p.libelle =:paramLib");
		hql.append(" and i =:paramInst");
		
		hql.append(" order by u.nom asc, u.prenom asc");
		Query q = dataSource.createQuery(hql.toString());
		q.setParameter("paramInst", utilisateur.getInstitut());
		q.setParameter("paramLib", Constantes.PROFESSEUR);
		return q.list();
	}
	
	public void payer(){
		if(listCours.isEmpty()){
			facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Liste vide!");
			return;
		}
		Boolean flag = false;
		for (Cours eachCours  : listCours) {
			if(eachCours.getCocher())flag=true;
		}
		if(!flag){
			facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Cochez au moins une ligne!");
			return;
		}
		PaiementGenere paiementGenere = new PaiementGenere();
		flag=false;
		for (Cours eachCours  : listCours) {
			if(eachCours.getCocher()&& !eachCours.getPayer()){
				//eachCours.setPaiementGenere(paiementGenere);
				//eachCours.setPayer(true);
				flag = true;
				paiementGenere.setNbDHeureTotalPaye(paiementGenere.getNbDHeureTotalPaye()+eachCours.getDuree());
				//dataSource.update(eachCours);
			}
		}
		if(flag){
			paiementGenere.setDatePayement(new Date());
			paiementGenere.setPayerON(false);
			paiementGenere.setUserGenerateur(utilisateur);
			paiementGenere.setRefPaiement("Paiement des cours effectués");
			dataSource.save(paiementGenere);
			for (Cours eachCours  : listCours) {
				if(eachCours.getCocher()&& !eachCours.getPayer()){
					eachCours.setPaiementGenere(paiementGenere);
					eachCours.setPayer(true);
					dataSource.update(eachCours);
				}
			}
			dataSource.flush();
			facesMessages.addToControlFromResourceBundle("infoGenerique", 
	        		"Paiement généré avec succés!");
			findNotes();
			deselectionTout();
		}else{
			facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Heure dejà payer(marquer)!!");
		}
	}
	
	/**
	 * @param e
	 *selectionner tout  ou de selectionner tout.
	 */
	public void selectionTout(ValueChangeEvent e)
 	{
		Boolean valeur=(Boolean)e.getNewValue();
		if(valeur){
			for(int i=0;i<getListCours().size();i++){
				getListCours().get(i).setCocher(true);
			}
		this.selectionTous="decocherTous";
		}
		else{ 
			deselectionTout();
		}
	}
	
	/**
	 *deselectionner tout.
	 */
	public void deselectionTout()
	{
		  for(int i=0;i<getListCours().size();i++){
			  getListCours().get(i).setCocher(false);
			}
		  this.selectionTous="cocherTous";
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

	public List<Utilisateur> getListProfs() {
		return listProfs = allProfs();
	}

	public void setListProfs(List<Utilisateur> listProfs) {
		this.listProfs = listProfs;
	}

	public String getSelectionTous() {
		return selectionTous;
	}

	public void setSelectionTous(String selectionTous) {
		this.selectionTous = selectionTous;
	}

}
