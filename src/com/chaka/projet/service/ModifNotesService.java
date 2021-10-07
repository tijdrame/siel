package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.common.utils.ChakaUtils;
import com.chaka.constantes.Constantes;
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.AnneeAcademique;
import com.tidiane.model.Classe;
import com.tidiane.model.Cycle;
import com.tidiane.model.Inscription;
import com.tidiane.model.Matiere;
import com.tidiane.model.Notes;
import com.tidiane.model.Semestre;
import com.tidiane.model.TypeNote;

@Name("modifNotesService")
@Scope(ScopeType.SESSION)
public class ModifNotesService implements Serializable {

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
	
	NotesService notesService = (NotesService)Component.getInstance(NotesService.class);
	
	InscriptionService inscriptionService = (InscriptionService)Component.getInstance(InscriptionService.class);
	
	ClasseService classeService = (ClasseService)Component.getInstance(ClasseService.class);
	
	MatiereService matiereService = (MatiereService)Component.getInstance(MatiereService.class);
	
	private Notes notes;
    private List<Notes> listNotes= new ArrayList<Notes>();
    private Classe filtreClasse;
    private Cycle filtreCycle;
    private AnneeAcademique filtreAcademique;
    private Semestre filtreSemestre;
    private Matiere filtreMatiere;
    private TypeNote filtreTypeNote;
    private List<Classe> listClasses = new ArrayList<Classe>();
    private List<Matiere> listMatieres = new ArrayList<Matiere>();
    private List<TypeNote> listTypeNotes = new ArrayList<TypeNote>();
    private List<Inscription> listInscriptions = new ArrayList<Inscription>();

    public void init(){
        notes = new Notes();
        filtreCycle = null;
        filtreClasse = null ;
        filtreMatiere = null;
        filtreAcademique =  null;
        filtreSemestre = null;
    }
    
    public void findNotes(){
        if(filtreClasse != null && filtreCycle != null && filtreAcademique != null && filtreSemestre != null
                && filtreTypeNote != null) {
            listNotes = findNotesEnregistrees(filtreMatiere, filtreSemestre, filtreClasse, filtreTypeNote,
                    filtreAcademique);
        }else{
        	facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Tous les critères de recherche sont obligatoires!");
        }
    }
    
    public void consulter(Notes iNotes){
        notes = notesService.consuler(iNotes);
        
        //modif();
    }
    
    
    public void modif(){
        try {
        	Boolean flag = false;
        	if(notes.getValNotes()==null)return;
            if(notes.getValNotes()!=null)
            	flag =notesService.controle(notes);
            if(flag){
            	facesMessages.addToControlFromResourceBundle("erreurGenerique", 
            			"La note doit être comprise entre 0 et 20!");
            	return;
            }
            notes.setDateModif(new Date());
            //notes.setUserModif(loginBean.getMonUser());
            ChakaUtils.println("modif notes+"+notes.getMatiere().getLibelle()+" val");
            notes.setUserModif(utilisateur);
            dataSource.update(notes);
            dataSource.flush();

            facesMessages.addToControlFromResourceBundle("infoGenerique", 
            		"Notes  de "+notes.getMatiere().getLibelle()+" modifié avec succés.");
            
            //init();
            listNotes = findNotesEnregistrees(filtreMatiere, filtreSemestre, filtreClasse, filtreTypeNote,
                    filtreAcademique);
        } catch (Exception e) {
            e.printStackTrace();
            facesMessages.addToControlFromResourceBundle("erreurGenerique", 
        			"Erreur lors de la modification des notes!");
        }
    }
    
    public String versModif()
	{
    	init();
		return "/pages/parametrage/modifNotes.xhtml";
	}
    
    @SuppressWarnings("unchecked")
	public List<Notes> findNotesEnregistrees(Matiere matiere, Semestre semestre, Classe classe, TypeNote typeNote,
            AnneeAcademique aa) {
    	try {
			StringBuilder hql = new StringBuilder();
			hql.append("from Notes n");
			hql.append(" inner join fetch n.matiere m");
			hql.append(" inner join fetch n.typeNote t");
			hql.append(" inner join fetch n.semestre s");
			hql.append(" inner join fetch n.inscription i");
			hql.append(" inner join fetch i.classe cl");
			hql.append(" inner join fetch cl.institut it");
			hql.append(" inner join fetch cl.cycle cy");
			hql.append(" inner join fetch i.etudiant et");
			hql.append(" inner join fetch et.parent pa");
			hql.append(" inner join fetch i.anneeAcademique a");
			hql.append(" where it.idInstitut =:paramInstit");
			if(utilisateur.getProfile().getLibelle().equals(Constantes.PARENT))
            	hql.append(" and pa.idUtilisateur =:paramParent");
			
			if(classe != null)hql.append(" and cl.idClasse =:paramClasse");
			if(matiere != null)hql.append(" and m.idMatiere =:paramMatiere");
			if(aa != null)hql.append(" and a.idAnneeAc =:paramAnnee");
			
			if(semestre != null)hql.append(" and s.idSemestre =:paramSemest");
			if(typeNote != null)hql.append(" and t.idTypeNote =:paramTypeNote");
			hql.append(" order by et.nom, et.prenom, m.libelle");
			Query q = dataSource.createQuery(hql.toString());
			if(classe != null) q.setParameter("paramClasse",classe.getIdClasse());
			if(matiere != null)q.setParameter("paramMatiere",matiere.getIdMatiere());
			if(aa != null)q.setParameter("paramAnnee",aa.getIdAnneeAc());
			if(semestre != null)q.setParameter("paramSemest",semestre.getIdSemestre());
			if(typeNote != null)q.setParameter("paramTypeNote",typeNote.getIdTypeNote());
			q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
			if(utilisateur.getProfile().getLibelle().equals(Constantes.PARENT))
            	q.setParameter("paramParent", utilisateur.getIdUtilisateur());
			List<Notes> list = q.list();
			if(!list.isEmpty()) ChakaUtils.println("liste notes en consultation non vide"+list.size());
			return  list;
		}catch (HibernateException e){
			e.printStackTrace();
		}
		return null;
	}
    
    public void findClasseByCycle(ValueChangeEvent e) {
        ChakaUtils.println("entrez methodeeeeeeee");
        if (e.getNewValue()!=null) {
        	filtreCycle = (Cycle)e.getNewValue();
            ChakaUtils.println("avec filtreeeeeeee="+filtreCycle.getIdCycle());
            listClasses = inscriptionService.getClasseByCycle(filtreCycle);
        }else listClasses = classeService.allClasse();
    }
    
    public void findMatiereByClasse(ValueChangeEvent e) {
    	if (e.getNewValue()!=null) {
    		ChakaUtils.println("avec filtre cl");
    		filtreClasse = (Classe)e.getNewValue();
            listMatieres = matiereService.matieresByClasse(filtreClasse);
    	}
    }

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}

	public List<Notes> getListNotes() {
		return listNotes;
	}

	public void setListNotes(List<Notes> listNotes) {
		this.listNotes = listNotes;
	}

	public Classe getFiltreClasse() {
		return filtreClasse;
	}

	public void setFiltreClasse(Classe filtreClasse) {
		this.filtreClasse = filtreClasse;
	}

	public Cycle getFiltreCycle() {
		return filtreCycle;
	}

	public void setFiltreCycle(Cycle filtreCycle) {
		this.filtreCycle = filtreCycle;
	}

	public AnneeAcademique getFiltreAcademique() {
		return filtreAcademique;
	}

	public void setFiltreAcademique(AnneeAcademique filtreAcademique) {
		this.filtreAcademique = filtreAcademique;
	}

	public Semestre getFiltreSemestre() {
		return filtreSemestre;
	}

	public void setFiltreSemestre(Semestre filtreSemestre) {
		this.filtreSemestre = filtreSemestre;
	}

	public Matiere getFiltreMatiere() {
		return filtreMatiere;
	}

	public void setFiltreMatiere(Matiere filtreMatiere) {
		this.filtreMatiere = filtreMatiere;
	}

	public TypeNote getFiltreTypeNote() {
		return filtreTypeNote;
	}

	public void setFiltreTypeNote(TypeNote filtreTypeNote) {
		this.filtreTypeNote = filtreTypeNote;
	}

	public List<Classe> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
	}

	public List<Matiere> getListMatieres() {
		return listMatieres;
	}

	public void setListMatieres(List<Matiere> listMatieres) {
		this.listMatieres = listMatieres;
	}

	public List<TypeNote> getListTypeNotes() {
		return listTypeNotes;
	}

	public void setListTypeNotes(List<TypeNote> listTypeNotes) {
		this.listTypeNotes = listTypeNotes;
	}

	public List<Inscription> getListInscriptions() {
		return listInscriptions;
	}

	public void setListInscriptions(List<Inscription> listInscriptions) {
		this.listInscriptions = listInscriptions;
	}
    
}
