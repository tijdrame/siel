package com.chaka.projet.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.common.utils.ChakaUtils;
import com.chaka.commons.service.FileUploadService;
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.Etudiant;

@Name("etudiantService")
@Scope(ScopeType.SESSION)
public class EtudiantService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@In
	private Session dataSource;
	
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	/** service chargement fichier. */
	@In(required = false)
	@Out(required = false)
	FileUploadService fileUploadService;
	
	@In 
	FacesMessages facesMessages;
	
	private Etudiant etudiant = new Etudiant();
	
	private List<Etudiant> listEtudiants = new ArrayList<Etudiant>();
	
	private String filtreNum, filtreNom, filtrePrenom;
	
	
	private String filePath;
	
	private String filtreParent;
	
	private byte[] contenu;
	
	public void init(){
		filtreNum= "";
		filtreNom="";
		filtrePrenom="";
        etudiant = new Etudiant();
        filtreParent="";
    }
    public String consuler(Etudiant et){
    	try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Etudiant e");
            hql.append(" inner join fetch e.institut i");
            hql.append(" inner join fetch e.parent p");
            hql.append(" where e.idEtudiant =:paramId and i.idInstitut =:paramInstit");
            etudiant = (Etudiant) dataSource.createQuery(hql.toString())
                    .setParameter("paramId", et.getIdEtudiant())
                    .setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut())
                    .uniqueResult();
            filtreParent = etudiant.getParent().getLogin();
            return "/pages/parametrage/saisieEleve.xhtml";
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void ajout(){
        try {
            if(etudiant.getIdEtudiant()==null) {
                etudiant.setInstitut(utilisateur.getInstitut());
                String chaine = ChakaUtils.completeStr(etudiant.getDateNaissance().hashCode()+
                		new SecureRandom().hashCode()+"", '0', 3, true);
                chaine = chaine.replaceAll("-", "");
                String initiale = etudiant.getPrenom().substring(0,1)+etudiant.getNom().substring(0,1);
                etudiant.setNumEleve(initiale+" - "+chaine);

                dataSource.save(etudiant);
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Etudiant ajouté avec succés avec le numéro: "+etudiant.getNumEleve());
            }else {
            	if(etudiant.getNumEleve()==null){
                    String chaine = ChakaUtils.completeStr(etudiant.getDateNaissance().hashCode()+
                    		new SecureRandom().hashCode()+"", '0', 3, true);
                    chaine = chaine.replaceAll("-", "");
                    String initiale = etudiant.getPrenom().substring(0,1)+etudiant.getNom().substring(0,1);
                    etudiant.setNumEleve(initiale+" - "+chaine);
                }
                dataSource.update(etudiant);
                facesMessages.addToControlFromResourceBundle("infoGenerique", 
                		"Etudiant modifié avec succés avec le numéro: "+etudiant.getNumEleve());
            }
            init();
            //listEtudiants = eleveBusiness.allEtudiant();
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'ajout de l'Etudiant!");
        }
    }
    
    public void findParent(ValueChangeEvent e){
    	if (e.getNewValue() != null) {
	    	try {
				StringBuilder hql = new StringBuilder();
				hql.append("from Utilisateur u where u.login=:param and u.profile.libelle=:param2");
				Utilisateur user = (Utilisateur)dataSource.createQuery(hql.toString())
						.setParameter("param", e.getNewValue().toString())
						.setParameter("param2","PARENT")
						.uniqueResult();
				if(user!=null && user.getIdUtilisateur()!=null)
				etudiant.setParent(user);
				else facesMessages.addToControlFromResourceBundle("erreurGenerique", "Parent non trouvé!");
					
			} catch (HibernateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    }
    
    /**
	 * affecte l'image au piece courant.
	 * 
	 * @throws IOException
	 */
	public void selectCurrentImage() throws IOException {
		etudiant.setPhoto(fileUploadService.getContenu());
		/*fileUploadService.getContenu();
		fileUploadService.getFile();
		String ext = fileUploadService.getExtension();

		BufferedInputStream is = null;
		BufferedOutputStream os = null;
		String chemin = "/home/photos/";
		try {
			if (fileUploadService.getContenu() != null
					&& assure.getDemande() != null && assure.getDemande().getNumeroDemande() != null) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		        String name = fmt.format(new Date());
		        		
				contenu = fileUploadService.getContenu();
				is = new BufferedInputStream(new ByteArrayInputStream(
						fileUploadService.getContenu()));
				etudiant.setPhoto(name
						+ fileUploadService.getExtension());
				
				//FacesContext context = FacesContext.getCurrentInstance();
				//ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
	            
	            //String chemin = servletContext.getRealPath("photos/");
	            ChakaUtils.println("ds servlet"+chemin);
	            File file = new File(chemin  + name+ext);
				os = new BufferedOutputStream(new FileOutputStream(file));

				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			is = null;
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}*/
	}

    
    /*public List<Etudiant> getEtudiantByClasse(Classe classe) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Etudiant e");
            hql.append(" inner join fetch e.institut i");
            hql.append(" inner join fetch e.parent p");
            hql.append(" where i.idInstitut =:paramInstit");
            hql.append(" order by e.nom, e.prenom");
            List<Etudiant> list = session.createQuery(hql.toString())
                    .setParameter("paramInstit", loginBean.getMonUser().getInstitut().getIdInstitut())
                    .list();
            session.close();
            return  list;
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }*/
    
    public Etudiant getEtudiantByNum(String numEtudiant) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Etudiant e");
            hql.append(" inner join fetch e.institut i");
            hql.append(" inner join fetch e.parent p");
            hql.append(" where e.numEleve =:paramNum and i.idInstitut =:paramInstit");
            Etudiant etudiant1 = (Etudiant) dataSource.createQuery(hql.toString())
                    .setParameter("paramNum", numEtudiant)
                    .setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut())
                    .uniqueResult();
            return etudiant1;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
	public List<Etudiant> getEtudiantByCriteres(String numEtudiant, String nom, String prenom) {
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("from Etudiant et");
            hql.append(" inner join fetch et.institut i");
            hql.append(" inner join fetch et.parent p");
            hql.append(" where i.idInstitut =:paramInstit");
            if(numEtudiant != null && numEtudiant.trim().length() !=0 )hql.append(" and et.numEleve =:paramNum");
            if(nom != null && nom.trim().length() !=0 )hql.append(" and lower(et.nom) =:paramNom");
            if(prenom != null && prenom.trim().length() !=0 )hql.append(" and lower(et.prenom) =:paramPrenom");
            hql.append(" order by et.nom, et.prenom");
            Query q = dataSource.createQuery(hql.toString());
            if(numEtudiant != null && numEtudiant.trim().length() !=0 )q.setParameter("paramNum",numEtudiant);
            if(nom != null && nom.trim().length() !=0 )q.setParameter("paramNom",nom.toLowerCase());
            if(prenom != null && prenom.trim().length() !=0 )q.setParameter("paramPrenom",prenom.toLowerCase());
            q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
            listEtudiants = q.list();
            return listEtudiants;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String versNewEl()
	{
    	init();
		return "/pages/parametrage/saisieEleve.xhtml";
	}
    
    public String versEtud()
	{
    	init();
    	listEtudiants = new ArrayList<Etudiant>();
		return "/pages/parametrage/paramEtud.xhtml";
	}

    public void findEtudiant(){
        listEtudiants = getEtudiantByCriteres(filtreNum, filtreNom, filtrePrenom);
    }


    public void paint(OutputStream out, Object data) throws IOException {
		if (etudiant != null && etudiant.getPhoto()!=null)
			out.write(etudiant.getPhoto());
	}
    
	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public List<Etudiant> getListEtudiants() {
		return listEtudiants;
	}

	public void setListEtudiants(List<Etudiant> listEtudiants) {
		this.listEtudiants = listEtudiants;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFiltreNum() {
		return filtreNum;
	}
	public void setFiltreNum(String filtreNum) {
		this.filtreNum = filtreNum;
	}
	public String getFiltreNom() {
		return filtreNom;
	}
	public void setFiltreNom(String filtreNom) {
		this.filtreNom = filtreNom;
	}
	public String getFiltrePrenom() {
		return filtrePrenom;
	}
	public void setFiltrePrenom(String filtrePrenom) {
		this.filtrePrenom = filtrePrenom;
	}
	public byte[] getContenu() {
		return contenu;
	}
	public void setContenu(byte[] contenu) {
		this.contenu = contenu;
	}
	public String getFiltreParent() {
		return filtreParent;
	}
	public void setFiltreParent(String filtreParent) {
		this.filtreParent = filtreParent;
	}
	
}
