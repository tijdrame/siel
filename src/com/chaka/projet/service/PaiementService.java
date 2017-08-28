package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

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
import com.tidiane.model.LnkPaiemtMois;
import com.tidiane.model.MoisConcerne;
import com.tidiane.model.Paiement;


@Name("paiementService")
@Scope(ScopeType.SESSION)
public class PaiementService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@In
	private Session dataSource;
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	@In(required = false)
	@Out(required = false)
	private EtudiantService etudiantService;
	
	@In(required = false)
	@Out(required = false)
	private ClasseService classeService;
	
	@In 
	FacesMessages facesMessages;
	
	FilePrintService filePrintService = (FilePrintService)Component.getInstance(FilePrintService.class);
	
	InscriptionService inscriptionService = (InscriptionService)Component.getInstance(InscriptionService.class);
	
	private Paiement paiement = new Paiement();
	
	private List<Paiement>listPaiements = new ArrayList<Paiement>();
	
	private Classe filtreClasse;
    private Cycle filtreCycle;
    private AnneeAcademique filtreAcademique;
    private String filtreEtudiant;
    private Etudiant etudiant = new Etudiant();
    private List<Classe> listClasses = new ArrayList<Classe>();
    private List<MoisConcerne> listMoisConcernesChoisi = new ArrayList<MoisConcerne>();
    @SuppressWarnings("unused")
	private List<MoisConcerne> listMoisConcernes = new ArrayList<MoisConcerne>();
    
    public void init(){
    	etudiant = new Etudiant();
    	paiement = new Paiement();
        filtreEtudiant="";
        filtreClasse = null;
        filtreCycle =null;
        filtreAcademique=null;
        listMoisConcernesChoisi = new ArrayList<MoisConcerne>();
    }
    
    public String versPaiement()
	{
    	init();
		return "/pages/parametrage/gestionPaiement.xhtml";
	}
    
    public void ajout(){
        try {
        	List<LnkPaiemtMois> list = new ArrayList<LnkPaiemtMois>();
        	
            if(paiement.getIdPaiement()==null) {
            	for (MoisConcerne itMoisConcerne : listMoisConcernesChoisi) {
    				LnkPaiemtMois lnkPaiemtMois = new LnkPaiemtMois();
    				lnkPaiemtMois.setMoisConcerne(itMoisConcerne);
    				lnkPaiemtMois.setPaiement(paiement);
    				list.add(lnkPaiemtMois);
    				paiement.setListMoisConcerne(list);
    			}
            	ChakaUtils.println("comm bi="+paiement.getCommentaires()+
                		" mois yi="+listMoisConcernesChoisi.size());
                
                paiement.setDatePaiement(new Date());
                paiement.setUserSaisie(utilisateur);
                dataSource.save(paiement);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Paiment effectué avec succés!");
                
            }else {
            	if(!paiement.getListMoisConcerne().isEmpty()){
            		for(LnkPaiemtMois it:paiement.getListMoisConcerne()){
            			dataSource.delete(it);
            		}
            		dataSource.flush();
            	}
            	for (MoisConcerne itMoisConcerne : listMoisConcernesChoisi) {
    				LnkPaiemtMois lnkPaiemtMois = new LnkPaiemtMois();
    				lnkPaiemtMois.setMoisConcerne(itMoisConcerne);
    				lnkPaiemtMois.setPaiement(paiement);
    				list.add(lnkPaiemtMois);
    				paiement.setListMoisConcerne(list);
    			}
            	/*for (MoisConcerne itMoisConcerne : listMoisConcernesChoisi) {
					LnkPaiemtMois lnkPaiemtMois = new LnkPaiemtMois();
					lnkPaiemtMois.setMoisConcerne(itMoisConcerne);
					lnkPaiemtMois.setPaiement(paiement);
				}*/
            	//paiement.set
            	dataSource.update(paiement);
            	dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Paiment modifié avec succés!");
                
            }
            init();
            //listPaiements = paiementBusiness.findPaiementByCriteres(paiement.);
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Erreur lors du paiement!");
        }
    }
    
    public void findInscription(ValueChangeEvent e){
    	if(e.getNewValue()==null)return;
    	filtreEtudiant = e.getNewValue().toString();
        List<Inscription>list = inscriptionService.allInscriptionsByFiltre(filtreClasse, filtreCycle,
                filtreAcademique, filtreEtudiant);
        if(!list.isEmpty())
        ChakaUtils.println("list trouve="+list.get(0).getIdInscription());
        if(!list.isEmpty())paiement.setInscription(list.get(0));
        else facesMessages.addToControlFromResourceBundle("erreurGenerique", 
    			"Aucune inscription avec ces critéres!"); 
    }
    
    public List<Paiement> findPaiement(){
        return listPaiements = findPaiementByCriteres(filtreClasse, filtreEtudiant, filtreAcademique);
    }
    
    public Paiement consuler(Paiement paiement1) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("select distinct p from Paiement p");
            hql.append(" inner join fetch p.inscription i");
            hql.append(" inner join fetch p.listMoisConcerne li");
            hql.append(" inner join fetch li.moisConcerne");
            hql.append(" inner join fetch i.classe cl");
            hql.append(" inner join fetch  cl.cycle ");
            hql.append(" inner join fetch cl.institut it");
            hql.append(" inner join fetch i.etudiant");
            hql.append(" inner join fetch i.anneeAcademique");
            hql.append(" where p.idPaiement =:paramId");
            paiement = (Paiement) dataSource.createQuery(hql.toString())
                    .setParameter("paramId", paiement1.getIdPaiement())
                    .uniqueResult();
            listMoisConcernesChoisi = new ArrayList<MoisConcerne>();
            setFiltreAcademique(paiement.getInscription().getAnneeAcademique());
            setFiltreCycle(paiement.getInscription().getClasse().getCycle());
            setFiltreClasse(paiement.getInscription().getClasse());
            setFiltreEtudiant(paiement.getInscription().getEtudiant().getNumEleve());
            for(LnkPaiemtMois itLnk:paiement.getListMoisConcerne()){
            	listMoisConcernesChoisi.add(itLnk.getMoisConcerne());
            	
            }
            
            return paiement;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    @SuppressWarnings("unchecked")
	public List<Paiement> findPaiementByCriteres(Classe classe, String NumEleve, AnneeAcademique aa) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("select distinct p from Paiement p");
            hql.append(" inner join fetch p.listMoisConcerne li");
            hql.append(" inner join fetch li.moisConcerne");
            hql.append(" inner join fetch p.inscription i");
            hql.append(" inner join fetch i.classe cl");
            hql.append(" inner join fetch cl.institut it");
            hql.append(" inner join fetch cl.cycle cy");
            hql.append(" inner join fetch i.etudiant et");
            hql.append(" inner join fetch i.anneeAcademique a");
            hql.append(" where it.idInstitut =:paramInstit");
            if(classe != null)hql.append(" and cl.idClasse =:paramClasse");
            if(aa != null)hql.append(" and a.idAnneeAc =:paramAnAc");
            if(NumEleve != null && NumEleve.trim().length() !=0 )hql.append(" and et.numEleve =:paramEt");
            hql.append(" order by et.nom, et.prenom, a.anneeFin desc");
            Query q = dataSource.createQuery(hql.toString());
            if(classe != null) q.setParameter("paramClasse",classe.getIdClasse());
            if(aa != null) q.setParameter("paramAnAc",aa.getIdAnneeAc());
            if(NumEleve != null && NumEleve.trim().length() !=0 ) q.setParameter("paramEt",NumEleve);
            q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
            List<Paiement> list = q.list();
            if(!list.isEmpty()) ChakaUtils.println("liste paimet non vide"+list.size());
            return  list;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void findClasseByCycle(ValueChangeEvent e) {
        ChakaUtils.println("entrez methodeeeeeeee");
        if (e.getNewValue()!=null/*filtreCycle != null && filtreCycle.getIdCycle() != null*/) {
        	filtreCycle = (Cycle)e.getNewValue();
            ChakaUtils.println("avec filtreeeeeeee="+filtreCycle.getIdCycle());
            listClasses = getClasseByCycle(filtreCycle);
        }else listClasses = classeService.allClasse();
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String imprim(Paiement paiement1){
    	List<Paiement> list = new ArrayList<Paiement>();
        list.add(paiement1);
		
		Map parametros = new HashMap();
        //parametros.put("dateDebut", dateDebut);
        //parametros.put("dateFin", dateFin);
		parametros.put("logo", null);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sc = (ServletContext) ec.getContext();
		String reportFile = sc.getRealPath("/report/school");
		ChakaUtils.println("sub rep="+reportFile);
		parametros.put("SUBREPORT_DIR", reportFile+"/");
		filePrintService.imprimer("school", "recuPaiement", parametros,
				list, utilisateur,Constantes.ExportOption.PDF);
		
		return "";
	}

	public EtudiantService getEtudiantService() {
		return etudiantService;
	}

	public void setEtudiantService(EtudiantService etudiantService) {
		this.etudiantService = etudiantService;
	}

	public Paiement getPaiement() {
		return paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
	}

	public List<Paiement> getListPaiements() {
		return listPaiements;
	}

	public void setListPaiements(List<Paiement> listPaiements) {
		this.listPaiements = listPaiements;
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

	@SuppressWarnings("unchecked")
	public List<MoisConcerne> getListMoisConcernes() {
		StringBuilder hql = new StringBuilder();
        hql.append("from MoisConcerne t order by idMois");
		return listMoisConcernes=dataSource.createQuery(hql.toString()).list();
	}

	public void setListMoisConcernes(List<MoisConcerne> listMoisConcernes) {
		this.listMoisConcernes = listMoisConcernes;
	}

	public List<MoisConcerne> getListMoisConcernesChoisi() {
		return listMoisConcernesChoisi;
	}

	public void setListMoisConcernesChoisi(List<MoisConcerne> listMoisConcernesChoisi) {
		this.listMoisConcernesChoisi = listMoisConcernesChoisi;
	}

    
}
