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
import com.tidiane.model.Institut;

@Name("institutService")
@Scope(ScopeType.SESSION)
public class InstitutService implements Serializable{

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
	
	private Institut institut = new Institut();

    private List<Institut> listInstituts = new ArrayList<Institut>();

    public void init(){
        institut = new Institut();
    }
    
    public void supInstitut(Institut institut1){
    	try {
			institut = consulerInstitut(institut1);
			institut.setDeleted(true);
			dataSource.update(institut);
			dataSource.flush();
			facesMessages.addToControlFromResourceBundle("infoGenerique", "Institut supprimé avec succés!");
		} catch (Exception e) {
			// TODO: handle exception
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de la suppression!");
		}
    }
    
    public String versInstit()
	{
		return "/pages/parametrage/paramInstitut.xhtml";
	}
    
    public Institut consulerInstitut(Institut institut1){
    	StringBuilder hql = new StringBuilder();
        hql.append("from Institut i");
        hql.append(" where i.idInstitut =:paramId");
        return institut = (Institut) dataSource.createQuery(hql.toString())
                .setParameter("paramId", institut1.getIdInstitut())
                .uniqueResult();
    }
    
    public void ajout(){
        try {
            if(institut.getIdInstitut()==null) {
                dataSource.save(institut);
                facesMessages.addToControlFromResourceBundle("infoGenerique", "Institut enregistré avec succés!");
            }else{
                dataSource.update(institut);
                facesMessages.addToControlFromResourceBundle("infoGenerique", "Institut modifié avec succés!");
            }
            init();
            listInstituts = allInstitut();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout de l'institut!");
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<Institut> allInstitut() {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Institut i");
            hql.append(" where i.deleted=false");
            if(utilisateur.getInstitut()!=null)
            	hql.append(" and i.idInstitut =:paramInstit");
            hql.append(" order by i.nomInstitut");
            Query q = dataSource.createQuery(hql.toString());
            if(utilisateur.getInstitut()!=null)
            	q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
            List<Institut> list = q.list();
            return  list;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }

	public Institut getInstitut() {
		return institut;
	}

	public void setInstitut(Institut institut) {
		this.institut = institut;
	}

	public List<Institut> getListInstituts() {
		return listInstituts = allInstitut();
	}

	public void setListInstituts(List<Institut> listInstituts) {
		this.listInstituts = listInstituts;
	}
    
    

}
