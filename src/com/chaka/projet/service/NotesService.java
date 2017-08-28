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
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.AnneeAcademique;
import com.tidiane.model.Classe;
import com.tidiane.model.Cycle;
import com.tidiane.model.Inscription;
import com.tidiane.model.Matiere;
import com.tidiane.model.Notes;
import com.tidiane.model.Semestre;
import com.tidiane.model.TypeNote;

@Name("notesService")
@Scope(ScopeType.SESSION)
public class NotesService implements Serializable {

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

	ClasseService classeService = (ClasseService) Component.getInstance(ClasseService.class);

	MatiereService matiereService = (MatiereService) Component.getInstance(MatiereService.class);

	InscriptionService inscriptionService = (InscriptionService) Component.getInstance(InscriptionService.class);

	private Notes notes;
	private List<Notes> listNotes = new ArrayList<Notes>();
	private Classe filtreClasse;
	private Cycle filtreCycle;
	private AnneeAcademique filtreAcademique;
	private Semestre filtreSemestre;
	private Matiere filtreMatiere;
	private TypeNote filtreTypeNote;
	private List<Classe> listClasses = new ArrayList<Classe>();
	private List<Matiere> listMatieres = new ArrayList<Matiere>();
	private List<Semestre> listSemestres = new ArrayList<Semestre>();
	@SuppressWarnings("unused")
	private List<TypeNote> listTypeNotes = new ArrayList<TypeNote>();
	private List<Inscription> listInscriptions = new ArrayList<Inscription>();

	public void init() {
		notes = new Notes();
		filtreCycle = null;
		filtreClasse = null;
		filtreMatiere = null;
		filtreAcademique = null;
		filtreSemestre = null;
	}

	public String versNote() {
		init();
		return "/pages/parametrage/gestionNotes.xhtml";
	}

	public void ajout() {
		try {
			if (listNotes.isEmpty()) {
				facesMessages.addToControlFromResourceBundle("erreurGenerique", "Aucune note à saisir!");
				return;
			}
			Boolean flag = false;
			for (Notes eachNotes : listNotes) {
				ChakaUtils.println("not=" + eachNotes);
				if (eachNotes.getValNotes() != null)
					flag = controle(eachNotes);
			}
			if (flag) {
				facesMessages.addToControlFromResourceBundle("erreurGenerique",
						"La note doit être comprise entre 0 et 20!");
				return;
			}

			for (Notes eachNotes : listNotes) {
				eachNotes.setDateSaisie(new Date());
				eachNotes.setUserSaisie(utilisateur);
				dataSource.save(eachNotes);
			}
			dataSource.flush();
			facesMessages.addToControlFromResourceBundle("infoGenerique",
					"Notes de " + filtreMatiere.getLibelle() + " saisie avec succés.");

			// init();
			listNotes = new ArrayList<Notes>();
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de la saisie des notes!");

		}
	}

	public Boolean controle(Notes notes1) {
		Boolean flag = false;
		if (notes1.getValNotes() < 0 || notes1.getValNotes() > 20) {
			flag = true;

			// notes1.setValNotes(0d);
		}
		return flag;
	}

	public void recupLesInscrits() {
		if (filtreClasse != null && filtreCycle != null && filtreAcademique != null && filtreSemestre != null
				&& filtreMatiere != null && filtreTypeNote != null) {
			listNotes = new ArrayList<Notes>();
			listInscriptions = inscriptionService.allInscriptionsByFiltre(filtreClasse, filtreCycle, filtreAcademique,
					null);
			for (Inscription eachInscription : listInscriptions) {
				Notes iNotes = new Notes();
				iNotes.setInscription(eachInscription);
				iNotes.setDateSaisie(new Date());
				iNotes.setMatiere(filtreMatiere);
				iNotes.setSemestre(filtreSemestre);
				iNotes.setTypeNote(filtreTypeNote);
				listNotes.add(iNotes);
			}
		} else {
			facesMessages.addToControlFromResourceBundle("erreurGenerique",
					"Tous les critères de recherche sont obligatoires!");
		}
	}

	public void findClasseByCycle(ValueChangeEvent e) {
		ChakaUtils.println("entrez methodeeeeeeee");
		if (e.getNewValue() != null) {
			filtreCycle = (Cycle) e.getNewValue();
			ChakaUtils.println("avec filtreeeeeeee=" + filtreCycle.getIdCycle());
			listClasses = inscriptionService.getClasseByCycle(filtreCycle);
		} else
			listClasses = classeService.allClasse();
	}

	public void findMatiereByClasse(ValueChangeEvent e) {
		if (e.getNewValue() != null) {
			ChakaUtils.println("avec filtre cl");
			filtreClasse = (Classe) e.getNewValue();
			listMatieres = matiereService.matieresByClasse(filtreClasse);
		}
	}

	public Notes consuler(Notes notes) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("from Notes n");
			hql.append(" inner join fetch n.matiere");
			hql.append(" inner join fetch n.typeNote");
			hql.append(" inner join fetch n.semestre");
			hql.append(" inner join fetch n.inscription i");
			hql.append(" inner join fetch i.classe cl");
			hql.append(" inner join fetch cl.institut it");
			hql.append(" inner join fetch cl.cycle cy");
			hql.append(" inner join fetch i.etudiant");
			hql.append(" inner join fetch i.anneeAcademique");
			hql.append(" where n.idNotes =:paramId");
			Notes notes1 = (Notes) dataSource.createQuery(hql.toString()).setParameter("paramId", notes.getIdNotes())
					.uniqueResult();
			return notes1;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<TypeNote> allTypeNote() {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("from TypeNote t");
			hql.append(" order by t.libelle");
			List<TypeNote> list = dataSource.createQuery(hql.toString()).list();
			return list;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
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
			hql.append(" inner join fetch i.anneeAcademique a");
			hql.append(" where it.idInstitut =:paramInstit");

			if (classe != null)
				hql.append(" and cl.idClasse =:paramClasse");
			if (matiere != null)
				hql.append(" and m.idMatiere =:paramMatiere");
			if (aa != null)
				hql.append(" and a.idAnneeAc =:paramAnnee");

			if (semestre != null)
				hql.append(" and s.idSemestre =:paramSemest");
			if (typeNote != null)
				hql.append(" and t.idTypeNote =:paramTypeNote");
			hql.append(" order by et.nom, et.prenom, m.libelle");
			Query q = dataSource.createQuery(hql.toString());
			if (classe != null)
				q.setParameter("paramClasse", classe.getIdClasse());
			if (matiere != null)
				q.setParameter("paramMatiere", matiere.getIdMatiere());
			if (aa != null)
				q.setParameter("paramAnnee", aa.getIdAnneeAc());
			if (semestre != null)
				q.setParameter("paramSemest", semestre.getIdSemestre());
			if (typeNote != null)
				q.setParameter("paramTypeNote", typeNote.getIdTypeNote());
			q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
			List<Notes> list = q.list();
			if (!list.isEmpty())
				ChakaUtils.println("liste notes en consultation non vide" + list.size());
			return list;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
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
		return listTypeNotes = allTypeNote();
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

	@SuppressWarnings("unchecked")
	public List<Semestre> getListSemestres() {
		StringBuilder hql = new StringBuilder();
		hql.append("from Semestre s");
		hql.append(" order by s.libelle");
		listSemestres = dataSource.createQuery(hql.toString()).list();
		return listSemestres;
	}

	public void setListSemestres(List<Semestre> listSemestres) {
		this.listSemestres = listSemestres;
	}

}
