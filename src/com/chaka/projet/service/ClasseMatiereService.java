package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.Classe;
import com.tidiane.model.LnkCoefMatiere;
import com.tidiane.model.Matiere;

@Name("classeMatiereService")
@Scope(ScopeType.PAGE)
public class ClasseMatiereService implements Serializable {

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
	
	/*@In(required = false)
	@Out(required = false)
	ClasseService classeService;*/
	
	private Classe classet = new Classe();
    private List<Classe> listClasses = new ArrayList<Classe>();
    private List<Matiere> listMatieres= new ArrayList<Matiere>();
    private List<Matiere> listMatieresChoisi= new ArrayList<Matiere>();


    public void init(){
        classet = new Classe();
        listMatieresChoisi = new ArrayList<Matiere>();
    }
    public void ajoutCOef(){
        try {
			dataSource.update(classet);
			dataSource.flush();
			init();
			facesMessages.addToControlFromResourceBundle("infoGenerique", "Classe modifiée avec succés!");
		} catch (HibernateException e) {
			e.printStackTrace();
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de la modification!");
		}
    }
    
    public void consulerClasse(Classe classe1){
        listMatieresChoisi.clear();
        StringBuilder hql = new StringBuilder();
        hql.append("from Classe c");
        hql.append(" inner join fetch c.cycle");
        hql.append(" inner join fetch c.institut i");
        hql.append(" left outer join fetch c.listLnkCoefMatieres l");
        hql.append(" left outer join fetch l.matiere");
        hql.append(" where c.idClasse =:paramId");
        classet = (Classe) dataSource.createQuery(hql.toString())
                .setParameter("paramId", classe1.getIdClasse())
                .uniqueResult();
        for (LnkCoefMatiere eachCoefMatiere:classet.getListLnkCoefMatieres()) {
            listMatieresChoisi.add(eachCoefMatiere.getMatiere());
        }
    }
    
    public void ajoutMatieresClasse(){
    	System.out.println("list mat choisi:"+listMatieresChoisi.size());
    	if(!listMatieresChoisi.isEmpty()) {
    		System.out.println("decocher=");
    		for (LnkCoefMatiere matieresChoisi:classet.getListLnkCoefMatieres() ) {
        			supp(matieresChoisi);
        	}
    	}
    	classet.setListLnkCoefMatieres(new ArrayList<LnkCoefMatiere>());
    	//consulerClasse(classet);
        if(!listMatieresChoisi.isEmpty()) {
            for (Matiere matieresChoisi:listMatieresChoisi ) {
                LnkCoefMatiere coefMatiere = new LnkCoefMatiere();
                coefMatiere.setClasse(classet);
                coefMatiere.setMatiere(matieresChoisi);
                //if(!veriffLnkDejaEnreg(matieresChoisi, classet)){
                	System.out.println("mat bi="+matieresChoisi.getLibelle());
                	System.out.println("list mat classe:"+classet.getListLnkCoefMatieres().size());
                    classet.getListLnkCoefMatieres().add(coefMatiere);
                //}
            }
            dataSource.merge(classet);
			dataSource.flush();
            init();
        }else {
        	facesMessages.addToControlFromResourceBundle("erreurGenerique", "Vous devez au moins choisir une matiére!");
        }
    }
    
    private LnkCoefMatiere cons(LnkCoefMatiere iLnk){
    	StringBuilder hql = new StringBuilder();
        hql.append("from LnkCoefMatiere l");
        hql.append(" inner join fetch l.matiere m");
        hql.append(" inner join fetch l.classe c");
        hql.append(" where l.idLnkCoef =:paramId ");
        LnkCoefMatiere coefMatiere =(LnkCoefMatiere) dataSource.createQuery(hql.toString())
                .setParameter("paramId",iLnk.getIdLnkCoef())
                .uniqueResult();
        return coefMatiere;
    }
    
    private void supp(LnkCoefMatiere iLnk){
    	dataSource.delete(iLnk);
    	dataSource.flush();
    }
    
    public boolean veriffLnkDejaEnreg(Matiere matiere, Classe classe) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from LnkCoefMatiere l");
            hql.append(" inner join fetch l.matiere m");
            hql.append(" inner join fetch l.classe c");
            hql.append(" where c.idClasse =:paramCl and m.idMatiere =:paramIdM ");
            LnkCoefMatiere coefMatiere =(LnkCoefMatiere) dataSource.createQuery(hql.toString())
                    .setParameter("paramCl",classe.getIdClasse())
                    .setParameter("paramIdM",matiere.getIdMatiere())
                    .uniqueResult();
            if(coefMatiere!=null && coefMatiere.getIdLnkCoef()!=null)return  true;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public String versCoef()
	{
		return "/pages/parametrage/paramCoef.xhtml";
	}
    
    @SuppressWarnings("unchecked")
	public List<Classe> allClasse() {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("select distinct c from Classe c");
            hql.append(" inner join fetch c.cycle");
            hql.append(" inner join fetch c.institut i");
            hql.append(" left outer join fetch c.listLnkCoefMatieres l");
            hql.append(" left outer join fetch l.matiere");
            if(utilisateur.getInstitut()!=null)
            	hql.append(" where i.idInstitut =:paramInstit");
            hql.append(" order by c.libelle");
            Query q = dataSource.createQuery(hql.toString());
            if(utilisateur.getInstitut()!=null)
            	q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
            List<Classe> list = q.list();
            return  list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
	public Classe getClasset() {
		return classet;
	}
	public void setClasset(Classe classet) {
		this.classet = classet;
	}
	public List<Classe> getListClasses() {
		return listClasses = allClasse();
	}
	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
	}
	
	@SuppressWarnings("unchecked")
	public List<Matiere> getListMatieres() {
		StringBuilder hql = new StringBuilder();
		hql.append("from Matiere m");
        hql.append(" order by m.libelle");
        listMatieres = dataSource.createQuery(hql.toString()).list();
		return listMatieres;
	}
	public void setListMatieres(List<Matiere> listMatieres) {
		this.listMatieres = listMatieres;
	}
	public List<Matiere> getListMatieresChoisi() {
		return listMatieresChoisi;
	}
	public void setListMatieresChoisi(List<Matiere> listMatieresChoisi) {
		this.listMatieresChoisi = listMatieresChoisi;
	}
    
}
