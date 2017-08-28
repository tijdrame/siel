package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.AnneeAcademique;

@Name("anneeService")
@Scope(ScopeType.PAGE)
public class AnneeService implements Serializable {

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
	
	private AnneeAcademique academique = new AnneeAcademique();
	
	private List<AnneeAcademique> listAcademiques = new ArrayList<AnneeAcademique>();
	
	public AnneeAcademique consuler(AnneeAcademique aac){
		StringBuilder hql = new StringBuilder();
        hql.append("from AnneeAcademique s");
        hql.append(" inner join fetch s.institut i");
        hql.append(" where s.idAnneeAc =:paramId");
        return academique = (AnneeAcademique) dataSource.createQuery(hql.toString())
        		.setParameter("paramId", aac.getIdAnneeAc())
        		.uniqueResult();
    }
    public void ajout(){
        try {
            if(academique.getIdAnneeAc()==null) {
            	academique.setInstitut(utilisateur.getInstitut());
            	academique.setAnneeFin(academique.getAnneeDebut()+1);
            	dataSource.save(academique);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Année Académique ajoutée avec succés!");
            }else {
            	academique.setAnneeFin(academique.getAnneeDebut()+1);
            	dataSource.update(academique);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Année Académique modifiée avec succés!");
            }
            init();
            listAcademiques = allAcademiques();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout de l'année Académique!");
        }
    }
    
    public String versAcademique()
	{
		return "/pages/parametrage/paramAcademique.xhtml";
	}

    @SuppressWarnings("unchecked")
	public List<AnneeAcademique> allAcademiques() {
    	StringBuilder hql = new StringBuilder();
        hql.append("from AnneeAcademique s");
        hql.append(" inner join fetch s.institut i");
        if(utilisateur.getInstitut()!=null)
        	hql.append(" where i.idInstitut =:paramInstit");
        hql.append(" order by s.anneeDebut");
        Query q = dataSource.createQuery(hql.toString());

        if(utilisateur.getInstitut()!=null)
        	q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
		return q.list();
    }

	public void init(){
		academique = new AnneeAcademique();
    }
	public AnneeAcademique getAcademique() {
		return academique;
	}
	public void setAcademique(AnneeAcademique academique) {
		this.academique = academique;
	}
	public List<AnneeAcademique> getListAcademiques() {
		return listAcademiques = allAcademiques();
	}
	public void setListAcademiques(List<AnneeAcademique> listAcademiques) {
		this.listAcademiques = listAcademiques;
	}
	
	
	
}
