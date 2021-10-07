package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.hibernate.HibernateException;
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
import com.tidiane.model.Cycle;
import com.tidiane.model.Etudiant;
import com.tidiane.model.Inscription;
import com.tidiane.model.Matiere;
import com.tidiane.model.Presence;
import com.tidiane.model.Semestre;

@Name("presenceService")
@Scope(ScopeType.SESSION)
public class PresenceService implements Serializable {

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
	
	ClasseService classeService = (ClasseService)Component.getInstance(ClasseService.class);
	
	private Classe filtreClasse;
    private Matiere filtreMatiere;
    private String filtreEtudiant;
    private Etudiant etudiant;
    private AnneeAcademique filtreAcademique;
    private Semestre filtreSemestre;
    private Cycle filtreCycle;
    private Presence presence = new Presence();
    private List<Presence> listPresences = new ArrayList<Presence>();
    private List<Semestre>listSemestres = new ArrayList<Semestre>();

    private List<Classe> listClasses = new ArrayList<Classe>();
    private List<Inscription> listInscription = new ArrayList<Inscription>();

    public void init(){
        presence = new Presence();
        filtreCycle = null;

        filtreEtudiant="";
        filtreClasse = null ;
        filtreMatiere = null;
        etudiant =  null ;
        filtreAcademique =  null;
        filtreSemestre = null;
    }
    
    public String versPresence()
	{
    	init();
		return "/pages/parametrage/presence.xhtml";
	}
    
    public String versFiche()
	{
    	init();
		return "/pages/parametrage/fichePresence.xhtml";
	}
    
    public void consuler(Presence presence1){
        try {
			//filtreEtudiant = inscription1.getEtudiant().getNumEleve();
			StringBuilder hql = new StringBuilder();
			hql.append("from Presence p");
			hql.append(" inner join fetch p.matiere m");
			hql.append(" inner join fetch p.inscription i");
			hql.append(" inner join fetch i.classe c");
			hql.append(" inner join fetch c.institut it");
			hql.append(" inner join fetch i.etudiant et");
			hql.append(" inner join fetch i.anneeAcademique ac");
			hql.append(" inner join fetch p.semestre");
			hql.append(" where p.idPresence =:paramId");
			presence = (Presence) dataSource.createQuery(hql.toString())
			        .setParameter("paramId", presence1.getIdPresence())
			        .uniqueResult();
			filtreEtudiant = presence.getInscription().getEtudiant().getNumEleve();
			setEtudiant(etudiantService.getEtudiantByNum(filtreEtudiant));
			filtreClasse = presence.getInscription().getClasse();
			filtreAcademique = presence.getInscription().getAnneeAcademique();
			filtreCycle = presence.getInscription().getClasse().getCycle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void ajout(){
        try {
            if(presence.getIdPresence()==null) {
                //presence.setInscription(findInscription());
                presence.setDateAbsRetard(new Date());
                presence.setUserSaisie(utilisateur);
                dataSource.save(presence);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Fiche de présence ajoutée avec succés!");
               
            }else {
            	presence.setDateModif(new Date());
                presence.setUserModif(utilisateur);
            	dataSource.update(presence);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Fiche de présence modifiée avec succés!");
            }
            init();
            //listPresences = presenceBusiness.;
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Erreur lors de l'ajout/modification de la présence!");
        }
    }
    
    public Inscription findInscription(ValueChangeEvent e){
    	if(e.getNewValue()==null)return null;
    	filtreAcademique = (AnneeAcademique)e.getNewValue();
    	if(filtreAcademique==null || etudiant==null || filtreClasse==null)return null;
        ChakaUtils.println("academ"+filtreAcademique.getAnneeAc());
        ChakaUtils.println("etudi"+etudiant.getNom());
        ChakaUtils.println("filtre cla"+filtreClasse.getLibelle());
        List<Inscription> list = inscriptionService.allInscriptionsByFiltre(filtreClasse, null,filtreAcademique,
                etudiant.getNumEleve());
        if(!list.isEmpty()){
            presence.setInscription(list.get(0));
            return list.get(0);
        }
        else{
        	facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Aucune inscription ne correspond avec ces critères!");
            presence.setInscription(null);
            return null;
        }
    }
    
    public List<Presence> findPresences(){
        ChakaUtils.println("dans find presence");
        ChakaUtils.println("academmmmm:"+filtreAcademique);
        //if(!listInscriptions.isEmpty())listInscriptions.clear();
        if(filtreEtudiant != null && filtreEtudiant.trim().length() !=0 ){
            setEtudiant(etudiantService.getEtudiantByNum(filtreEtudiant));
            if(etudiant==null ) {
                ChakaUtils.println("jisoulll kokouuuu");
                facesMessages.addToControlFromResourceBundle("erreurGenerique", 
            			"Ce numéro étudiant n'existe pas dans nos livres!");
                return listPresences = new ArrayList<Presence>();
            }
        }
        listPresences = presencesByClasse(filtreClasse, filtreAcademique,etudiant,
                filtreMatiere, filtreSemestre);
        if(!listPresences.isEmpty())ChakaUtils.println("taill list pres:"+listPresences.size());
        return listPresences;
    }
    
    public Etudiant findEtudiant(ValueChangeEvent e){
    	if(e.getNewValue()==null)return null;
    	filtreEtudiant = (String) e.getNewValue();
        ChakaUtils.println("dans find etud");
        etudiant = etudiantService.getEtudiantByNum(filtreEtudiant);
        if(etudiant==null)
        	facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Numéro d'élève abscent dans notre base!");
        return etudiant;
    }
    
    public void findClasseByCycle(ValueChangeEvent e) {
        ChakaUtils.println("entrez methodeeeeeeee");
        if (e.getNewValue()!=null/*filtreCycle != null && filtreCycle.getIdCycle() != null*/) {
        	filtreCycle = (Cycle)e.getNewValue();
            ChakaUtils.println("avec filtreeeeeeee="+filtreCycle.getIdCycle());
            listClasses = inscriptionService.getClasseByCycle(filtreCycle);
        }else listClasses = classeService.allClasse();
    }
    
    @SuppressWarnings("unchecked")
	public List<Presence> presencesByClasse(Classe classe, AnneeAcademique anneeAcademique, Etudiant etudiant,
            Matiere matiere, Semestre semestre) {
    	try {
			StringBuilder hql = new StringBuilder();
			hql.append("from Presence p");
			hql.append(" inner join fetch p.matiere m");
			hql.append(" inner join fetch p.inscription i");
			hql.append(" inner join fetch i.classe cl");
			hql.append(" inner join fetch i.etudiant et");
			hql.append(" inner join fetch et.parent pa");
			hql.append(" inner join fetch et.institut it");
			hql.append(" inner join fetch i.anneeAcademique ac");
			hql.append(" inner join fetch p.semestre s");
			hql.append(" where it.idInstitut =:paramInstit");
			if(utilisateur.getProfile().getLibelle().equals(Constantes.PARENT))
            	hql.append(" and pa.idUtilisateur =:paramParent");
			if(classe != null)hql.append(" and cl.idClasse =:paramClasse");
			if(anneeAcademique != null)hql.append(" and ac.idAnneeAc =:paramAnneeAc");
			if(etudiant != null)hql.append(" and et.idEtudiant =:paramEtudiant");
			if(matiere != null)hql.append(" and m.idMatiere =:paramMatiere");
			if(semestre != null)hql.append(" and s.idSemestre =:paramSemestre");
			Query q = dataSource.createQuery(hql.toString());
			if(classe != null) q.setParameter("paramClasse",classe.getIdClasse());
			if(anneeAcademique != null)q.setParameter("paramAnneeAc",anneeAcademique.getIdAnneeAc());
			if(etudiant != null)q.setParameter("paramEtudiant",etudiant.getIdEtudiant());
			if(matiere != null)q.setParameter("paramMatiere",matiere.getIdMatiere());
			if(semestre != null)q.setParameter("paramSemestre",semestre.getIdSemestre());
			q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
			if(utilisateur.getProfile().getLibelle().equals(Constantes.PARENT))
            	q.setParameter("paramParent", utilisateur.getIdUtilisateur());
			List<Presence> list = q.list();
			return list;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public List<Inscription> findInscriptions(){
    	if (filtreClasse != null && filtreCycle != null && filtreAcademique != null && filtreSemestre != null
				&& filtreMatiere != null ) {
    	listInscription = new ArrayList<Inscription>();
    	listInscription = inscriptionService.allInscriptionsByFiltre(filtreClasse, filtreCycle, filtreAcademique, null);
    	}else {
			facesMessages.addToControlFromResourceBundle("erreurGenerique",
					"Tous les critères de recherche sont obligatoires!");
		}
    	return listInscription;
    	
    }
    
    public void save(){
    	if(dejaSave()){
    		facesMessages.addToControlFromResourceBundle("erreurGenerique",
					"Fiche de présence déjà enregistrée!");
    		return;
    	}
    	if(!listInscription.isEmpty()){
			for (Inscription eachInscription : listInscription) {
				if(eachInscription.getAbscence() || eachInscription.getRetard()){
					Presence presence = new Presence();
					presence.setInscription(eachInscription);
					presence.setMatiere(filtreMatiere);
					presence.setSemestre(filtreSemestre);
					if(eachInscription.getAbscence())
						presence.setTypeAbsence("Abscence");
					if(eachInscription.getRetard())
						presence.setTypeAbsence("Retard");
					presence.setDateAbsRetard(new Date());
			        presence.setUserSaisie(utilisateur);
			        dataSource.save(presence);
				}
			}
			dataSource.flush();
	        facesMessages.addToControlFromResourceBundle("infoGenerique", 
	        		"Fiche de présence ajoutée avec succés!");
		}
    	
    	
        
    }
    
    private Boolean dejaSave(){
    	Boolean flag = false;
    	List<Presence> list = presencesByClasse(filtreClasse, filtreAcademique, null, filtreMatiere, filtreSemestre);
    	if(!list.isEmpty())flag=true;
    	return flag;
    }

    @SuppressWarnings("unchecked")
	public List<Presence> presencesByEtudiant(Etudiant etudiant) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Presence p");
            hql.append(" inner join fetch p.matiere m");
            hql.append(" inner join fetch p.inscription i");
            hql.append(" inner join fetch i.classe c");
            hql.append(" inner join fetch i.etudiant et");
            hql.append(" inner join fetch et.institut it");
            hql.append(" inner join fetch i.anneeAcademique ac");
            hql.append(" inner join fetch p.semestre");
            hql.append(" where et.idEtudiant =:paramId and it.idInstitut =:paramInstit");
            List<Presence> list = dataSource.createQuery(hql.toString())
                    .setParameter("paramId", etudiant.getIdEtudiant())
                    .setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut())
                    .list();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	public Classe getFiltreClasse() {
		return filtreClasse;
	}

	public void setFiltreClasse(Classe filtreClasse) {
		this.filtreClasse = filtreClasse;
	}

	public Matiere getFiltreMatiere() {
		return filtreMatiere;
	}

	public void setFiltreMatiere(Matiere filtreMatiere) {
		this.filtreMatiere = filtreMatiere;
	}

	public String getFiltreEtudiant() {
		return filtreEtudiant;
	}

	public void setFiltreEtudiant(String filtreEtudiant) {
		this.filtreEtudiant = filtreEtudiant;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
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

	public Cycle getFiltreCycle() {
		return filtreCycle;
	}

	public void setFiltreCycle(Cycle filtreCycle) {
		this.filtreCycle = filtreCycle;
	}

	public Presence getPresence() {
		return presence;
	}

	public void setPresence(Presence presence) {
		this.presence = presence;
	}

	public List<Presence> getListPresences() {
		return listPresences;
	}

	public void setListPresences(List<Presence> listPresences) {
		this.listPresences = listPresences;
	}

	public List<Semestre> getListSemestres() {
		return listSemestres;
	}

	public void setListSemestres(List<Semestre> listSemestres) {
		this.listSemestres = listSemestres;
	}

	public List<Classe> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
	}

	public List<Inscription> getListInscription() {
		return listInscription;
	}

	public void setListInscription(List<Inscription> listInscription) {
		this.listInscription = listInscription;
	}

}
