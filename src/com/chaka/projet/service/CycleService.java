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
import com.tidiane.model.Cycle;

@Name("cycleService")
@Scope(ScopeType.PAGE)
public class CycleService implements Serializable {

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
	
	private Cycle cycle = new Cycle();
	
	private List<Cycle> listCycles = new ArrayList<Cycle>();
	
	public Cycle consuler(Cycle cycl){
        return cycle = (Cycle) dataSource.get(Cycle.class, cycl.getIdCycle());
    }
    public void ajout(){
        try {
            if(cycle.getIdCycle()==null) {
            	dataSource.save(cycle);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Cycle ajouté avec succés!");
            }else {
            	dataSource.update(cycle);
            	dataSource.flush();
            	facesMessages.addToControlFromResourceBundle("infoGenerique", "Cycle modifié avec succés!");
            }
            init();
            listCycles = allCycles();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout du cycle!");
        }
    }
    
    public String versCycle()
	{
		return "/pages/parametrage/paramCycle.xhtml";
	}

    @SuppressWarnings("unchecked")
	public List<Cycle> allCycles() {
    	StringBuilder hql = new StringBuilder();
        hql.append("from Cycle c order by c.libelle");
        return  dataSource.createQuery(hql.toString()).list();
    }

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}
	
	public void init(){
		cycle = new Cycle();
    }
	public List<Cycle> getListCycles() {
		return listCycles = allCycles();
	}
	public void setListCycles(List<Cycle> listCycles) {
		this.listCycles = listCycles;
	}
	
}
