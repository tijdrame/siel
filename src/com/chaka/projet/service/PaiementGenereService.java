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
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.AnneeAcademique;
import com.tidiane.model.Classe;
import com.tidiane.model.Cycle;
import com.tidiane.model.Matiere;
import com.tidiane.model.PaiementGenere;
import com.tidiane.model.Semestre;

@Name("paiementGenereService")
@Scope(ScopeType.SESSION)
public class PaiementGenereService implements Serializable {

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
	CoursService coursService = (CoursService) Component.getInstance(CoursService.class);
	
	private List<PaiementGenere> listPaiements = new ArrayList<PaiementGenere>();
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
		paiementGenere = new  PaiementGenere();
		filtreCycle = null;
		filtreClasse = null;
		filtreAcademique = null;
		filtreSemestre = null;
		filtreProfesseur = null;
		filtreMatiere = null;
		listPaiements = new ArrayList<PaiementGenere>();
		this.selectionTous="cocherTous";
	}
	
	public String versPaiement()
	{
    	init();
		return "/pages/parametrage/paiementCours.xhtml";
	}
	
	@SuppressWarnings("unchecked")
	public void findPaiement(){
		if (filtreAcademique != null && filtreSemestre != null
				&& filtreProfesseur!=null) {
			listPaiements = new ArrayList<PaiementGenere>();
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct p from PaiementGenere p");			
			hql.append(" inner join fetch p.userGenerateur ug");
			hql.append(" inner join fetch p.listCours c");
			
			hql.append(" inner join fetch c.matiere m");
			hql.append(" inner join fetch c.classe cl");
			hql.append(" inner join fetch c.semestre s");
			hql.append(" inner join fetch c.professeur pr");
			hql.append(" inner join fetch c.academique a");
			hql.append(" inner join fetch c.userSaisie u");
			hql.append(" inner join fetch u.institut i");
			hql.append(" where pr.idUtilisateur =:paramID");
			hql.append(" and a.idAnneeAc =:paramAcad");
			hql.append(" and s.idSemestre =:paramSemestre");
			hql.append(" and i =:paramInst");
			if(filtreClasse!=null)
				hql.append(" and cl.idClasse =:paramClasse");
			if(filtreMatiere!=null)
				hql.append(" and m.idMatiere =:paramMat");
			//if(filtreProfesseur!=null)
				//hql.append(" and p.idUtilisateur =:paramID");
			hql.append(" order by pr.nom asc, pr.prenom asc");
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
			listPaiements = q.list();
		}else {
			facesMessages.addToControlFromResourceBundle("erreurGenerique",
					"Tous les critères de recherche sont obligatoires!");
		}
	}
	
	public void consuler(PaiementGenere genere){
		StringBuilder hql = new StringBuilder();
		hql.append("from PaiementGenere p");
		hql.append(" inner join fetch p.listCours c");
		hql.append(" inner join fetch c.matiere m");
		hql.append(" inner join fetch c.classe cl");
		hql.append(" inner join fetch c.semestre s");
		hql.append(" inner join fetch c.professeur pr");
		hql.append(" inner join fetch c.academique a");
		hql.append(" inner join fetch c.userSaisie u");
		hql.append(" inner join fetch u.institut i");
		hql.append(" where p.idPaiement =:paramID");
		paiementGenere = (PaiementGenere) dataSource.createQuery(hql.toString())
				.setParameter("paramID", genere.getIdPaiement())
				.uniqueResult();
	}
	
	public void payer(){
		if(listPaiements.isEmpty()){
			facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Liste vide!");
			return;
		}
		Boolean flag = false;
		for (PaiementGenere eachGenere  : listPaiements) {
			if(eachGenere.getCocher())flag=true;
		}
		if(!flag){
			facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Cochez au moins une ligne!");
			return;
		}
		flag=false;
		for (PaiementGenere eachGenere  : listPaiements) {
			if(!eachGenere.getPayerON() && eachGenere.getCocher()){
				flag=true;
				//eachGenere.setDatePayement(new Date());
				eachGenere.setPayerON(true);
				eachGenere.setUserPayeur(utilisateur);
				eachGenere.setDatePayementEffectif(new Date());
				dataSource.update(eachGenere);
			}
			
		}
		if(flag){
			dataSource.flush();
			facesMessages.addToControlFromResourceBundle("infoGenerique", 
	        		"Paiement effectué avec succés!");
			findPaiement();
			deselectionTout();
		}else{
			facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Paiement dejà effectué!!");
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
	/**
	 * @param e
	 *selectionner tout  ou de selectionner tout.
	 */
	public void selectionTout(ValueChangeEvent e)
 	{
		Boolean valeur=(Boolean)e.getNewValue();
		if(valeur){
			for(int i=0;i<getListPaiements().size();i++){
				getListPaiements().get(i).setCocher(true);
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
		  for(int i=0;i<getListPaiements().size();i++){
			  getListPaiements().get(i).setCocher(false);
			}
		  this.selectionTous="cocherTous";
	}
	
	
	private PaiementGenere paiementGenere = new PaiementGenere();
	public List<PaiementGenere> getListPaiements() {
		return listPaiements;
	}
	public void setListPaiements(List<PaiementGenere> listPaiements) {
		this.listPaiements = listPaiements;
	}
	public PaiementGenere getPaiementGenere() {
		return paiementGenere;
	}
	public void setPaiementGenere(PaiementGenere paiementGenere) {
		this.paiementGenere = paiementGenere;
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
		return listProfs = coursService.allProfs();
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
