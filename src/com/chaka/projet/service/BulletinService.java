package com.chaka.projet.service;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.tidiane.model.Bulletin;
import com.tidiane.model.CalculBulletin;
import com.tidiane.model.Classe;
import com.tidiane.model.Cycle;
import com.tidiane.model.Etudiant;
import com.tidiane.model.Inscription;
import com.tidiane.model.LnkBulletinMatiere;
import com.tidiane.model.LnkCoefMatiere;
import com.tidiane.model.Matiere;
import com.tidiane.model.Notes;
import com.tidiane.model.Presence;
import com.tidiane.model.Semestre;

@Name("bulletinService")
@Scope(ScopeType.SESSION)
public class BulletinService implements Serializable {

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

	EtudiantService etudiantService = (EtudiantService) Component.getInstance(EtudiantService.class);

	InscriptionService inscriptionService = (InscriptionService) Component.getInstance(InscriptionService.class);

	ClasseService classeService = (ClasseService) Component.getInstance(ClasseService.class);

	NotesService notesService = (NotesService) Component.getInstance(NotesService.class);

	MatiereService matiereService = (MatiereService) Component.getInstance(MatiereService.class);
	PresenceService presenceService = (PresenceService) Component.getInstance(PresenceService.class);

	private Bulletin bulletin;
	private List<Bulletin> listBulletins = new ArrayList<Bulletin>();
	private List<Classe> listClasses = new ArrayList<Classe>();
	private List<Notes> listNotes = new ArrayList<Notes>();
	private List<Etudiant> listEtudiants = new ArrayList<Etudiant>();
	private List<Matiere> listMatieres = new ArrayList<Matiere>();
	private Classe filtreClasse;
	private Cycle filtreCycle;
	private AnneeAcademique filtreAcademique;
	private Semestre filtreSemestre;
	DecimalFormat df = new DecimalFormat("#,##0.##");

	public void init() {
		bulletin = new Bulletin();
		filtreCycle = null;
		filtreClasse = null;
		filtreAcademique = null;
		filtreSemestre = null;
		listNotes = new ArrayList<Notes>();
	}
	
	public String versBulletin()
	{
    	init();
		return "/pages/parametrage/bulletin.xhtml";
	}

	public void findNotes() {
		
		if (filtreClasse != null && filtreCycle != null && filtreAcademique != null && filtreSemestre != null) {
			if(verifBulletinDejaEditer())
				facesMessages.addToControlFromResourceBundle("erreurGenerique",
						"Bulletin dèjà créé, voulez-vous les rééditer?");
			listNotes = new ArrayList<Notes>();
			listNotes = notesService.findNotesEnregistrees(null, filtreSemestre, filtreClasse, null, filtreAcademique);

		} else {
			facesMessages.addToControlFromResourceBundle("erreurGenerique",
					"Tous les critères de recherche sont obligatoires!");
		}
	}

	public Boolean verifBulletinDejaEditer() {
		boolean flag = false;
		listBulletins = findBulletins(filtreClasse, filtreSemestre, filtreAcademique);
		if (listBulletins != null && !listBulletins.isEmpty())
			flag = true;
		ChakaUtils.println("list bukk:" + listBulletins.size());
		return flag;
	}

	public void editerBulletin() {
		try {
			if (listNotes.isEmpty()) {
				facesMessages.addToControlFromResourceBundle("erreurGenerique",
						"Aucune note trouvée afin de pouvoir générer les bulletins!");
				return;
			}
			if (verifBulletinDejaEditer()) {
				modifierBulletin();
			}
			listEtudiants = new ArrayList<Etudiant>();
			List<Inscription> listInscriptions = inscriptionService.allInscriptionsByFiltre(filtreClasse, filtreCycle,
					filtreAcademique, null);
			for (Inscription eachInscription : listInscriptions) {
				ChakaUtils.println("etud bi ds inscr:" + eachInscription.getEtudiant().getIdEtudiant() + " nom:"
						+ eachInscription.getEtudiant().getNom());
				listEtudiants.add(etudiantService.getEtudiantByNum(eachInscription.getEtudiant().getNumEleve()));
			}

			int i = 0;
			listMatieres = matiereService.matieresByClasse(filtreClasse);
			CalculBulletin calculBulletin = new CalculBulletin();
			List<CalculBulletin> listCalculBulletins = new ArrayList<CalculBulletin>();
			for (Etudiant eachtudiant : listEtudiants) {
				ChakaUtils.println("etud bi:" + eachtudiant.getIdEtudiant() + " nom:" + eachtudiant.getNom());
				calculBulletin = new CalculBulletin();
				calculBulletin.setListNotes(new ArrayList<Notes>());
				for (int j = i; j < listNotes.size(); j++) {
					Notes iNotes = new Notes();// listNotes.get(j);
					iNotes.setMatiere(listNotes.get(j).getMatiere());
					iNotes.setTypeNote(listNotes.get(j).getTypeNote());
					iNotes.setValNotes(listNotes.get(j).getValNotes());
					iNotes.setSemestre(listNotes.get(j).getSemestre());
					iNotes.setDateSaisie(listNotes.get(j).getDateSaisie());
					iNotes.setIdNotes(listNotes.get(j).getIdNotes());
					iNotes.setInscription(listNotes.get(j).getInscription());
					if (eachtudiant.equals(iNotes.getInscription().getEtudiant())) {
						calculBulletin.getListNotes().add(iNotes);
						ChakaUtils.println("note add:" + iNotes.getValNotes() + " mat:"
								+ iNotes.getMatiere().getLibelle() + " typ:" + iNotes.getTypeNote().getLibelle());
					} else {
						ChakaUtils.println("indic i:" + i + " indic j+" + j);
						i = j;
						break;
					}
				}
				listCalculBulletins.add(calculBulletin);

			}

			ChakaUtils.println("taille list calcul Bulletin:" + listCalculBulletins.size());
			List<Bulletin> list = new ArrayList<Bulletin>();
			for (CalculBulletin eachCalculBulletin : listCalculBulletins) {
				bulletin = new Bulletin();
				bulletin = findNotesByMatiere(eachCalculBulletin);
				ChakaUtils.println("inscr ds bull+" + bulletin.getInscription().getIdInscription() + " nom"
						+ bulletin.getInscription().getEtudiant().getNom());
				bulletin.setAppreciation(getAppreciation(bulletin.getMoyGen()));
				// ************************** si c second semestre
				if (filtreSemestre.getLibelleCourt().equals(Constantes.SECOND_SEMESTRE)) {
					ChakaUtils.println("bulletin du second semestre prise en compte du 1er sem");
					Bulletin bulletinPemierSem = findBulletinsPremierSem(filtreClasse, Constantes.PREMIER_SEMESTRE,
							filtreAcademique, bulletin.getInscription());
					if (bulletinPemierSem != null && bulletinPemierSem.getIdBulletin() != null) {
						bulletin.setMoyPremierSemestre(
								Double.parseDouble((df.format(bulletinPemierSem.getMoyGen())).replace(',', '.')));
						bulletin.setMoySemestre(Double
								.parseDouble((df.format((bulletin.getMoyGen() + bulletinPemierSem.getMoyGen()) / 2))
										.replace(',', '.')));
						if (bulletin.getMoySemestre() < 10) {// TODO a DEFINIR
																// SI C 10 OU UN
																// PEU MOINS
																// COMME 9.
							bulletin.getInscription().setRedouble(true);
							dataSource.update(bulletin.getInscription());
						}
					}
				}
				list.add(bulletin);
			}
			dataSource.flush();
			rangBulletin(list);
			Integer ra = list.size();
			for (Bulletin eachB : list) {
				eachB.setRangB(ra);
				ra -= 1;
			}
			for (Bulletin eachB : list) {
				List<Presence> listP = presenceService.presencesByClasse(filtreClasse, filtreAcademique,
						eachB.getInscription().getEtudiant(), null, filtreSemestre);
				if (!listP.isEmpty())
					eachB.setNbAbscence(listP.size());
				else
					eachB.setNbAbscence(0);
				eachB.setUserSaisie(utilisateur);
				eachB.setDateEdition(new Date());
				dataSource.save(eachB);
			}
			dataSource.flush();
			facesMessages.addToControlFromResourceBundle("infoGenerique", "Bulletin(s) édité avec succés!");

			init();
			// listNotes = new ArrayList<Notes>();
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'édition des bulletins!");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Bulletin> findBulletins(Classe classe, Semestre semestre, AnneeAcademique aa) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct b from Bulletin b");
			hql.append(" inner join fetch b.semestre s");
			hql.append(" inner join fetch b.listLnkBulletinMatieres lst");
			hql.append(" inner join fetch lst.matiere m");
			hql.append(" inner join fetch b.inscription i");
			hql.append(" inner join fetch i.classe cl");
			hql.append(" inner join fetch cl.institut it");
			hql.append(" inner join fetch cl.cycle cy");
			hql.append(" inner join fetch i.etudiant et");
			hql.append(" inner join fetch i.anneeAcademique a");
			hql.append(" where b.deleted is false and it.idInstitut =:paramInstit");
			if (classe != null)
				hql.append(" and cl.idClasse =:paramClasse");
			if (semestre != null)
				hql.append(" and s.idSemestre =:paramSem");
			if (aa != null)
				hql.append(" and a.idAnneeAc =:paramAnAc");

			hql.append(" order by et.nom, et.prenom, a.anneeFin desc");
			Query q = dataSource.createQuery(hql.toString());
			if (classe != null)
				q.setParameter("paramClasse", classe.getIdClasse());
			if (semestre != null)
				q.setParameter("paramSem", semestre.getIdSemestre());
			if (aa != null)
				q.setParameter("paramAnAc", aa.getIdAnneeAc());
			// q.setParameter("paramFalse", false);
			q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());
			List<Bulletin> list = q.list();
			if (!list.isEmpty())
				ChakaUtils.println("liste bull non vide" + list.size());
			return list;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Bulletin findNotesByMatiere(CalculBulletin calculBulletin) {

		List<Notes> list = new ArrayList<Notes>();
		Bulletin iBulletin = new Bulletin();
		int i = 0;
		for (Matiere eachMatiere : listMatieres) {
			LnkBulletinMatiere lnkBulletinMatiere = new LnkBulletinMatiere();
			Integer nbNote = 0;
			for (int j = i; j < calculBulletin.getListNotes().size(); j++) {
				Notes iNotes = new Notes();// listNotes.get(j);
				iNotes.setMatiere(calculBulletin.getListNotes().get(j).getMatiere());
				iNotes.setTypeNote(calculBulletin.getListNotes().get(j).getTypeNote());
				iNotes.setValNotes(calculBulletin.getListNotes().get(j).getValNotes());
				iNotes.setSemestre(calculBulletin.getListNotes().get(j).getSemestre());
				iNotes.setDateSaisie(calculBulletin.getListNotes().get(j).getDateSaisie());
				iNotes.setIdNotes(calculBulletin.getListNotes().get(j).getIdNotes());
				iNotes.setInscription(calculBulletin.getListNotes().get(j).getInscription());
				if (eachMatiere.equals(iNotes.getMatiere())) {
					list.add(iNotes);
					if (iNotes.getTypeNote().getLibelleCourt().equals(Constantes.DEVOIR_TYPE_NOTE)) {
						lnkBulletinMatiere.setTotNote(lnkBulletinMatiere.getTotNote() != null
								? lnkBulletinMatiere.getTotNote() + iNotes.getValNotes() : 0d + iNotes.getValNotes());
						nbNote += 1;
					}
					if (iNotes.getTypeNote().getLibelleCourt().equals(Constantes.EXAMEN_TYPE_NOTE))
						lnkBulletinMatiere.setNoteExamen(iNotes.getValNotes());
					ChakaUtils.println("nb note :" + nbNote);
					// ChakaUtils.println("nb note
					// :"+lnkBulletinMatiere.getTotNote()==null);
					if (nbNote != 0)
						lnkBulletinMatiere.setMoyNote(Double
								.parseDouble((df.format(lnkBulletinMatiere.getTotNote() / nbNote)).replace(',', '.')));
					// ChakaUtils.println("moy note a
					// voir:"+lnkBulletinMatiere.getMoyNote()==null?0:lnkBulletinMatiere.getMoyNote());
					// ChakaUtils.println("note exam a
					// voir:"+lnkBulletinMatiere.getNoteExamen()==null?0:lnkBulletinMatiere.getNoteExamen());
					if (lnkBulletinMatiere.getMoyNote() != null && lnkBulletinMatiere.getNoteExamen() != null) {
						lnkBulletinMatiere.setMoyenneMat(Double.parseDouble(
								(df.format((lnkBulletinMatiere.getMoyNote() + lnkBulletinMatiere.getNoteExamen()) / 2))
										.replace(',', '.')));
					} else if (lnkBulletinMatiere.getMoyNote() != null)
						lnkBulletinMatiere.setMoyenneMat(
								Double.parseDouble((df.format(lnkBulletinMatiere.getMoyNote())).replace(',', '.')));
					else if (lnkBulletinMatiere.getNoteExamen() != null)
						lnkBulletinMatiere.setMoyenneMat(
								Double.parseDouble((df.format(lnkBulletinMatiere.getNoteExamen())).replace(',', '.')));
					if (lnkBulletinMatiere.getMoyenneMat() != null && lnkBulletinMatiere.getMoyenneMat() >= 12)
						lnkBulletinMatiere.setTableauH("TH");
					else
						lnkBulletinMatiere.setTableauH(null);
					lnkBulletinMatiere.setAppreciation(getAppreciation(lnkBulletinMatiere.getMoyenneMat()));
					lnkBulletinMatiere.setMatiere(eachMatiere);
					lnkBulletinMatiere.setBulletin(iBulletin);
					lnkBulletinMatiere.setCoef(getCoefMatiere(filtreClasse, eachMatiere));
					lnkBulletinMatiere.setNoteXCoef(lnkBulletinMatiere.getCoef() * lnkBulletinMatiere.getMoyenneMat());
					if (iBulletin.getListLnkBulletinMatieres() == null)
						iBulletin.setListLnkBulletinMatieres(new ArrayList<LnkBulletinMatiere>());
					if (!matierDejaPresent(iBulletin, lnkBulletinMatiere))
						iBulletin.getListLnkBulletinMatieres().add(lnkBulletinMatiere);
				} else {
					i = j;
					break;
				}
			}
		}
		Double totCoef = 0d;
		Double totMat = 0d;
		ChakaUtils.println("taill->" + calculBulletin.getListNotes().size());
		ChakaUtils.println("l'edtud->" + calculBulletin.getListNotes().get(0).getInscription().getEtudiant().getNom());

		for (LnkBulletinMatiere eachLnk : iBulletin.getListLnkBulletinMatieres()) {
			ChakaUtils.println("matiere:" + eachLnk.getMatiere().getLibelle() + " moy note:" + eachLnk.getMoyNote()
					+ " note exam:" + eachLnk.getNoteExamen() + " moy matiere:" + eachLnk.getMoyenneMat());
			if (eachLnk.getMoyenneMat() != null) {
				eachLnk.setCoef(getCoefMatiere(filtreClasse, eachLnk.getMatiere()));
				totCoef += eachLnk.getCoef();// bulletinBusiness.getCoefMatiere(filtreClasse,
												// eachLnk.getMatiere());
				totMat += eachLnk.getMoyenneMat() * eachLnk.getCoef();// bulletinBusiness.getCoefMatiere(filtreClasse,
																		// eachLnk.getMatiere());
			}
		}
		// ChakaUtils.println("coef total:"+totCoef);
		Double moyG = totMat / totCoef;
		String mg = (df.format(moyG)).replace(',', '.');
		mg.replace(',', '.');
		if (totCoef != 0d)
			iBulletin.setMoyGen(Double.parseDouble(mg));
		if (iBulletin.getSemestre() == null)
			iBulletin.setSemestre(filtreSemestre);
		if (iBulletin.getInscription() == null)
			iBulletin.setInscription(calculBulletin.getListNotes().get(0).getInscription());
		// double d = (double) Math.round(tonDouble * 100) / 100;
		// double d = (double) Math.round((totCoef/totCoef)*100) /
		// 100;//(totMat/totCoef)*100
		ChakaUtils.println("tot Coef de" + calculBulletin.getListNotes().get(0).getInscription().getEtudiant().getNom()
				+ " est" + totCoef);
		ChakaUtils.println("Moy Gene :" + calculBulletin.getListNotes().get(0).getInscription().getEtudiant().getNom()
				+ " est" + iBulletin.getMoyGen());
		return iBulletin;
	}

	public void modifierBulletin() {
		try {
			if (listNotes.isEmpty()) {
				facesMessages.addToControlFromResourceBundle("erreurGenerique",
						"Aucune note trouvée afin de pouvoir modifier les bulletins!");
				return;
			}
			for (Bulletin eachBulletin : listBulletins) {
				for (LnkBulletinMatiere lnk : eachBulletin.getListLnkBulletinMatieres()) {
					lnk.setDeleted(true);
				}
				eachBulletin.setDeleted(true);
				dataSource.update(eachBulletin);
			}
			dataSource.flush();
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.addToControlFromResourceBundle("erreurGenerique", "Erreur lors de l'édition des bulletins!");
		}
	}

	public Bulletin consuler(Bulletin bull) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("from Bulletin b");
			hql.append(" inner join fetch b.semestre s");
			hql.append(" inner join fetch b.listLnkBulletinMatieres lst");
			hql.append(" inner join fetch lst.matiere m");
			hql.append(" inner join fetch b.inscription i");
			hql.append(" inner join fetch i.classe cl");
			hql.append(" inner join fetch cl.institut it");
			hql.append(" inner join fetch cl.cycle cy");
			hql.append(" inner join fetch i.etudiant et");
			hql.append(" inner join fetch i.anneeAcademique a");
			hql.append(" where b.deleted is false and lst.deleted is false and b.idBulletin =:param");
			bulletin = (Bulletin) dataSource.createQuery(hql.toString()).setParameter("param", bull.getIdBulletin())
					.uniqueResult();
			return bulletin;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void rangBulletin(List<Bulletin> listBul) {
		Collections.sort(listBul, new Comparator<Bulletin>() {
			public int compare(Bulletin r1, Bulletin r2) {
				return r1.getMoyGen().compareTo(r2.getMoyGen());

			}
		});
	}

	private String getAppreciation(Double moyGen) {
		String appreciation = "";
		if (moyGen == null)
			return "";
		if (moyGen < 20 && moyGen >= 18)
			appreciation = "Félicitations";
		else if (moyGen < 18 && moyGen >= 16)
			appreciation = "Très Bien";
		else if (moyGen < 16 && moyGen >= 14)
			appreciation = "Bon travail";
		else if (moyGen < 14 && moyGen >= 12)
			appreciation = "Assez bien";
		else if (moyGen < 12 && moyGen >= 10)
			appreciation = "Passable";
		else if (moyGen < 10 && moyGen >= 8)
			appreciation = "Des difficultès";
		else if (moyGen < 8 && moyGen >= 6)
			appreciation = "Travail insuffisant";
		else if (moyGen < 6)
			appreciation = "Trés insuffisant";
		return appreciation;
	}

	public Boolean matierDejaPresent(Bulletin bulletin, LnkBulletinMatiere lnkBulletinMatiere) {
		boolean flag = false;
		if (bulletin.getListLnkBulletinMatieres().size() == 0)
			return flag;
		for (LnkBulletinMatiere lnk : bulletin.getListLnkBulletinMatieres()) {
			if (lnk.getMatiere().equals(lnkBulletinMatiere.getMatiere()))
				flag = true;
		}

		return flag;
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

	@SuppressWarnings("unchecked")
	public Integer getCoefMatiere(Classe classe, Matiere matiere) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("from LnkCoefMatiere l");
			hql.append(" inner join fetch l.matiere m");
			hql.append(" inner join fetch l.classe c");
			hql.append(" inner join fetch c.institut it");
			hql.append(" where m.idMatiere =:paramMat and c.idClasse =:paramClass");
			hql.append(" and it.idInstitut =:paramInstit");
			List<LnkCoefMatiere> coefMatiere = dataSource.createQuery(hql.toString())
					.setParameter("paramMat", matiere.getIdMatiere()).setParameter("paramClass", classe.getIdClasse())
					.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut()).list();
			if (!coefMatiere.isEmpty())
				return coefMatiere.get(0).getValCoef();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Bulletin findBulletinsPremierSem(Classe classe, String semestre, AnneeAcademique aa,
			Inscription inscription) {
		try {
			Bulletin b = new Bulletin();
			StringBuilder hql = new StringBuilder();
			hql.append("from Bulletin b");
			hql.append(" inner join fetch b.semestre s");
			hql.append(" inner join fetch b.listLnkBulletinMatieres lst");
			hql.append(" inner join fetch lst.matiere m");
			hql.append(" inner join fetch b.inscription i");
			hql.append(" inner join fetch i.classe cl");
			hql.append(" inner join fetch cl.institut it");
			hql.append(" inner join fetch cl.cycle cy");
			hql.append(" inner join fetch i.etudiant et");
			hql.append(" inner join fetch i.anneeAcademique a");
			hql.append(" where b.deleted is false and it.idInstitut =:paramInstit");
			if (classe != null)
				hql.append(" and cl.idClasse =:paramClasse");
			if (semestre != null)
				hql.append(" and s.libelleCourt =:paramSem");
			if (aa != null)
				hql.append(" and a.idAnneeAc =:paramAnAc");
			if (inscription != null)
				hql.append(" and i.idInscription =:paramIns");

			hql.append(" order by et.nom, et.prenom, a.anneeFin desc");
			Query q = dataSource.createQuery(hql.toString());
			if (classe != null)
				q.setParameter("paramClasse", classe.getIdClasse());
			if (semestre != null)
				q.setParameter("paramSem", semestre);
			if (aa != null)
				q.setParameter("paramAnAc", aa.getIdAnneeAc());
			if (inscription != null)
				q.setParameter("paramIns", inscription.getIdInscription());
			q.setParameter("paramInstit", utilisateur.getInstitut().getIdInstitut());

			List<Bulletin> list = q.list();

			if (!list.isEmpty())
				b = list.get(0);
			return b;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}

	public List<Bulletin> getListBulletins() {
		return listBulletins;
	}

	public void setListBulletins(List<Bulletin> listBulletins) {
		this.listBulletins = listBulletins;
	}

	public List<Classe> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
	}

	public List<Notes> getListNotes() {
		return listNotes;
	}

	public void setListNotes(List<Notes> listNotes) {
		this.listNotes = listNotes;
	}

	public List<Etudiant> getListEtudiants() {
		return listEtudiants;
	}

	public void setListEtudiants(List<Etudiant> listEtudiants) {
		this.listEtudiants = listEtudiants;
	}

	public List<Matiere> getListMatieres() {
		return listMatieres;
	}

	public void setListMatieres(List<Matiere> listMatieres) {
		this.listMatieres = listMatieres;
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

}
