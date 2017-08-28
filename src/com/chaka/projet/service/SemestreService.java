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
import com.tidiane.model.Semestre;

@Name("semestreService")
@Scope(ScopeType.PAGE)
public class SemestreService implements Serializable {

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
	
	private Semestre semestre = new Semestre();
	
	private List<Semestre> listSemestres = new ArrayList<Semestre>();
	
	public Semestre consuler(Semestre semes){
		StringBuilder hql = new StringBuilder();
        hql.append("from Semestre s");
        hql.append(" inner join fetch s.institut i");
        hql.append(" where s.idSemestre =:paramId");
        return semestre = (Semestre) dataSource.createQuery(hql.toString())
        		.setParameter("paramId", semes.getIdSemestre())
        		.uniqueResult();
    }
    public void ajout(){
        try {
            if(semestre.getIdSemestre()==null) {
            	semestre.setInstitut(utilisateur.getInstitut());
            	dataSource.save(semestre);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Semestre ajouté avec succés!");
            }else {
            	dataSource.update(semestre);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Semestre modifié avec succés!");
            }
            init();
            listSemestres = allSemestres();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout du Semestre!");
        }
    }
    
    public String versSemestre()
	{
		return "/pages/parametrage/paramSemestre.xhtml";
	}

    @SuppressWarnings("unchecked")
	public List<Semestre> allSemestres() {
    	StringBuilder hql = new StringBuilder();
        hql.append("from Semestre s");
        hql.append(" inner join fetch s.institut i");
        if(utilisateur.getInstitut()!=null)
        	hql.append(" where i.idInstitut =:paramInstit");
        hql.append(" order by s.libelle");
        Query q = dataSource.createQuery(hql.toString());

        if(utilisateur.getInstitut()!=null)
        	q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
		return q.list();
    }

	public void init(){
		semestre = new Semestre();
    }
	public Semestre getSemestre() {
		return semestre;
	}
	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}
	public List<Semestre> getListSemestres() {
		return listSemestres = allSemestres();
	}
	public void setListSemestres(List<Semestre> listSemestres) {
		this.listSemestres = listSemestres;
	}
	
	
}
