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

@Name("matiereService")
@Scope(ScopeType.PAGE)
public class MatiereService implements Serializable {
	
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
	
	private Matiere matiere = new Matiere();
	
	@SuppressWarnings("unused")
	private List<Matiere> listMatieres = new ArrayList<Matiere>();
	
	public void init(){
		matiere = new Matiere();
    }
	
	public void consuler(Matiere matiere1){
    	StringBuilder hql = new StringBuilder();
        hql.append("from Matiere m");
        hql.append(" inner join fetch m.institut i");
        hql.append(" where m.idMatiere =:paramId");
        matiere = (Matiere) dataSource.createQuery(hql.toString())
                .setParameter("paramId", matiere1.getIdMatiere())
                .uniqueResult();
    }
	
	public void ajout(){
        try {
            if(matiere.getIdMatiere()==null) {
            	matiere.setInstitut(utilisateur.getInstitut());
                dataSource.save(matiere);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", "Matiére ajoutée avec succés!");
            }else{
            	dataSource.update(matiere);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", "Matiére modifiée avec succés!");
            }
            init();
            listMatieres = allMatieres();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout de la matiére!");
        }
    }
    
    @SuppressWarnings("unchecked")
	private List<Matiere> allMatieres() {
    	StringBuilder hql = new StringBuilder();
        hql.append("from Matiere m");
        hql.append(" inner join fetch m.institut i");
        
        if(utilisateur.getInstitut()!=null)
        	hql.append(" where i.idInstitut =:paramInstit");
        hql.append(" order by m.libelle");
        Query q = dataSource.createQuery(hql.toString());

        if(utilisateur.getInstitut()!=null)
        	q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
		return q.list();
	}
    
    @SuppressWarnings("unchecked")
	public List<Matiere> matieresByClasse(Classe classe) {
        try {
            List<Matiere> listMatieres = new ArrayList<Matiere>();
            
            StringBuilder hql = new StringBuilder();
            hql.append("from LnkCoefMatiere l");
            hql.append(" inner join fetch l.classe cl");
            hql.append(" inner join fetch l.matiere m");
            hql.append(" where cl.idClasse =:paramId");
            hql.append(" order by m.libelle");
            List<LnkCoefMatiere> list = dataSource.createQuery(hql.toString())
                    .setParameter("paramId",classe.getIdClasse())
                    .list();
            for (LnkCoefMatiere eachCoefMatiere : list ) {
                Matiere matiere = consulterMatiereClasse(eachCoefMatiere.getMatiere());
                listMatieres.add(matiere);
            }
            return  listMatieres;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Matiere consulterMatiereClasse(Matiere matiere) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Matiere m");
            hql.append(" where m.idMatiere =:paramId");
            Matiere matiere1 = (Matiere) dataSource.createQuery(hql.toString())
                    .setParameter("paramId", matiere.getIdMatiere())
                    .uniqueResult();
            return matiere1;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
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

	public String versMatiere()
	{
		return "/pages/parametrage/paramMatiere.xhtml";
	}
    

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public List<Matiere> getListMatieres() {
		return listMatieres = allMatieres();
	}

	public void setListMatieres(List<Matiere> listMatieres) {
		this.listMatieres = listMatieres;
	}
	
	

}
