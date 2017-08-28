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
import com.tidiane.model.Classe;

@Name("classeService")
@Scope(ScopeType.PAGE)
public class ClasseService implements Serializable {

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
	
	private Classe classe = new Classe();
	
	private List<Classe> listClasses = new ArrayList<Classe>();
	
	public void init(){
        classe = new Classe();
    }
    public void consulerClasse(Classe classe1){
    	StringBuilder hql = new StringBuilder();
        hql.append("from Classe c");
        hql.append(" inner join fetch c.cycle");
        hql.append(" inner join fetch c.institut i");
        hql.append(" left outer join fetch c.listLnkCoefMatieres l");
        hql.append(" left outer join fetch l.matiere");
        hql.append(" where c.idClasse =:paramId");
        classe = (Classe) dataSource.createQuery(hql.toString())
                .setParameter("paramId", classe1.getIdClasse())
                .uniqueResult();
    }
    
    public void ajout(){
        try {
            if(classe.getIdClasse()==null) {
                classe.setInstitut(utilisateur.getInstitut());
                dataSource.save(classe);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", "Classe ajoutée avec succés!");
            }else{
            	dataSource.update(classe);
                dataSource.flush();
                facesMessages.addToControlFromResourceBundle("infoGenerique", "Classe modifiée avec succés!");
            }
            init();
            listClasses = allClasse();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout de la classe!");
        }
    }
    
    public String versCycle()
	{
		return "/pages/parametrage/paramClasse.xhtml";
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

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Classe> getListClasses() {
		return listClasses = allClasse();
	}

	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
	}
	

}
