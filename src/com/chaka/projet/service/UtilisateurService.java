package com.chaka.projet.service;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.chaka.projet.entity.UtilisateurSecurise;
import com.chaka.projet.service.utils.ServiceCryptage;



@Name("utilisateurService")
@Scope(ScopeType.SESSION)
public class UtilisateurService  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3131849938087110342L;

	@In
	private Session dataSource;
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	@In 
	FacesMessages facesMessages;
	

	private String nomSearchString;
	
	
	private String prenomSearchString;
	
	private Profile profileSearch;
	
	private List<Utilisateur> listeUtilisateurs;
	
	private List<Profile> listeProfilesSearch;
	
	private Utilisateur utilisateurCreationModification;
	
	private boolean enCreation;
	
	private String password1;
	
	private String password2;
	
	private String ancienpassword;
	private List<Profile> lstProfiles = new ArrayList<Profile>();
	
	private Boolean isParent;
	
	private Profile profileParent = new Profile();

	/**
	 * Constructeur
	 */
	public UtilisateurService() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public void find() {
		
		listeUtilisateurs.clear();
		
		if(this.nomSearchString != null && nomSearchString.trim().length() !=0)
			this.nomSearchString = nomSearchString.toLowerCase();
		
		if(this.prenomSearchString != null && prenomSearchString.trim().length() !=0)
			this.prenomSearchString = prenomSearchString.toLowerCase();

		String filtreNom = "";
		String filtrePrenom = "";
		StringBuffer hql = new StringBuffer();
		hql.append("select distinct u  from Utilisateur u");
		hql.append(" where u.idUtilisateur != null");
		if(this.nomSearchString != null && nomSearchString.trim().length() !=0){
			filtreNom = "%" + this.nomSearchString + "%";
			hql.append(" and  lower(u.nom) like :nomSearchStr");
		}
		if(this.prenomSearchString != null && prenomSearchString.trim().length() !=0){
			filtrePrenom = "%" + this.prenomSearchString + "%";
			hql.append(" and lower(u.prenom) like :prenomSearchStr");
		}
		if(profileSearch!=null)hql.append(" and u.profile.idProfile = :profileSearch");
		if(utilisateur.getProfile()!=null && utilisateur.getProfile().getLibelle()!=null &&
				utilisateur.getProfile().getLibelle().equals("ADMIN"))
			hql.append(" and u.institut =:paramInst");
		hql.append(" order by u.nom asc, u.prenom asc");
		Query q = dataSource.createQuery(hql.toString());
		if(this.nomSearchString != null && nomSearchString.trim().length() !=0)
			q.setParameter("nomSearchStr", filtreNom);
		if(this.prenomSearchString != null && prenomSearchString.trim().length() !=0)
			q.setParameter("prenomSearchStr", filtrePrenom);
		if(utilisateur.getProfile()!=null && utilisateur.getProfile().getLibelle()!=null &&
				utilisateur.getProfile().getLibelle().equals("ADMIN"))
			q.setParameter("paramInst", utilisateur.getInstitut());
		if(profileSearch!=null)q.setParameter("profileSearch", profileSearch.getIdProfile());
		listeUtilisateurs =  q.list();
				/*"select distinct u  from Utilisateur u "//38
					+ "where  lower(u.nom) like :nomSearchStr and  lower(u.prenom) like :prenomSearchStr "//118
					+ "and (:profileSearch is null or u.profile = :profileSearch )  " 
					+ "order by u.nom asc, u.prenom asc")
					.setParameter("nomSearchStr", filtreNom)
					.setParameter("prenomSearchStr", filtrePrenom)
					
					.setParameter("profileSearch", profileSearch)
					.list();*/
		
	}
	
	@SuppressWarnings("unchecked")
	public void find2() {
		
		listeUtilisateurs.clear();
		
		if(this.nomSearchString != null && nomSearchString.trim().length() !=0)
			this.nomSearchString = nomSearchString.toLowerCase();
		
		if(this.prenomSearchString != null && prenomSearchString.trim().length() !=0)
			this.prenomSearchString = prenomSearchString.toLowerCase();

		String filtreNom = "";
		String filtrePrenom = "";
		StringBuffer hql = new StringBuffer();
		hql.append("select distinct u  from Utilisateur u");
		hql.append(" where u.idUtilisateur != null");
		if(this.nomSearchString != null && nomSearchString.trim().length() !=0){
			filtreNom = "%" + this.nomSearchString + "%";
			hql.append(" and  lower(u.nom) like :nomSearchStr");
		}
		if(this.prenomSearchString != null && prenomSearchString.trim().length() !=0){
			filtrePrenom = "%" + this.prenomSearchString + "%";
			hql.append(" and lower(u.prenom) like :prenomSearchStr");
		}
		hql.append(" and u.profile.libelle = :profileSearch");
		if(utilisateur.getProfile()!=null && utilisateur.getProfile().getLibelle()!=null &&
				utilisateur.getProfile().getLibelle().equals("ADMIN"))
			hql.append(" and u.institut =:paramInst");
		hql.append(" order by u.nom asc, u.prenom asc");
		Query q = dataSource.createQuery(hql.toString());
		if(this.nomSearchString != null && nomSearchString.trim().length() !=0)
			q.setParameter("nomSearchStr", filtreNom);
		if(this.prenomSearchString != null && prenomSearchString.trim().length() !=0)
			q.setParameter("prenomSearchStr", filtrePrenom);
		if(utilisateur.getProfile()!=null && utilisateur.getProfile().getLibelle()!=null &&
				utilisateur.getProfile().getLibelle().equals("ADMIN"))
			q.setParameter("paramInst", utilisateur.getInstitut());
		q.setParameter("profileSearch", "PARENT");
		listeUtilisateurs =  q.list();
		
	}
	
	
	public String retourRechercheUtilisateurs()
	{
		return "/pages/utilisateur/utilisateurListe.xhtml";
	}
	
	public String versCreationUtilisateurAgence()
	{
		this.enCreation = true;
		this.isParent = false;
		this.utilisateurCreationModification = new Utilisateur();
		this.utilisateurCreationModification.setActif(true);
		
		return "/pages/utilisateur/utilisateurCreationModication.xhtml";
	}
	
	/**
	 * @return "/pages/securite/CreerUtilisateur.xhtml".
	 */
	public String versCreationParent() {
		isParent =true;
		String hql= "from Profile p where p.libelle= :param";
		this.enCreation = true;
		//this.isParent = false;
		this.utilisateurCreationModification = new Utilisateur();
		this.utilisateurCreationModification.setActif(true);
		this.utilisateurCreationModification.setInstitut(utilisateur.getInstitut());
		profileParent = (Profile)dataSource.createQuery(hql)
				.setParameter("param", "PARENT")
				.uniqueResult();
		this.utilisateurCreationModification.setProfile(profileParent);
		return "/pages/utilisateur/utilisateurCreationModication.xhtml";
	}
	
	public String versGestionParents()
	{
		isParent =true;
		this.nomSearchString = "";
		this.prenomSearchString = "";
		this.listeProfilesSearch = Collections.emptyList();
		this.profileSearch = null;
		this.listeUtilisateurs = Collections.emptyList();
		
		find2();
		
		return "/pages/utilisateur/utilisateurListe2.xhtml";
	}
	
	public String versCreationUtilisateurIntervenant()
	{
		this.enCreation = true;
		
		this.utilisateurCreationModification = new Utilisateur();
		this.utilisateurCreationModification.setActif(true);
		
		Profile profileIntervenant = new Profile();
		profileIntervenant.setIdProfile(3L);
		this.utilisateurCreationModification.setProfile(profileIntervenant);
		
		return "/pages/utilisateur/utilisateurIntervenantCreationModication.xhtml";
	}
	
	public String versCreationUtilisateurBeneficiaire()
	{
		this.enCreation = true;
		
		this.utilisateurCreationModification = new Utilisateur();
		this.utilisateurCreationModification.setActif(true);
		
		Profile profileBeneficiaire = new Profile();
		profileBeneficiaire.setIdProfile(4L);
		this.utilisateurCreationModification.setProfile(profileBeneficiaire);
		
		return "/pages/utilisateur/utilisateurBeneficiaireCreationModication.xhtml";
	}
	
	public String consulterUtilisateur(Utilisateur utilisateurAConsulter)
	{
		this.enCreation = false;
		this.isParent = false;
		
		this.utilisateurCreationModification = (Utilisateur) this.dataSource.get(Utilisateur.class,utilisateurAConsulter.getIdUtilisateur());
		
		return "/pages/utilisateur/utilisateurCreationModication.xhtml";	
		
		
	}
	
	public String consulterUtilisateur2(Utilisateur utilisateurAConsulter)
	{
		this.enCreation = false;
		this.isParent = true;
		
		this.utilisateurCreationModification = (Utilisateur) this.dataSource.get(Utilisateur.class,utilisateurAConsulter.getIdUtilisateur());
		
		return "/pages/utilisateur/utilisateurCreationModication.xhtml";	
		
		
	}
	
	
	public String consulterMySelf()
	{
		this.enCreation = false;
		this.isParent = false;
		this.utilisateurCreationModification = (Utilisateur) this.dataSource.get(Utilisateur.class,utilisateur.getIdUtilisateur());
			
		return "/pages/utilisateur/monUtilisateurCreationModication.xhtml";	
	}
	
	
	public String initialisationPassword()
	{
		this.password1 = "";
		this.password2 = "";
		return "";
	}
	
	public void validerPassword()
	{
		String passwordChoisi = password1;
		
		if (!password1.equals(password2))
		{
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "msg.passwordPasIdentiques");
		} else {
			
			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class, utilisateurCreationModification.getIdUtilisateur());
			
			ServiceCryptage sc = new ServiceCryptage();
			try {
				
				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(passwordChoisi, salt);
				
				us.setSalt(salt);
				us.setSecurePassword(securePass);
				
				dataSource.update(us);
				dataSource.flush();
				
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
			
			facesMessages.addToControlFromResourceBundle("infoGenerique", "msg.utilisateur.modification.ok");
		}
		
		
		
		
		
		
		
		
	}
	
	public String creerModifierUtilisateur()
	{
		if(isParent)utilisateur.setProfile(profileParent);
		String passwordChoisi = utilisateurCreationModification.getPassword();
		
		
		if (enCreation && !this.utilisateurCreationModification.getPassword().equals(this.utilisateurCreationModification.getPassword2()))
		{
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "msg.passwordPasIdentiques");
			return "";
		}
		
		// V�rification de l'unicit� du login !!
		Number nb = null;
		if (this.utilisateurCreationModification.getIdUtilisateur() == null)
		{
			String hql = "select count(u) from Utilisateur u where u.login = :login";
			nb = (Number) this.dataSource.createQuery(hql).setParameter("login", this.utilisateurCreationModification.getLogin()).uniqueResult();
		} else {
			String hql = "select count(u) from Utilisateur u where u.login = :login and u.idUtilisateur != :idUtilisateur ";
			nb = (Number) this.dataSource.createQuery(hql).setParameter("login", this.utilisateurCreationModification.getLogin()).setParameter("idUtilisateur", utilisateurCreationModification.getIdUtilisateur()).uniqueResult();
		}
		
		if (nb.intValue() > 0)
		{
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "msg.loginIndisponible");
			return "";
		}
		
		
		this.utilisateurCreationModification.setDateMaj(new Date());
		
		
		if (this.utilisateurCreationModification.getIdUtilisateur() == null)
		{
			if(utilisateur!= null && utilisateur.getProfile()!=null && utilisateur.getProfile().getLibelle() !=null
					&& utilisateur.getProfile().getLibelle().equals("ADMIN"))
				//if(utilisateur.getInstitut()!=null)
				this.utilisateurCreationModification.setInstitut(utilisateur.getInstitut());
			this.utilisateurCreationModification.setDateCreation(new Date());
			this.dataSource.save(this.utilisateurCreationModification);
			System.out.println("user save="+utilisateurCreationModification.getLogin());
			System.out.println("user save="+utilisateurCreationModification.getIdUtilisateur());
		} else {
			System.out.println("user 1="+utilisateur);
			System.out.println("user 2="+utilisateur.getProfile());
			System.out.println("user 3="+utilisateur.getProfile().getLibelle());
			System.out.println("intiti 1="+utilisateur.getInstitut());
			if(utilisateur!= null && utilisateur.getProfile()!=null && utilisateur.getProfile().getLibelle() !=null
					&& utilisateur.getProfile().getLibelle().equals("ADMIN"))
				//if(utilisateur.getInstitut()!=null)
				this.utilisateurCreationModification.setInstitut(utilisateur.getInstitut());
			this.dataSource.update(this.utilisateurCreationModification);
		}
		System.out.println("user flush");
		this.dataSource.flush();
		
		
		if (enCreation)
		{
			// pr�paration du password :
			
			UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class, utilisateurCreationModification.getIdUtilisateur());
			System.out.println("us ="+ us);
			ServiceCryptage sc = new ServiceCryptage();
			try {
				
				byte[] salt = sc.generateSalt();
				byte[] securePass = sc.getEncryptedPassword(passwordChoisi, salt);
				
				us.setSalt(salt);
				us.setSecurePassword(securePass);
				
				dataSource.update(us);
				dataSource.flush();
				
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		if (utilisateur.getIdUtilisateur() != null && utilisateur.getIdUtilisateur().equals(utilisateurCreationModification.getIdUtilisateur()))
		{
			utilisateur = (Utilisateur)dataSource.load(Utilisateur.class, utilisateur.getIdUtilisateur());
		}
		
		if (enCreation)
		{
			facesMessages.addToControlFromResourceBundle("infoGenerique", "msg.utilisateur.creation.ok");
			utilisateurCreationModification = new Utilisateur();
		} else {
			utilisateurCreationModification = new Utilisateur();
			facesMessages.addToControlFromResourceBundle("infoGenerique", "msg.utilisateur.modification.ok");
		}
		
		this.enCreation = false;
		if(isParent){
			isParent = false;
			return "/pages/parametrage/paramEtud.xhtml";
		}
		utilisateurCreationModification = new Utilisateur();
		return "";
	}
	
	public String versGestionUtilisateurs()
	{
		this.nomSearchString = "";
		this.prenomSearchString = "";
		this.listeProfilesSearch = Collections.emptyList();
		this.profileSearch = null;
		this.listeUtilisateurs = Collections.emptyList();
		
		find();
		
		return "/pages/utilisateur/utilisateurListe.xhtml";
	}
	
	public String versModifPassword()
	{	
		return "/pages/utilisateur/modifPassword.xhtml";
	}
	
	public String modifPasswd(){
		System.out.println("dans modif passwd");
		if(verifPasswd2()){
			if(verifPasswd()){
				UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class, utilisateur.getIdUtilisateur());
				System.out.println("us ="+ us.getLogin());
				ServiceCryptage sc = new ServiceCryptage();
				try {
					
					byte[] salt = sc.generateSalt();
					byte[] securePass = sc.getEncryptedPassword(password1, salt);
					
					us.setSalt(salt);
					us.setSecurePassword(securePass);
					
					dataSource.update(us);
					dataSource.flush();
					
					
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					e.printStackTrace();
				}
			}else{
				facesMessages.addToControlFromResourceBundle("erreurGenerique", "Les mots de passe ne sont pas identiques");
				return"";
			}
		}else{
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "L'ancien mot de passe n'est pas bon");
			return"";
		}
		facesMessages.addToControlFromResourceBundle("infoGenerique", "Mot de passe changé avec succés!");
		return"";
	}
	
	private boolean verifPasswd(){
        boolean flag = false;
        if(password1.equals(password2))flag=true;
        System.out.println("flag ="+flag);
        return flag;
    }
	//verifier si l'ancien mot de passe est le bon
    private boolean verifPasswd2(){
    	ServiceCryptage sc = new ServiceCryptage();
    	UtilisateurSecurise us = (UtilisateurSecurise) dataSource.get(UtilisateurSecurise.class, utilisateur.getIdUtilisateur());
        boolean flag = false;
        try {
            
            flag = sc.authenticate(ancienpassword, us.getSecurePassword(),
            		us.getSalt());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        System.out.println("flag 2="+flag);
        return flag;
    }
	
	public void activer(Utilisateur utilisateurAActiver)
	{
		utilisateurAActiver.setActif(true);
		utilisateurAActiver.setDateMaj(new Date());
		this.dataSource.update(utilisateurAActiver);
		this.dataSource.flush();
	}
	
	public void desactiver(Utilisateur utilisateurAActiver)
	{
		utilisateurAActiver.setActif(false);
		utilisateurAActiver.setDateMaj(new Date());
		this.dataSource.update(utilisateurAActiver);
		this.dataSource.flush();
	}


	public String getNomSearchString() {
		return nomSearchString;
	}


	public void setNomSearchString(String nomSearchString) {
		this.nomSearchString = nomSearchString;
	}


	public String getPrenomSearchString() {
		return prenomSearchString;
	}


	public void setPrenomSearchString(String prenomSearchString) {
		this.prenomSearchString = prenomSearchString;
	}


	public Profile getProfileSearch() {
		return profileSearch;
	}


	public void setProfileSearch(Profile profileSearch) {
		this.profileSearch = profileSearch;
	}

	public List<Utilisateur> getListeUtilisateurs() {
		return listeUtilisateurs;
	}

	public void setListeUtilisateurs(List<Utilisateur> listeUtilisateurs) {
		this.listeUtilisateurs = listeUtilisateurs;
	}

	public Utilisateur getUtilisateurCreationModification() {
		return utilisateurCreationModification;
	}

	public void setUtilisateurCreationModification(
			Utilisateur utilisateurCreationModification) {
		this.utilisateurCreationModification = utilisateurCreationModification;
	}

	public boolean isEnCreation() {
		return enCreation;
	}

	public void setEnCreation(boolean enCreation) {
		this.enCreation = enCreation;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	/**
	 * @return the listeProfilesSearch
	 */
	public List<Profile> getListeProfilesSearch() {
		return listeProfilesSearch;
	}

	/**
	 * @param listeProfilesSearch the listeProfilesSearch to set
	 */
	public void setListeProfilesSearch(List<Profile> listeProfilesSearch) {
		this.listeProfilesSearch = listeProfilesSearch;
	}

	public String getAncienpassword() {
		return ancienpassword;
	}

	public void setAncienpassword(String ancienpassword) {
		this.ancienpassword = ancienpassword;
	}

	@SuppressWarnings("unchecked")
	public List<Profile> getLstProfiles() {
		StringBuilder hql = new StringBuilder();
		hql.append("from Profile p");
		hql.append(" order by p.libelle");
		return lstProfiles= dataSource.createQuery(hql.toString()).list();
	}

	public void setLstProfiles(List<Profile> lstProfiles) {
		this.lstProfiles = lstProfiles;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Profile getProfileParent() {
		return profileParent;
	}

	public void setProfileParent(Profile profileParent) {
		this.profileParent = profileParent;
	}

	

}
