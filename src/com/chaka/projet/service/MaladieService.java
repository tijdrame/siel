package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.Maladie;


@Name("maladieService")
@Scope(ScopeType.PAGE)
public class MaladieService implements Serializable {

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
	
	private Maladie maladie = new Maladie();
	
	private List<Maladie> listMaladies = new ArrayList<Maladie>();
	
	public Maladie consuler(Maladie mal){
        return maladie = (Maladie) dataSource.get(Maladie.class, mal.getIdMaladie());
    }
    public void ajout(){
        try {
            if(maladie.getIdMaladie()==null) {
            	dataSource.save(maladie);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Maladie ajoutée avec succés!");
            }else {
            	dataSource.update(maladie);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Maladie modifiée avec succés!");
            }
            init();
            listMaladies = allMaladies();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout de la maladie!");
        }
    }
    
    public String versMaladie()
	{
		return "/pages/parametrage/paramMaladie.xhtml";
	}

    @SuppressWarnings("unchecked")
	public List<Maladie> allMaladies() {
    	StringBuilder hql = new StringBuilder();
        hql.append("from Maladie c order by c.libelle");
        return  dataSource.createQuery(hql.toString()).list();
    }
    
    public void init(){
		maladie = new Maladie();
    }
	public Maladie getMaladie() {
		return maladie;
	}
	public void setMaladie(Maladie maladie) {
		this.maladie = maladie;
	}
	public List<Maladie> getListMaladies() {
		return listMaladies = allMaladies();
	}
	public void setListMaladies(List<Maladie> listMaladies) {
		this.listMaladies = listMaladies;
	}

	
	
}
