package com.chaka.projet.service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chaka.commons.service.FilePrintService;
import com.chaka.constantes.Constantes;
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.AnneeAcademique;
import com.tidiane.model.Classe;
import com.tidiane.model.Cycle;
import com.tidiane.model.Etudiant;
import com.tidiane.model.Inscription;

@Name("inscriptionService")
@Scope(ScopeType.SESSION)
public class InscriptionService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@In
	private Session dataSource;
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	EtudiantService etudiantService = (EtudiantService)Component.getInstance(EtudiantService.class);
	
	ClasseService classeService = (ClasseService)Component.getInstance(ClasseService.class);
	
	@In 
	FacesMessages facesMessages;
	
	FilePrintService filePrintService = (FilePrintService)Component.getInstance(FilePrintService.class);

	
	private Inscription inscription = new Inscription();
    private List<Inscription> listInscriptions = new ArrayList<Inscription>();
    private Classe filtreClasse;
    private Cycle filtreCycle;
    private AnneeAcademique filtreAcademique;
    private String filtreEtudiant;
    private Etudiant etudiant = new Etudiant();
    private List<Classe> listClasses = new ArrayList<Classe>();
    
    public void init(){
    	etudiant = new Etudiant();
        inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        filtreEtudiant="";
    }
    
    public String versInsc()
	{
    	init();
		return "/pages/parametrage/gestionInscription.xhtml";
	}
    
    public void consuler(Inscription inscription1){
        try {
        	StringBuilder hql = new StringBuilder();
            hql.append("from Inscription i");
            hql.append(" inner join fetch i.classe cl");
            hql.append(" inner join fetch  cl.cycle");
            hql.append(" inner join fetch cl.institut it");
            hql.append(" inner join fetch i.etudiant");
            hql.append(" inner join fetch i.anneeAcademique");
            hql.append(" where i.idInscription =:paramId");
            inscription = (Inscription) dataSource.createQuery(hql.toString())
                    .setParameter("paramId", inscription1.getIdInscription())
                    .uniqueResult();
            ChakaUtils.println("etud=+"+inscription.getEtudiant().getPrenom());
			filtreEtudiant = inscription.getEtudiant().getNumEleve();
			setFiltreCycle(inscription.getClasse().getCycle());
			listClasses = getClasseByCycle(filtreCycle);
			//etudiantService.consuler(inscription.getEtudiant());
			setEtudiant(inscription.getEtudiant());
			ChakaUtils.println("classe+"+inscription.getClasse()+" etud="+etudiant.getPrenom());
			ChakaUtils.println("classe+"+inscription.getClasse().getFraisInscription());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void ajout(){
        if(inscription.getClasse().getIdClasse()==null){
        	facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Erreur choix de la classe obligatoire!");
            return;
        }

        try {
            if(inscription.getIdInscription()==null) {
                if(estDejaInscrit(inscription.getAnneeAcademique(), filtreEtudiant)){
                    facesMessages.addToControlFromResourceBundle("erreurGenerique", "Etudiant déjà inscrit!");
                    return;
                }
                inscription.setRedouble(false);
                inscription.setEtudiant(etudiant);
                inscription.setDateInscription(new Date());
                inscription.setMontant(inscription.getClasse().getFraisInscription());
                
                String chaine = ChakaUtils.completeStr(
                		new SecureRandom().hashCode()+"", '0', 3, true);
                chaine = chaine.replaceAll("-", "");
                //String initiale = inscription.getEtudiant().getNumEleve().substring(0,2);
                inscription.setNumInscription(ChakaUtils.getCurrentYear(new Date())+" - "+chaine);
                
                dataSource.save(inscription);
                dataSource.flush();

                //generarRepoteparametroIndiv(inscription);
                
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Inscription réalisée avec succés: "+inscription.getNumInscription());
            }else {
                inscription.setMontant(inscription.getClasse().getFraisInscription());
                dataSource.update(inscription);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Inscription modifiée avec succés: "+inscription.getNumInscription());
            }
            init();
            listInscriptions = new ArrayList<Inscription>();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Erreur lors de l'inscription!");
        }
    }

    /*public Boolean dejaInscrit(Inscription inscription){


    }*/

    public List<Inscription> findInscriptions(){
        ChakaUtils.println("dans find inscriptttttt");
        //if(!listInscriptions.isEmpty())listInscriptions.clear();
        listInscriptions = allInscriptionsByFiltre(filtreClasse, filtreCycle,
                filtreAcademique, filtreEtudiant);
        if(!listInscriptions.isEmpty())ChakaUtils.println("taill list:"+listInscriptions.size());
        return listInscriptions;
    }
    
    public Etudiant findEtudiant(ValueChangeEvent e){
        if(e.getNewValue()==null)return null;
        filtreEtudiant = e.getNewValue().toString();
        ChakaUtils.println("dans find etud");
         etudiant = etudiantService.getEtudiantByNum(filtreEtudiant);
         //etudiant = eleveBusiness.getEtudiantByNum(inscription.getEtudiant().getNumEleve());
         if(etudiant!=null && etudiant.getIdEtudiant()!=null){
	        inscription.setEtudiant(etudiant);
	        ChakaUtils.println("etud bi nakala="+inscription.getEtudiant().getNom());
         }else
        	 facesMessages.addToControlFromResourceBundle("erreurGenerique", 
         			"Numéro Etudiant absent dans notre base!");
        return etudiant;
    }

    public void findClasseByCycle(ValueChangeEvent e) {
        ChakaUtils.println("entrez methodeeeeeeee");
        if (e.getNewValue()!=null/*filtreCycle != null && filtreCycle.getIdCycle() != null*/) {
        	filtreCycle = (Cycle)e.getNewValue();
            ChakaUtils.println("avec filtreeeeeeee="+filtreCycle.getIdCycle());
            listClasses = getClasseByCycle(filtreCycle);
        }else listClasses = classeService.allClasse();
    }
    
    public void getMontant(){
        if(inscription.getClasse()!=null && inscription.getClasse().getIdClasse()!=null)
            inscription.setMontant(inscription.getClasse().getFraisInscription());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String imprim(Inscription inscription1){
    	List<Inscription> list = new ArrayList<Inscription>();
        list.add(inscription1);
		
		Map parametros = new HashMap();
        //parametros.put("dateDebut", dateDebut);
        //parametros.put("dateFin", dateFin);
		parametros.put("logo", null);
		filePrintService.imprimer("school", "recuInscription", parametros,
				list, utilisateur,Constantes.ExportOption.PDF);
		
		return "";
	}

    @SuppressWarnings("unchecked")
	public List<Cycle> allCycles() {
    	StringBuilder hql = new StringBuilder();
        hql.append("from Cycle c order by c.libelle");
        return  dataSource.createQuery(hql.toString()).list();
    }
    
    @SuppressWarnings("unchecked")
	public List<Inscription> allInscriptionsByFiltre(Classe classe, Cycle cycle, AnneeAcademique aa, String numEt) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Inscription i");
            hql.append(" inner join fetch i.classe cl");
            hql.append(" inner join fetch cl.institut it");
            hql.append(" inner join fetch cl.cycle cy");
            hql.append(" inner join fetch i.etudiant et");
            hql.append(" inner join fetch et.parent pa");
            hql.append(" inner join fetch i.anneeAcademique a");
            hql.append(" where i.idInscription >0");
            // hql.append(" and it.idInstitut =:paramInstit");
            if(utilisateur.getInstitut()!=null)hql.append(" and it.idInstitut =:paramInstit");
            if(utilisateur.getProfile().getLibelle().equals(Constantes.PARENT))
            	hql.append(" and pa.idUtilisateur =:paramParent");
            if(classe != null)hql.append(" and cl.idClasse =:paramClasse");
            if(cycle != null)hql.append(" and cy.idCycle =:paramCycle");

            if(aa != null)hql.append(" and a.idAnneeAc =:paramAnAc");
            if(numEt != null && numEt.trim().length() !=0 )hql.append(" and et.numEleve =:paramEt");
            hql.append(" order by et.nom, et.prenom, a.anneeFin desc");
            Query q = dataSource.createQuery(hql.toString());
            if(classe != null) q.setParameter("paramClasse",classe.getIdClasse());
            if(cycle != null)q.setParameter("paramCycle",cycle.getIdCycle());
            if(aa != null) q.setParameter("paramAnAc",aa.getIdAnneeAc());
            if(utilisateur.getInstitut()!=null) q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
            if(numEt != null && numEt.trim().length() !=0 ) q.setParameter("paramEt",numEt);
            if(utilisateur.getProfile().getLibelle().equals(Constantes.PARENT))
            	q.setParameter("paramParent", utilisateur.getIdUtilisateur());
            List<Inscription> list = q.list();
            if(!list.isEmpty())ChakaUtils.println("liste non vide"+list.size());
            return  list;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private Boolean estDejaInscrit(AnneeAcademique aa, String filtreEtudiant){
        Boolean flag = false;
        List<Inscription> list = allInscriptionsByFiltre(null, null,
                aa, filtreEtudiant);
        if(!list.isEmpty())flag = true;
        return flag;
    }
    
    @SuppressWarnings("unchecked")
	public List<Classe> getClasseByCycle(Cycle cycle) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("select distinct c from Classe c");
            hql.append(" inner join fetch c.cycle cy");
            hql.append(" inner join fetch c.institut i");
            hql.append(" left outer join fetch c.listLnkCoefMatieres l");
            hql.append(" left outer join fetch l.matiere");
            hql.append(" where cy.idCycle =:paramCycle and i.idInstitut =:paramInstit");
            hql.append(" order by c.libelle");
            List<Classe> list = dataSource.createQuery(hql.toString())
                    .setParameter("paramCycle", cycle.getIdCycle())
                    .setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut())
                    .list();
            return  list;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }
    
	public Inscription getInscription() {
		return inscription;
	}
	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}
	public List<Inscription> getListInscriptions() {
		return listInscriptions;
	}
	public void setListInscriptions(List<Inscription> listInscriptions) {
		this.listInscriptions = listInscriptions;
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
	public List<Classe> getListClasses() {
		return listClasses;
	}
	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
	}


}
