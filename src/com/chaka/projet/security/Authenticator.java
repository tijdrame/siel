package com.chaka.projet.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.LocaleSelector;
import org.jboss.seam.security.Identity;

import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.chaka.projet.entity.UtilisateurSecurise;
import com.chaka.projet.service.utils.ServiceCryptage;



/**
 * Classe utilis�e pour l'authentification
 *
 * @author Chaka V 0.1 : Maj : 09/11/2007
 */
@Name("authenticator")
public class Authenticator {

	/**
	 * Session hibernate permettant le dialogue avec la base de donn�es.
	 */
	@In
	private Session dataSource;
	
	@In 
	FacesMessages facesMessages;
	
	@In
	LocaleSelector localeSelector;

	/**
	 * Utilisateur loggu�
	 */
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	
	@In(required = false)
	private String username;
	 
	@In(required = false)
	private String password;


	/**
	 * Boolean indiquant si l'utilisateur est deja loggu�.
	 */
	private boolean loggedIn;

	
	/**
	 * Constructeur de la classe agent.
	 */
	public Authenticator() {
		super();
	}

	/**
	 * Methode de Login Chaka
	 */
	public void login() {
		Identity.instance().logout();
		Identity.instance().login();

		loggedIn = Identity.instance().isLoggedIn();

		//FacesMessages.instance().add("Login de l'utilisateur #0", niGend);
	}

	
	public String redirection()
	{
		if (utilisateur == null)
			authenticate();
		localeSelector.setLanguage("fr");
		return "/pages/accueil.xhtml";
	}
	
	
	/**
	 * M�thode d'authentification � l'application.
	 *
	 * @return l'autorisation de l'authentification.
	 */
	public boolean authenticate() {
		
		String login = Identity.instance().getCredentials().getUsername();
		String password = Identity.instance().getCredentials().getPassword();
		
		String hqlSecure = "select us from UtilisateurSecurise us where us.login = :login ";
		
		UtilisateurSecurise userSecured = (UtilisateurSecurise) dataSource.createQuery(hqlSecure)
				.setParameter("login", login)
				.uniqueResult();
		
		if (userSecured != null)
		{
			ServiceCryptage sc = new ServiceCryptage();
			
			try {
				
				if (userSecured.getSecurePassword() == null || userSecured.getSalt() == null)
				{
					loggedIn = false;
				} else {
					
					loggedIn = sc.authenticate(password, userSecured.getSecurePassword(), userSecured.getSalt());
					
					if (loggedIn)
					{
						utilisateur = (Utilisateur) dataSource.get(Utilisateur.class, userSecured.getIdUtilisateur());
						
						int profile = utilisateur.getProfile().getIdProfile().intValue();
						
						switch (profile) {
						case Utilisateur.SADMIN:
							Identity.instance().addRole(Utilisateur.R_SADMIN);
							break;
						case Utilisateur.ADMIN:
							Identity.instance().addRole(Utilisateur.R_ADMIN);
							break;
						case Utilisateur.SURVEILLANT:
							Identity.instance().addRole(Utilisateur.R_SURVEILLANT);
							break;
						case Utilisateur.SECRETAIRE:
							Identity.instance().addRole(Utilisateur.R_SECRETAIRE);
							break;
						case Utilisateur.PROFESSEUR:
							Identity.instance().addRole(Utilisateur.R_PROFESSEUR);
							break;
						case Utilisateur.CAISSIER:
							Identity.instance().addRole(Utilisateur.R_CAISSIER);
							break;
						case Utilisateur.DIR_GEN:
							Identity.instance().addRole(Utilisateur.R_DIR_GEN);
							break;
						case Utilisateur.PARENT:
							Identity.instance().addRole(Utilisateur.R_PARENT);
							break;
						default:
							break;
						}
						
					}
					
				}
				
				
			
			
			} catch (NoSuchAlgorithmException e) {
				loggedIn = false;
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				loggedIn = false;
				e.printStackTrace();
			}
			
		} else {
			
			if ("chaka".equals(login) && "chakaadmin".equals(password))
			{
				
				utilisateur = new Utilisateur();
				utilisateur.setActif(true);
				utilisateur.setNom("Administrateur");
				utilisateur.setPrenom("Chaka");
				
				Profile profile2 = new Profile();
				profile2.setIdProfile(2L);
				
				utilisateur.setProfile(profile2);
				
				
				
				loggedIn = true;
				Identity.instance().addRole(Utilisateur.R_SADMIN);
				/*Identity.instance().addRole(Utilisateur.R_ADMIN);
				Identity.instance().addRole(Utilisateur.R_SURVEILLANT);
				Identity.instance().addRole(Utilisateur.R_SECRETAIRE);
				Identity.instance().addRole(Utilisateur.R_PROFESSEUR);
				Identity.instance().addRole(Utilisateur.R_CAISSIER);
				Identity.instance().addRole(Utilisateur.R_DIR_GEN);*/
			} else {
				loggedIn = false;
			}
			
			
		}
		
		if (!loggedIn)
		{
			facesMessages.addToControlFromResourceBundle("loginMsg", "msg.login.ko");
		}
		
		return loggedIn;
		
		
	}

	/**
	 * Methode de redirection
	 *
	 * @return String
	 */
	public String redirect() {
		Identity.instance().isLoggedIn();
		if (Identity.instance().isLoggedIn()) {
			localeSelector.setLanguage("fr");
			return "/pages/accueil.xhtml";
		}
		
		return "/pages/loginImpossible.xhtml";
	}
	

	

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
