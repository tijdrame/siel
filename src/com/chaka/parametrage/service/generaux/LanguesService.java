package com.chaka.parametrage.service.generaux;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import com.chaka.parametrage.entity.generaux.Langues;
import com.chaka.projet.entity.Utilisateur;

@Scope(ScopeType.SESSION)
@Name("langueService")
public class LanguesService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2774659771071458273L;
	/**
	 * Session hibernate permettant le dialogue avec la base de données.
	 */
	@In
	private Session dataSource;
	

	/**
	 * Instance courante de l'utilisateur manipulé par cette classe de service.
	 */
	@In(required = false)
	@Out(required = false)
	Utilisateur utilisateur;
	
	@SuppressWarnings("unused")
	private List<Langues> listLangues;
	
	private Langues langueEnCreation;
	
	private Langues langueEnModification;
	
	/** 
	 * enregistre les traces dans un fichier log.
	 */
	@Logger
	private Log log;

	/**
	 * Constructeur
	 */
	public LanguesService() {
		super();
	}
	/**
	 * 
	 * @return
	 */
	public boolean supprimable(Langues langue)
	{
		String hql = "select count(i) from Siege i where i.langue = :langue ";
		Number nb = (Number)this.dataSource.createQuery(hql).setParameter("langue", langue).uniqueResult();
		
		if (nb.intValue() > 0)
			return false;
		
		return true;
	}
	public String versGestionLangue()
	{
		rafraichirListeLangue();
		
		initialiserCreation();
		
		return "/pages/parametrage/generaux/parametrageLangues.xhtml";
	}
	
	
	@SuppressWarnings("unchecked")
	private void rafraichirListeLangue()
	{
		String hql = "select e from Langues e order by e.libelle";
		
		listLangues = this.dataSource.createQuery(hql)
		.list();
	}
	
	
	private void initialiserCreation()
	{
		langueEnCreation = new Langues();
	}
	
	public void supprimerLangue(Langues langueASupprimer)
	{
		try{
		this.dataSource.delete(langueASupprimer);
		this.dataSource.flush();
		
		rafraichirListeLangue();
		this.log.info("Suppression de la langue #0 par #1  #2", langueASupprimer.getLibelle(),
				   this.utilisateur.getNom() +" " + this.utilisateur.getPrenom(),new Date());
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "langue.suppression.ok");
		}catch(Exception t){
			this.log.info("Utilisateur #0 a essayé de supprimer la langue #1 #2",this.utilisateur.getNom() +" " + this.utilisateur.getPrenom()
					,langueASupprimer.getLibelle() ,new Date());
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "langue.suppression.ko");
			
		}
		
	}
	
	public void consulterLangue(Langues langueAModifier)
	{
		this.langueEnModification = (Langues)this.dataSource.load(Langues.class, langueAModifier.getIdLangue());
	}
	
	public void modifierLangue(Langues langueAModifier)
	{
		Date now = new Date();
		try{
		langueAModifier.setDateMaj(now);
		
		this.dataSource.update(langueAModifier);
		this.dataSource.flush();
		Utilisateur user=new Utilisateur();
		user.setIdUtilisateur(utilisateur.getIdUtilisateur());
		langueEnModification.setUtilisateur(user);
		rafraichirListeLangue();
		this.log.info("Modification de la langue #0 par #1  #2", langueAModifier.getLibelle(),
				   this.utilisateur.getNom() +" " + this.utilisateur.getPrenom(),new Date());
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "langue.modification.ok");
		}catch(Exception t){
			this.log.info("Utilisateur #0 a essayé de modifier la langue #1 #2",this.utilisateur.getNom() +" " + this.utilisateur.getPrenom()
					,langueAModifier.getLibelle() ,new Date());
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "langue.modification.ko");
			
		}
		
	}
	
	public void ajouterLangue()
	{
		Date now = new Date();
		try{
		langueEnCreation.setDateCreation(now);
		langueEnCreation.setDateMaj(now);
		Utilisateur user=new Utilisateur();
		user.setIdUtilisateur(utilisateur.getIdUtilisateur());
		langueEnCreation.setUtilisateur(user);
		this.dataSource.save(langueEnCreation);
		int code=langueEnCreation.getIdLangue().intValue();
		langueEnCreation.setCodeLangue(9+ code);
		dataSource.update(langueEnCreation);
		this.dataSource.flush();
		rafraichirListeLangue();
		
		initialiserCreation();
		this.log.info("Création de la langue #0 par #1  #2", this.langueEnCreation.getLibelle(),
				   this.utilisateur.getNom() +" " + this.utilisateur.getPrenom(),new Date());
		FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "langue.creation.ok");
		} 
		catch(Exception t){
			this.log.info("Utilisateur #0 a essayé de créer la langue #1 #2",this.utilisateur.getNom() +" " + this.utilisateur.getPrenom()
					,this.langueEnCreation.getLibelle() ,new Date());
			FacesMessages.instance().addToControlFromResourceBundle("infoGenerique", "langue.creation.ko");
			
		}
	}
	/**
	 * @return the listLangue
	 */
	@SuppressWarnings("unchecked")
	public List<Langues> getListLangues() {
			String hql = "select e from Langues e order by e.libelle";
		
		return this.dataSource.createQuery(hql)
		.list();
	}


	/**
	 * @param listLangue the listLangue to set
	 */
	public void setListLangues(List<Langues> listLangues) {
		this.listLangues = listLangues;
	}


	/**
	 * @return the langueEnCreation
	 */
	public Langues getLangueEnCreation() {
		return langueEnCreation;
	}


	/**
	 * @param langueEnCreation the langueEnCreation to set
	 */
	public void setLangueEnCreation(Langues langueEnCreation) {
		this.langueEnCreation = langueEnCreation;
	}


	/**
	 * @return the langueEnModification
	 */
	public Langues getLangueEnModification() {
		return langueEnModification;
	}


	/**
	 * @param langueEnModification the langueEnModification to set
	 */
	public void setLangueEnModification(Langues langueEnModification) {
		this.langueEnModification = langueEnModification;
	}

	
}
