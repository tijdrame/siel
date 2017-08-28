/**
 * 
 */
package com.chaka.constantes;

import java.util.ArrayList;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.chaka.projet.entity.Utilisateur;

/**
 * @author Gora
 * 
 */

@Name("constantes")
@Scope(ScopeType.APPLICATION)
public class Constantes {

	/**
	 * 
	 */
	public Constantes() {
		// TODO Auto-generated constructor stub
	}

	public final static Integer CODE_CIVILITE_MR = 1;
	public final static Integer CODE_CIVILITE_MME = 2;
	public final static Integer CODE_CIVILITE_MLLE = 3;

	/** longueur des numero de compte courts. */
	public static final int LONGUEUR_NUMERO_COMPTE_COURT = 15;

	/** longueur des numero de compte long. */
	public static final int LONGUEUR_NUMERO_COMPTE_LONG = 30;
	/** longueur des code BIC . */
	public static final int LONGUEUR_CODE_BIC = 11;
	/** longueur des code IBAN . */
	public static final int LONGUEUR_CODE_IBAN = 34;
	/** longueur des libelles longs. */
	public static final int LONGUEUR_LIBELLE = 80;

	/** longueur des libelles courts. */
	public static final int LONGUEUR_LIBELLE_COURT = 20;

	/** longueur des commentaires des listes. */
	public static final int LONGUEUR_COMMENTAIRE = 30;

	/** longueur des commentaires des listes. */
	public static final int LONGUEUR_NATIONNALITE = 50;
	
	public static final String DEVOIR_TYPE_NOTE = "DEVOIR";
    public static final String EXAMEN_TYPE_NOTE = "EXAMEN";
    public static final String PREMIER_SEMESTRE = "SEMESTRE1";
    public static final String SECOND_SEMESTRE = "SEMESTRE2";
	
	/**
	 * longueur de 50.
	 */
	public final static int LONGUEUR_50 = 50;
	/**
	 * longueur number.
	 */
	public final static int LONGUEUR_TELEPHONE = 15;
	/**
	 * longueur code.
	 */
	public final static int LONGUEUR_CODE = 3;
	/**
	 * longueur code.
	 */
	public final static int LONGUEUR_MATRICULE = 20;
	/**
	 * longueur commentaire.
	 */
	public final static int LONGUEUR_ADRESSE = 350;
	/**
	 * longueur ville.
	 */
	public final static int LONGUEUR_VILLE = 80;
	/**
	 * longueur email.
	 */
	public final static int LONGUEUR_EMAIL = 50;
	/**
	 * longueur MAx.
	 */
	public final static int MAX = 20;

	public static final String code_statut = "recu";
	public static final String code_reliquider = "ARL";
	
	public static final String STATUT_A_IMMATRICULER = "IMMAT";
	
	public static final String code_statutsim = "SECTS";
	
	public static final String code_statutsimilaire = "SIM";

	/**
	 * longueur NBR_DE_MOIS.
	 */
	public final static int NBR_DE_MOIS = 2;
	/**
	 * 
	 */
	public final static String personne_physique = "PP";
	/**
	 * 
	 */
	public final static String accidentTravail = "AT";
	
	public final static String CODE_REC="REC";
	public final static String CODE_REC_P="REP";
	/**
	 * 
	 */
	public final static String maladieProf = "MP";
	/**
	 * 
	 */
	public final static String codeSexeM = "H";
	public final static String codeSexeF = "F";
	/**
	 * 
	 */
	public final static String personne_morale = "PM";

	public final static Long ID_STATUT_ATTENTE_CREATION = 1l;

	public final static Long ID_STATUT_VALIDATION = 2l;

	public final static Long ID_STATUT_ANNULATION = 3l;

	public final static Long ID_STATUT_SIMILAIRE = 4l;

	public final static Long ID_STATUT_ATTENTE_MODIFICATION = 4l;
	public final static double SMIG = 80000;

	public final static Integer ID_TYPE_ASSURE_VOLONTAIRE = 2;

	public final static Integer ID_TYPE_ASSURE_EMPLOYEUR = 1;

	/* LIEN PARENTE */
	public final static Long ID_LIEN_PARENTE_ENFANT = 1l;
	public final static Long ID_LIEN_PARENTE_CONJOINT = 2l;

	public final static String CODE_LIEN_PARENTE_PERE = "LPP";
	public final static String CODE_LIEN_PARENTE_MERE = "LPM";
	public final static String CODE_LIEN_PARENTE_CONJOINT = "LPCJ";
	public final static String CODE_LIEN_PARENTE_ENFANT = "LPE";
	public final static String CODE_LIEN_PARENTE_PROCHE_PARENT = "LPPP";
	public final static String CODE_LIEN_PARENTE_CONCUBINE = "LPCC";
	public final static String CODE_LIEN_PARENTE_FEMME= "LF";

	public final static String POSITION_LIEN_PARENTE_PERE = "1";
	public final static String POSITION_LIEN_PARENTE_MERE = "2";
	public final static String POSITION_LIEN_PARENTE_CONJOINT = "3";
	public final static String POSITION_LIEN_PARENTE_ENFANT = "4";
	public final static String POSITION_LIEN_PARENTE_PROCHE_PARENT = "6";
	public final static String POSITION_LIEN_PARENTE_CONCUBINE = "5";
	public final static String POSITION_LIEN_PARENTE_FEMME = "6";

	public final static String TYPE_CONDITION_ENFANT = "001";
	public final static String TYPE_CONDITION_CONJOINT = "010";
	public final static String TYPE_CONDITION_ASSURE = "100";

	private final static String CODE_TABLE_GRAPPE = "T0001";
	private final static String CODE_TABLE_ASSURE = "T0010";
	private final static String CODE_TABLE_EMPLOYEUR = "T0100";
	private final static String CODE_TABLE_COMPTE_INDIV = "T0001";

	private final static String CODE_ACTION_MODOFICATION = "A0010";
	private final static String CODE_ACTION_CREATION = "A0001";
	private final static String CODE_ACTION_SUPPRESSION = "A0100";
	private final static String CODE_ACTION_VALIDATION = "A1000";
	private final static String CODE_ACTION_ANNULATION = "A1001";
	/***************** TYPE DTS ****************************/
	public final static String CODE_TYPE_DEPOT_IMMATRICULATION_EMPLOYEUR = "IME";
	/***************** TYPE DEPOT ****************************/
	public final static String CODE_TYPE_DTS_ADDITIVE = "Additive";
	public final static String CODE_TYPE_DTS_NORMALE = "Principale";
	public final static String CODE_TYPE_DTS_ANTICIPEE = "Anticipée";
	public final static String CODE_TYPE_DTS_CORRECTIVE = "Corrective";
	public final static String CODE_TYPE_DEPOT_IMMATRICULATION_ASSURE = "IMA";
	public final static String CODE_TYPE_DEPOT_IMMATRICULATION_GRAPPE = "IMG";
	public final static String CODE_TYPE_DEPOT_DTS_MULTIPLE = "DMM";
	/*********************************************************/
	/****** Code Profile *************************/
	public final static String CODE_PROFILE_ADMIN = "admin";
	public final static String CODE_PROFILE_USER = "user";
	public final static String CODE_PROFILE_SUPERVISEUR = "spv";
	public final static String CODE_PROFILE_SUPERVISEURCS = "spc";
	public final static String CODE_PROFILE_CAISSIER = "csr";
	public final static String CODE_PROFILE_GESTIONNAIRE = "GES";
	public final static String CODE_PROFILE_DIRECTEUR = "DIR";
	public final static String CODE_PROFILE_VALIDATEUR = "VAL";
	
	public final static String CHEMIN_IMG_STAT_RESA="D://";
	
	//********************** CONSTANTE AFFECTATION EMPLOYEUR**********
	public final static String CODE_GESTIONNAIRE="GES";
	/***************** CODE PIECE ****************************/
	public final static String CODE_CERTIFICAT_SCOLARITE = "CS";
	public final static String CODE_CERTIFICAT_MEDICAL = "CMED";
	public final static String CODE_CERTIFICAT_MEDICAL_PROLONGATION = "CMP";
	public final static String CODE_STATUT_SCOLARISE = "LPSC";
	public final static String CODE_STATUT_NON_SCOLARISE = "NSCO";
	public final static String CODE_STATUT_RETARD_SCOLARITE = "RSCO";
	public final static String CODE_STATUT_PRET = "PRET";
	public final static String CODE_PREST_ARS = "ARS";
	public final static String CODE_STATUT_INVALIDE = "LPIV";
	/*********************************************************/


	/***************** CODE STRUCTURE ****************************/
	public final static String CODE_CNSS = "CNSS";
	public final static String CODE_CNAMGS = "CNAMGS";
	/*********************************************************/

	public final static String CODE_ALLOCATION = "AF";

	public final static String CODE_ALLOCATION_RENTREE_SCOLAIRE = "AR";

	private final static String CODE_GROSSESSE = "AG";

	private final static String CODE_MATERNITE = "AM";

	public final static String CODE_INDAMNITE_JOURNALIERE = "IJM";
	
	public final static String CODE_PRIME_NAISSANCE = "PN";

	private final static String CODE_FRAIS_MEDICAUX = "FM";

	public final static String CODE_AP1 = "APN1";

	public final static String CODE_AP2 = "APN2";
	
	public final static String CODE_AP3 = "APN3";

	private final static String DECOCHER_TOUS = "Décocher tous";

	private final static String COCHER_TOUS = "Cocher tous";
	
	public final static String CODE_RIP = "RIP";
	
	public final static String CODE_RS = "RS";

	private final static Integer LONGUEUR_TINYBLOB = 255;

	public final static int LONGUEUR_BLOB = 65535;

	public final static int LONGUEUR_MEDIUMBLOB = 16777215;

	public final static int LONGUEUR_LONGBLOB = 16777216;

	public final static int DebutTrimestreGestion = 1;

	public final static int DivisionID = 2;

	public final static int ServiceID = 3;

	public final static int DirectionID = 2;

	public final static int NBMAXDECHOMAGEMOIS = 24;

	public final static int NBMINDUREEAFFILIATION = 6;
	public final static int NBMINDUREEDTS = 6;
	/*** nombre de jours maximum de délai de paiement des cotisations ***/
	public final static int DELAI_PAIEMENT = 30;
	
	public final static int DELAI_CONTESTATION = 30;

	/******************** LES TRIMESTRES *************/
	/* le code du trimestre 1 */
	private final static String CODE_TRIMESTRE_1 = "1";
	/* le code du trimestre 2 */
	private final static String CODE_TRIMESTRE_2 = "2";
	/* le code du trimestre 3 */
	private final static String CODE_TRIMESTRE_3 = "3";
	/* le code du trimestre 4 */
	private final static String CODE_TRIMESTRE_4 = "4";
	/****************************MAJ POUR ABDOU***********************/
	public final static String CODE_ENTETE_PAGE="CAISSE NATIONALE DE SECURITE SOCIALE";
	public final static String CODE_PIED_PAGE="Boulevard de l'indFépendance - BP: 134 Libreville - Gabon - Tél: 01 79 12 00 - Fax: 01 74 64 25 - www.cnss.ga - Centre d'appels: 1432";
	/*************************************************/

	public final static String CODE_PENSION_ANTICIPEE = "PA";

	public final static String CODE_PENSION_INVALIDITE = "PI";

	public final static String CODE_PENSION = "P";

	public final static String CODE_PENSION_VIEILLESSE = "PV";

	public final static String CODE_PENSION_SURVIVANT = "PS";
	
	public final static String CODE_DEPOT_PIECE_ATMP = "DPA";
	
	public final static String CODE_SEQ_RP_CONS = "CONS";
	
	public final static String CODE_SEQ_RP_GUERRI = "GUERRI";
	
	public final static String CODE_SEQ_RP_EXPERT = "EXPERT";
	
	public final static String CODE_SEQ_RP_RECH = "RECH";
	
	public final static String CODE_DEPOT_PIECE_GROSSESSE = "DPG";
	
	
	public final static String CODE_REMBOURSEMENt_RP = "DRF";
	
	
	public final static String CODE_REMBOURSEMENt_GROSESSE = "DRG";
	
	
	public final static String CODE_PIECE_MAINTIENT_DROIT = "PMD";
	
	public final static String CODE_IDEMNITE_RP = "IRP";
	
	public final static String CODE_REGULARISATION = "DR";
	public final static String CODE_STATUT_ATTENTE_REGUL = "EAR";
	
	public final static String CODE_MODE_PAIYEMENT = "CMP";
	public final static String CODE_CENTRE_PAIYEMENT = "CCP";
	
	public final static String CODE_CHAMFEMENT_ATTRIBUTAIRE = "CHA";

	public final static String CODE_ALLOCATION_SURVIVANT = "AS";

	public final static String CODE_ALLOCATION_VIEILLESSE = "AV";

	public final static String CODE_REMBOURSEMENT_COTISATION = "RC";
	
	public final static String CODE_DEMANDE_PRET = "SAR";
	
	public final static String CODE_RADIATION_BENEFICIARE = "RB";
	
	public final static String CODE_SUSPENSION_BENEFICIARE = "SB";
	
	public final static String CODE_RENTRE_SURVIVANT = "RS";
	
	public final static String CODE_RENTRE_SURVIVANTRP = "RRS";
	
	public final static String CODE_RACHAT_RENTE = "RR";

	public final static String CODE_MOTIF_TRANS_CLOTURE = "CL";

	public final static String CODE_MOTIF_TRANS_SUSPENSION = "SP";
	
	
	public final static String CODE_MODIF_EMP = "DME";

	public final static String CODE_MOTIF_TRANS_REVISION = "RE";

	public final static String CODE_MOTIF_TRANS_LIQUIDATION = "LI";
	
	public final static String CODE_MODE_PAIEMENT_CHEQUE = "CHQ";
	/*******************************************************************/
	public final static String CODE_BRANCHE_ACTIVITE = "INDEFINI";
	
	public final static String CODE_QUARTIER_INCONNU = "INCONNU";

	// *********************STATUT***********************\\

	public final static String CODE_STATUT_ASSAINISSEMENT = "SAS";

	public final static String CODE_STATUT_ARCHIVAGE = "SARC";

	public final static String CODE_STATUT_RECU = "recu";
	
	public final static String CODE_AF = "DAF";
	
	public final static String CODE_GROCESSE = "DPM";
	
	public final static String CODE_PIECE_PROLONGATION = "CMP";
	
	public final static String CODE_RP = "DRP";
	
	public final static String CODE_PV = "DPV";
	
	public final static String CODE_STATUT_FIN= "fin";

	public final static String CODE_STATUT_VALIDATION = "SV";
	
	public final static String CODE_STATUT_LIQUIDATION = "BL";
	
	public final static String CODE_STATUT_EN_CREATION = "ECT";
	
	public final static String CODE_STATUT_REOUVERT = "SRVT";
	
	public final static String CODE_STATUT_REJET = "REJ";

	public final static String CODE_STATUT_EN_COURS_VALIDATION = "SECV";

	public final static String CODE_STATUT_ANNULATION = "SA";

	public final static String CODE_STATUT_BON_A_CONFIRMER = "SBAC";

	public final static String CODE_STATUT_EN_COURS_TRAITEMENT_SIMILARITE = "SECTS";

	public final static String CODE_STATUT_EN_COURS_VALIDATION_ANNULATION_ULTERIEUR = "SECVAU";

	public final static String CODE_STATUT_VALIDATION_ANNULATION_ULTERIEUR = "SVAU";

	public final static String CODE_STATUT_A_ASSAINIR = "SAA";

	public final static String CODE_STATUT_BONNE_A_SAISIR_DETAIL = "DBSD";

	public final static String CODE_STATUT_DETAIL_DTS_BON_A_VALIDER = "DDBV";

	public final static String CODE_STATUT_DTS_CHARGEE = "DTSMC";
	
	public final static String CODE_STATUT_CORRECTION = "CSC";
	
	public final static String CODE_STATUT_REJET_ARCHIVE = "CSRAR";
	
	public final static String CODE_STATUT_AU_FICHIER = "AFC";
	
	public final static String CODE_STATUT_SIM = "CSM";
	
	public final static String CODE_STATUT_ASS = "CSA";
	
	public final static String CODE_TYPE_RG_AVDG="AVDG";
	public final static String CODE_TYPE_RG_PCA="PCA";
	
	public final static String CODE_STATUT_A_VALIDER="VALIDER";
	
	public final static String CODE_STATUT_A_VALIDER_DRC_RG = "SECVDRC_RG";
	public final static String CODE_STATUT_A_VALIDER_DG_RG = "SECVDG_RG";
	public final static String CODE_STATUT_A_VALIDER_CA = "SECVDCA_RG";
	
	public final static String CODE_STATUT_VALIDER_DRC_RG = "SVDRC_RG";
	public final static String CODE_STATUT_VALIDER_DG_RG = "SVDG_RG";
	public final static String CODE_STATUT_VALIDER_CA  = "SVCA_RG";
	public final static String CODE_STATUT_SESSION_EN_COURS="EN COURS"; 
	public final static String CODE_STATUT_SESSION_ARCHIVEE="ARCHIVEE"; 
	public final static String CODE_STATUT_SESSION_EN_ATTENTEV="EN ATTENTEV";
	public final static String CODE_STATUT_SESSION_EN_ATTENTEC="EN ATTENTEC";
	public final static String CODE_STATUT_SESSION_VALIDEE="VALIDEE";
	public final static String CODE_STATUT_SESSION_AVALRMIS="A VALIDEE RT_MISS";
	public final static String CODE_STATUT_SESSION_VALRMIS="VALIDEE RT_MISS";
	
	public final static String CODE_STATUT_VALIDER_C1="SVAL1_C"; 
	public final static String CODE_STATUT_VALIDER_C2="SVAL2_C"; 
	public final static String CODE_STATUT_CONTESTTATION_C="SCONTES_C"; 
	public final static String CODE_STATUT_RDR_C="SRDSM_C"; 
	//public final static String CODE_STATUT_A_CORRIGER  = "a corriger";
	
	//***************CODE POSITION SERIGNE FALL********************
		public final static String CODE_POSITION_SUSPENDUS = "Suspend";
	 public final static String CODE_POSITION_RADIES_SIMPLE="Radie";
	 public final static String CODE_POSITION_ACTIF="Actif";
	
	public final static String POSITION_ASSURE_SALARIE = "Salarie";
	
	public final static String POSITION_ASSURE_CREE = "ass_cree";
	
	public final static String POSITION_ASSURE_SUSPENDU = "Suspendu";
	
	public final static String CODE_STATUT_MISSION_MODIFIER="MODIF"; 
	
	public final static String POSITION_EMPLOI_ACTIF = "Emploi_Actif";
	
	public final static String POSITION_EMPLOI_INACTIF = "Emploi_Inactif";
	
	public final static Integer AGE_MINIMUM_ASSURE = 16;

	public final static String TYPE_DEMANDE_ASSURE_VOLONTAIRE = "IAV";

	public final static String TYPE_ASSURE = "Assure";

	public final static String TYPE_ASSURE_VOLONTAIRE = "Volontaire";

	public final static String TYPE_TRAVAILLEUR = "Travailleur";

	public final static String CODE_REGIME_ASSURE_VOLONTAIRE = "V";
	
	//**********************************Position employeur à ne pas changer ***************************
	
	public final static String POSITION_SUSPENDU_SIMP= "SuspendSimple";
	
	public final static String POSITION_SUSPENDU_COMPT= "SuspendComptable";

	public final static String POSITION_RADIE_SIMP = "RadieSimple";
	
	public final static String POSITION_RADIE_COMPT = "RadieComptable";

	public final static String POSITION_ACTIF = "Actif";

	public final static String POSITION_CREE = "Crée";

	public final static String POSITION_INACTIF = "Inactif";

	//public final static String POSITION_RADIE = "Radie";
	
	public final static String POSITION_SIMILAIRE = "Similaire";
	
	public final static String POSITION_RETRAITE = "Retraite";
	
	public final static String POSITION_DECEDE = "Decede";
	
	public final static String POSITION_BLOQUER = "Bloquer";
	
	public final static String CODE_NOTIF_SUSP_SIMP = "NCPSS";
	
	public final static String CODE_NOTIF_PREST_MAT = "NPM";
	
	public final static String CODE_NOTIF_SUSP_COMP = "NCPSC";
	
	public final static String CODE_NOTIF_RAD_SIMP = "NCPRS";
	
	public final static String CODE_NOTIF_RAD_COMP = "NCPRC";
	
	public final static String CODE_NOTIF_RAISON_SOCIAL = "NCRS";
	
	public final static String CODE_MIS_EN_ENQUETE = "EN COURS";
	public final static String CODE_BON_VALIDATION_CARRIERE = "BPVC";

	public final static String CHEMIN_PHOTOS_ASSURES = "C:/photos/assures/";
	public final static String CHEMIN_SIGNATURES = "D:/signature/";
	public final static String CHEMIN_PHOTOS_MEMBRES = "C:/photos/membres/";
	public final static String COD_TYPE_BENEF_ASSURE = "ASSURE";
	public final static String CHEMIN_PHOTOS_BENEF = "F:/Photos/benef/";
	public final static String CHEMIN_FICHIER_VIREMENT = "E:/";
	public final static Integer TAUX_DEDUCTION=30;
	
	
	//**********************************Position DPT à ne pas changer ***************************
	
		public final static String POSITION_DPT_SUSPENDU= "S";
		
		public final static String POSITION_DPT_ACTIF= "A";

		public final static String POSITION_DPT_ATTENTE= "E";

		public final static String POSITION_DPT_RADIE = "R";
		
		public final static String POSITION_DPT_CREATION = "C";
		
		public final static String POSITION_DPT_PAYE = "P";
		
		public final static String POSITION_DPT_EN_ENQUETE = "EQ";
//***********************************Mode de paiement****************************************************** juldace
	public final static String CODE_MODE_PAIEMENT_VIR = "VIR";
	public final static String CODE_MODE_PAIEMENT_BANC = "BANC";
	public final static String CODE_MODE_PAIEMENT_CHQ = "CHQ";
	public final static String CODE_MODE_PAIEMENT_EPAIEMENT = "EPAIEMENT";
	public final static String CODE_MODE_PAIEMENT_ESP = "1";
	public final static String CODE_MODE_PAIEMENT_BORDEREAU = "BORDEREAU";
	//***********************************Motif de changement****************************************************** juldace
	public final static String CODE_MOTIF_CHGMNTBENEF_RDD = "RDD";
	/**
	 * longueur NBR_DE_MOIS.
	 */

	public final static int TAILLE_MAX_NB_CPT_GEN = 6;

	public final static int TAILLE_MAX_NB_CPT_AUX = 15;
	
	public final static int AGE_LIMITE_POUR_PRESTATION = 16;
	
	public final static String RAPPEL_PAIEMENT = "RPA";
	
	public final static String PRELEVEMENT_ASSURANCE_MALADIE = "PAM";
	
	public final static String INDEMNITE_JOURNALIERE_RP = "IJRP";
	
	public final static String PREST_REMB_FRAIS_RP = "RFATMP";
	

	public final static String ORGANISME_PAYEUR_DIRECTE_SOCIETE = "DIR_SOC";
	/**
	 * les constante unité de périodes
	 */
	public final static String UNITE_PERIODE_MOIS = "M";
	
	public final static String UNITE_PERIODE_TRIMESTRE = "T";
	
	public final static String UNITE_PERIODE_BIMESTRE = "B";
	
	public final static String UNITE_PERIODE_ANNE = "A";
	/**
	 * @return the codeStatutSessionEnCours
	 */
	public static String getCodeStatutSessionEnCours() {
		return CODE_STATUT_SESSION_EN_COURS;
	}

	/**
	 * @return the codeStatutSessionArchivee
	 */
	public static String getCodeStatutSessionArchivee() {
		return CODE_STATUT_SESSION_ARCHIVEE;
	}

	

	/**
	 * @return the codeStatutSessionValidee
	 */
	public static String getCodeStatutSessionValidee() {
		return CODE_STATUT_SESSION_VALIDEE;
	}

	private final static String SOUS_COMPTE_GENERAL_SIMPLE = "general";
	
	private final static String SOUS_COMPTE_GENERAL_COLLECTIF = "collectif";
	
	private final static String SOUS_COMPTE_GENERAL_VENTILLABLE = "ventillable";

	private final static String SOUS_COMPTE_AUXILIAIRE = "aux";
	
    private final static String CODE_SEXE_MASCULIN="H";
	
	private final static String CODE_SEXE_FEMININ="F";

	public final static Integer ID_TYPE_BENEFICIAIRE_ASSURE = 1;

	public final static Integer ID_TYPE_BENEFICIAIRE_MEMBRE = 2;
	public final static String ETAPE_DEBUT = "DEB";

	public final static String ETAPE_FIN = "FIN";
	/****CODE LANGUE*****************************/
	
	public final static String CODE_LANGUE_FR = "FR";

	/** Comptabilite Ndir */
	public final static String CODE_CPT_COTISANT = "Cpt cotisant";
	public final static String NOM_CAISSE_INTERM = "Caisse intermédiaire";

	public final static String CAISSE = "CAISSE NATIONALE DE SECURITE SOCIALE";

	public final static String SIEGE = "DIRECTION DU RECOUVREMENT ET DU PRECONTENTIEUX";

	public final static String SIEGE1 = "REPUBLIQUE GABONAISE";
	public final static String SIEGE2 = "=======BP 134 LIBREVILLE   TEL 74-67-41======";

	public final static String CODENE = "INE";
	public final static String CODECESSATION = "CCT";

	/********************* NDIR CODE VARIABLE COMPTE ********************/
	public final static String COD_VC_CT = "VC_CT";// Cpt cotisant
	public final static String COD_VC_AV = "VC_AV";// Cpt avoir
	public final static String COD_VC_CSE = "VC_CSE";// Cpt caisse espece
	public final static String COD_VC_CSC = "VC_CSC";// Cpt caisse cheque
	public final static String COD_VC_CSI_ES = "VC_CSI_ES";// Cpt caisse
	public final static String COD_VC_PAY_DROIT= "VC_PAY_DROIT";// compte paiement droit
															// intermediaire
															// Espece
	public final static String COD_VC_PF = "VC_PF";// Cpt caisse
	public final static String COD_VC_PVID = "VC_PVID";// Cpt caisse
	public final static String COD_VC_RP = "VC_RP";// Cpt caisse
	public final static String COD_VC_CNAMGS = "VCCNAMGS";// Cpt caisse
	public final static String COD_SEQ_CAISS_INT = "CSI";// sequence
															// indentifiant la
															// caisse
															// intermediaire
	public final static String COD_VC_PEN_PF = "VC_PEN_PF";// Cpt caisse
	public final static String COD_VC_PEN_PVID = "VC_PEN_PVID";// Cpt caisse
	public final static String COD_VC_PEN_RP = "VC_PEN_RP";// Cpt caisse
	public final static String COD_VC_PEN_PAY_TRD = "VC_PEN_PAY_TRD";// Cpt caisse
	public final static String COD_VC_PEN_DEP_TRD = "VC_PEN_DEP_TRD";// Cpt caisse
	public final static String COD_VC_PEN_CNAMGS = "VC_PEN_CNAMGS";// Cpt caisse
	public final static String COD_VC_PEN_TTL_CNSS = "VC_PEN_TTL_CNSS";// Cpt
	public final static String COD_VC_RG = "VC_RG"; 
																		// caisse
	public final static Integer COD_ANNULATION = 2;// valeur de l'annulation
	public final static Integer COD_DECLENCHEMENT = 1;// valeur de l'annulation
	/********************* NDIR CODE CONTEXTE COMPTE ********************/
	public final static String COD_CC_EMP = "CC_EMP";// employeur
	public final static String COD_CC_CS = "CC_CS";// caisse
	public final static String COD_CC_CSINT = "CC_CSINT";// caisse
	public final static String COD_CC_AG = "CC_AG";// caisse
	public final static String COD_CC_INST = "CC_INST";// caisse
	public final static String COD_CC_PD = "CC_PD";// caisse

	/********************* NDIR CODE NATURE OPERATION ********************/

	public final static String COD_DEB_APEL_COTISATION = "DEB_APEL";

	public final static String COD_FIN_APEL_COTISATION = "FIN_APEL";

	public final static String COD_DEB_DTS = "DEB_DTS";

	public final static String COD_FIN_DTS = "FIN_DTS";

	public final static String COD_DEB_PAYMNT = "DEB_PAY";

	public final static String COD_FIN_PAYMNT = "FIN_PAY";

	public final static String COD_DEB_PEN_DCL = "DEB_PEN_DCL";

	public final static String COD_DEB_PEN_PAY = "DEB_PEN_PAY";
	

	public final static String CARRIERE = "Demande de Reconstruction de Carriere";

	// *********************AGENCE NIVEAU ETABLISSEMENT***********************\\
	public final static String COD_AGENCE = "AGENCE";
	public final static String COD_SIEGE = "SIEGE";
	
	
	public final static String COD_ENQ = "ENQ";
	
	public final static String COD_DELEGATION = "DELEGATION";
	public final static String COD_CENTRE = "CENTRE";

	/********************* NDIR CODE VARIABLE MONTANT ********************/
	public final static String COD_VM_CT_DTS = "VM_DTS";
	public final static String COD_VM_PAY_DTS = "VM_PAY_DTS";
	public final static String COD_VM_TAX_DOF = "VM_TAX_DOF_DCL";
	public final static String COD_VM_PEN_DCL = "VM_PEN_DCL";
	public final static String COD_VM_PEN_PAY = "VM_PEN_PAY";
	public final static String COD_VM_DTS_DCL = "VM_DTS_DCL";
	public final static String COD_VM_DTS_PAY = "VM_DTS_PAY";
	public final static String COD_VM_PF = "VM_PF";
	public final static String COD_VM_PVID = "VM_PVID";
	public final static String COD_VM_PRP = "VM_RP";
	public final static String COD_VM_CNAMGS = "VM_CNAMGS";
	public final static String COD_VM_CNSS = "VM_CNSS";
	public final static String COD_VM_PASSFONDCS = "VM_PASSFONDCS";// passation de fond caisse
	public final static String COD_VM_RECEPFONDCI = "VM_RECEPFONDCI";// reception de fond caisse intermediaire
	public final static String COD_VM_PASSFONDCI = "VM_PASSFONDCI";// passation de fond caisse intermediaire
	public final static String COD_VM_RECEPFONDCS = "VM_RECEPFONDCS";// reception caisse
	public final static String COD_VM_PN_DLC = "VM_PN_DCL";// déclaration penalité
	public final static String COD_VM_AVOIR = "VM_AVOIR";// Avoir cotisant
	public final static String COD_VM_PN_PAY = "VM_PN_PAY";// paiement caisse
	public final static String COD_VM_PN_DEP_TARD = "VM_PN_DEP_TARD";// penalité depot tardif
	public final static String COD_VM_PN_PAY_TARD = "VM_PN_PAY_TARD";// paiement tardif
	public final static String COD_VM_TTL_PN_CNSS = "VM_TTL_PN_CNSS";// Total des pénalité pour la cnss
	public final static String COD_VM_TTL_PN_CNAMGS = "VM_TTL_PN_CNAMGS";// Total des pénalité pour la cnamgs
	public final static String COD_VM_PN_PF = "VM_PN_PF";// penalité pf
	public final static String COD_VM_PN_PVID = "VM_PN_PVID";// penalité pvid
	public final static String COD_VM_PN_RP = "VM_PN_RP";// penalité rp
	public final static String COD_VM_PN_CNAMGS = "VM_PN_CNAMGS";// penalité
	public final static String COD_VM_RG = "VM_RG"; // Remise gracieuse
	public final static String COD_VM_DROIT = "VM_DROIT";
																	// cnamgs
	/**************** NDIR FIN VARIABLE MONTANT ****************************/

	/********************* NDIR CODE NATURE OPERATION ********************/
	public final static String COD_NATOP_PASSFOND = "NATOP_PASSFOND";
	public final static String COD_NATOP_RECEPFOND = "NATOP_RECEPFOND";
	public final static String COD_NATOP_DTS_DCL = "NATOP_DTS_DCL";
	public final static String COD_NATOP_DTS_PAY = "NATOP_DTS_PAY";
	public final static String COD_NATOP_DTS_ANNULE = "NATOP_DTS_ANNULE";
	public final static String COD_NATOP_PEN_DCL = "NATOP_PEN_DCL";
	public final static String COD_NATOP_PEN_PAY = "NATOP_PEN_PAY";
	public final static String COD_NATOP_ANNULEFOND = "NATOP_ANNULEFOND";
	public final static String COD_NATOP_ENC_ES_CT = "NATOP_ENC_ES_CT";
	public final static String COD_NATOP_ENC_CH_CT = "NATOP_ENC_CH_CT";
	public final static String COD_NATOP_RG = "NATOP_RG";
	public final static String COD_NATOP_PD = "NATOP_PD";
	
	/**************** NDIR FIN NATURE OPERATION ****************************/

	public final static String REMISE_GRACIEUSE = "Demande Remise Gracieuse";
	public final static String MORATOIRE = "Demande de Moratoire";
	public final static String IMMAT_ASSURE = "Demande Immatriculation Travailleur";
	public final static String IMMAT_EMP = "Demande Immatriculaion Employeur";
	public final static String IMMAT_AYANT_DROIT = "Demande Immatriculation Membre Famllial";
	public final static String IMMAT_ASSURE_VOLOLONTAIRE = "Demande Assurance volontaire ";
	public final static String CODE_SUSPENSION_EMP = "SE";
	public final static String CODE_RADIATION_EMP = "RE";

	public final static String CODE_TYPE_EMPLOYEUR_PP = "PP";
	public final static String COD_DEBIT = "D";
	public final static String COD_CREDIT = "C";

	public final static String CONCERNE_EMP = "emp";
	public final static String CONCERNE_ASSURE = "ass";
	public final static String CONCERNE_GRAPPE = "CG";

	public final static String CODE_CHANGEMENTS_DIVERS = "CCT";
	public final static String CODE_IMMAT_EMP = "IME";
	public final static String CODE_IMMAT_CESSATION = "CA";
	public final static String CODE_IMMAT_CENTRE = "CCE";
	public final static String CODE_IMMAT_EMBAUCHE = "NEM";
	public final static String CODE_IMMAT_ASSURE = "IMA";
	public final static String CODE_CHANG_RAISON_SOCIALE = "CRS";
	public final static String CODE_IMMAT_DROIT = "IMG";
	public final static String CODE_IMMAT_VOLONTAIRE = "IAV";
	public final static String CODE_CONSTESTATION = "CEE";
	public final static String CODE_REDRESSEMENT = "CRD";
	public final static String CODE_IMMAT_CARRIERE = "ICR";
	public final static String CODE_IMMAT_POSITION = "CPE";

	public final static String CODE_SITUATION_COMPTE = "SCP";
	
	public final static String CODE_IMMAT_RADIATION = "RE";
	
	public final static String CODE_IMMAT_REPRISE = "REP";
	
	public final static String CODE_ATTESTATION_SOUMISSION = "ATS";
	public final static String CODE_IMMAT_MORATOIRE = "MO";
	public final static String CODE_IMMAT_REMISE = "RG";

	/* ********************** CONSTANTES STATISTIQUE*********************************** */
	public final static Integer VARIATION_CATEGORIE = 20;
	public final static Integer AGE_MIN_REPARTITION = 20;
	public final static Integer AGE_MAX_REPARTITION = 60;
	public final static Integer INTERVALLE_REPARTITION  = 5;
	public final static Integer AGE_MIN_REPARTITION_DUREE_SEXE = 5;
	public final static Integer AGE_MAX_REPARTITION_DUREE_SEXE = 40;
	
	public final static String CODE_CATEGORIE_REGIME_ASSURES_VOLONTAIRES = "AV";
	public final static Integer ANNEE_MAX_EVOLUTION = 0;
	public final static Integer ANNEE_MIN_EVOLUTION = -4;
	public final static Integer ANNEE_INTERVALLE_EVOLUTION = 1;
	public final static String CODE_AFFILIATION= "DFA";
	public final static String CODE_AFFILIATION1= "ATF";
	
	
	
	public final static String CODE_NON_AFFILIATION = "AFS";
	
	public final static String CODE_CHANGEMENT_ADRESSE= "CAB";
	
	public final static String CODE_DOMAINE_PENSION= "PVID";
	
	public final static String CODE_DOMAINE_RP= "RP";	
	
	public final static String CODE_CATEGORIE_REGIME_SECTREUR_PRIVE = "SPR";
	
	public final static String CODE_CATEGORIE_REGIME_SECTREUR_PUBLIC = "SPU";
	
	public final static String CODE_CATEGORIE_REGIME_GENS_MAISON = "GM";
	
	public final static String CODE_ACTIVITE_ECONOMIQUE_ENTREPRISE_PROPRETE_AUTRE = "A_EPA";
	public final static String CODE_ACTIVITE_ECONOMIQUE_BANQUE_ETABLISSEMENT_FINANCIERES = "A_BEF";
	public final static String CODE_ACTIVITE_ECONOMIQUE_AGRICULTURE = "A_A";
	public final static String CODE_ACTIVITE_ECONOMIQUE_EXPLOITATION_FORESTIERES = "A_EF";
	public final static String CODE_ACTIVITE_ECONOMIQUE_ELEVAGE = "A_EL";
	public final static String CODE_ACTIVITE_ECONOMIQUE_BTP = "A_BTP";
	public final static String CODE_POSITION_EFFECTIFS_DB  = "PE_A_DB";
	public final static String CODE_POSITION_EFFECTIFS_FIN = "PE_A_FIN";
	public final static String CODE_POSITION_NOUV_IMMAT="PE_NI";
	
	public final static String CODE_STATUT_AYANTDROIT_MARIAGE = "SA_MR";
	
	public final static String CODE_DEDUCTION_PLUS="Plus";
	public final static String CODE_DEDUCTION_MOINS="Moins";
	/* ********************************************************* */
	
	public final static String DUREE_SUSPENSION_PAIEMENT = "1";
	public final static String DUREE_RADIATION_PAIEMENT = "2";
	public final static String REIMPUTATION_SUSPENDU = "3";
	public final static String REIMPUTATION_RADIE = "4";
	
	public static boolean EXISTE_REMISE_GRACIEUSE_PARCELLE = true;
	public static String RecepFond;
	
	public final static String CODE_PAYS_DEFAUT = "GAB";
	
	public final static String CODE_POSITION_SUSPENDUSDPT = "Suspendu";
	
	

	public final static String CODE_STATUT_EN_COURS_DERECONSTITUTION = "SECR";
	
	public final static int CODE_DUREE_MARIAGE= 12;
	
	public static enum ExportOption {
		PDF, HTML, EXCEL, RTF
	};

	public static enum ContexteModePaiement {
		CAISSE, BANQUE, BORDEREAU
	};
	
	public static enum EtatCotisation {
		  NOUV_MOR_PR, NOUV_MOR_DT, MOR_COURS_PR, MOR_COURS_DT, MOR_REGL_PR, MOR_REGL_DT,Redressmnt, Declare, Forfait
	};
		 
	public static enum TypeContenuEmail {
		TEXT, HTML
	};

	public static enum TypePayeur  {Regime, Cotisant, MaisonMere};
	
	public static final String SESSION_LIST_KEY = "com.chaka.web.sessionList";
	
	public static java.util.List<Utilisateur> listAllUsersCoonected = new ArrayList<Utilisateur>();

	/**
	 * @return the longueurLibelle
	 */
	public static int getLongueurLibelle() {
		return LONGUEUR_LIBELLE;
	}

	/**
	 * @return the longueurLibelleCourt
	 */
	public static int getLongueurLibelleCourt() {
		return LONGUEUR_LIBELLE_COURT;
	}

	/**
	 * @return the longueurCommentaire
	 */
	public static int getLongueurCommentaire() {
		return LONGUEUR_COMMENTAIRE;
	}

	/**
	 * @return the longueurNationnalite
	 */
	public static int getLongueurNationnalite() {
		return LONGUEUR_NATIONNALITE;
	}

	/**
	 * @return the longueur50
	 */
	public static int getLongueur50() {
		return LONGUEUR_50;
	}

	/**
	 * @return the longueurTelephone
	 */
	public static int getLongueurTelephone() {
		return LONGUEUR_TELEPHONE;
	}

	/**
	 * @return the longueurCode
	 */
	public static int getLongueurCode() {
		return LONGUEUR_CODE;
	}

	/**
	 * @return the longueurMatricule
	 */
	public static int getLongueurMatricule() {
		return LONGUEUR_MATRICULE;
	}

	/**
	 * @return the longueurAdresse
	 */
	public static int getLongueurAdresse() {
		return LONGUEUR_ADRESSE;
	}

	/**
	 * @return the longueurVille
	 */
	public static int getLongueurVille() {
		return LONGUEUR_VILLE;
	}

	/**
	 * @return the longueurEmail
	 */
	public static int getLongueurEmail() {
		return LONGUEUR_EMAIL;
	}

	/**
	 * @return the max
	 */
	public static int getMax() {
		return MAX;
	}

	/**
	 * @return the nbrDeMois
	 */
	public static int getNbrDeMois() {
		return NBR_DE_MOIS;
	}

	/**
	 * @return the personnePhysique
	 */
	public static String getPersonnePhysique() {
		return personne_physique;
	}

	/**
	 * @return the personneMorale
	 */
	public static String getPersonneMorale() {
		return personne_morale;
	}

	/**
	 * @return the idStatutAttenteCreation
	 */
	public static Long getIdStatutAttenteCreation() {
		return ID_STATUT_ATTENTE_CREATION;
	}

	/**
	 * @return the idStatutValidation
	 */
	public static Long getIdStatutValidation() {
		return ID_STATUT_VALIDATION;
	}

	/**
	 * @return the idStatutAnnulation
	 */
	public static Long getIdStatutAnnulation() {
		return ID_STATUT_ANNULATION;
	}

	/**
	 * @return the codeDeductionPlus
	 */
	public static String getCodeDeductionPlus() {
		return CODE_DEDUCTION_PLUS;
	}

	/**
	 * @return the codeDeductionMoins
	 */
	public static String getCodeDeductionMoins() {
		return CODE_DEDUCTION_MOINS;
	}

	/**
	 * @return the idStatutAttenteModification
	 */
	public static Long getIdStatutAttenteModification() {
		return ID_STATUT_ATTENTE_MODIFICATION;
	}

	/**
	 * @return the idTypeAssureVolontaire
	 */
	public static Integer getIdTypeAssureVolontaire() {
		return ID_TYPE_ASSURE_VOLONTAIRE;
	}

	/**
	 * @return the idTypeAssureEmployeur
	 */
	public static Integer getIdTypeAssureEmployeur() {
		return ID_TYPE_ASSURE_EMPLOYEUR;
	}

	/**
	 * @return the codeAllocation
	 */
	public static String getCodeAllocation() {
		return CODE_ALLOCATION;
	}

	

	/**
	 * @return the sousCompteAuxiliaire
	 */
	public static String getSousCompteAuxiliaire() {
		return SOUS_COMPTE_AUXILIAIRE;
	}

	/**
	 * @return the codeGrossesse
	 */
	public static String getCodeGrossesse() {
		return CODE_GROSSESSE;
	}

	/**
	 * @return the codeAp1
	 */
	public static String getCodeAp1() {
		return CODE_AP1;
	}

	/**
	 * @return the codeAp2
	 */
	public static String getCodeAp2() {
		return CODE_AP2;
	}

	/**
	 * @return the codeMaternite
	 */
	public static String getCodeMaternite() {
		return CODE_MATERNITE;
	}

	/**
	 * @return the codeIndamniteJournaliere
	 */
	public static String getCodeIndamniteJournaliere() {
		return CODE_INDAMNITE_JOURNALIERE;
	}

	/**
	 * @return the decocherTous
	 */
	public static String getDecocherTous() {
		return DECOCHER_TOUS;
	}

	/**
	 * @return the cocherTous
	 */
	public static String getCocherTous() {
		return COCHER_TOUS;
	}

	/**
	 * @return the longueurTinyblob
	 */
	public static Integer getLongueurTinyblob() {
		return LONGUEUR_TINYBLOB;
	}

	/**
	 * @return the idLienParenteEnfant
	 */
	public static Long getIdLienParenteEnfant() {
		return ID_LIEN_PARENTE_ENFANT;
	}

	/**
	 * @return the idLienParenteConjoint
	 */
	public static Long getIdLienParenteConjoint() {
		return ID_LIEN_PARENTE_CONJOINT;
	}

	/**
	 * @return the longueurBlob
	 */
	public static int getLongueurBlob() {
		return LONGUEUR_BLOB;
	}

	/**
	 * @return the longueurMediumblob
	 */
	public static int getLongueurMediumblob() {
		return LONGUEUR_MEDIUMBLOB;
	}

	/**
	 * @return the longueurLongblob
	 */
	public static int getLongueurLongblob() {
		return LONGUEUR_LONGBLOB;
	}

	/**
	 * @return the debuttrimestregestion
	 */
	public static int getDebuttrimestregestion() {
		return DebutTrimestreGestion;
	}

	/**
	 * @return the delaiPaiement
	 */
	public static int getDelaiPaiement() {
		return DELAI_PAIEMENT;
	}

	/**
	 * @return the codeTrimestre1
	 */
	public static String getCodeTrimestre1() {
		return CODE_TRIMESTRE_1;
	}

	/**
	 * @return the codeTrimestre2
	 */
	public static String getCodeTrimestre2() {
		return CODE_TRIMESTRE_2;
	}

	/**
	 * @return the codeTrimestre3
	 */
	public static String getCodeTrimestre3() {
		return CODE_TRIMESTRE_3;
	}

	/**
	 * @return the codeTrimestre4
	 */
	public static String getCodeTrimestre4() {
		return CODE_TRIMESTRE_4;
	}

	/**
	 * @return the idTypeBeneficiaireAssure
	 */
	public static Integer getIdTypeBeneficiaireAssure() {
		return ID_TYPE_BENEFICIAIRE_ASSURE;
	}

	/**
	 * @return the idTypeBeneficiaireMembre
	 */
	public static Integer getIdTypeBeneficiaireMembre() {
		return ID_TYPE_BENEFICIAIRE_MEMBRE;
	}

	/**
	 * @return the codeFraisMedicaux
	 */
	public static String getCodeFraisMedicaux() {
		return CODE_FRAIS_MEDICAUX;
	}

	/**
	 * @return the codeActionModofication
	 */
	public static String getCodeActionModofication() {
		return CODE_ACTION_MODOFICATION;
	}

	/**
	 * @return the codeActionCreation
	 */
	public static String getCodeActionCreation() {
		return CODE_ACTION_CREATION;
	}

	/**
	 * @return the codeActionSuppression
	 */
	public static String getCodeActionSuppression() {
		return CODE_ACTION_SUPPRESSION;
	}

	/**
	 * @return the codeActionValidation
	 */
	public static String getCodeActionValidation() {
		return CODE_ACTION_VALIDATION;
	}

	/**
	 * @return the codeActionAnnulation
	 */
	public static String getCodeActionAnnulation() {
		return CODE_ACTION_ANNULATION;
	}

	/**
	 * @return the codeTableGrappe
	 */
	public static String getCodeTableGrappe() {
		return CODE_TABLE_GRAPPE;
	}

	/**
	 * @return the codeTableAssure
	 */
	public static String getCodeTableAssure() {
		return CODE_TABLE_ASSURE;
	}

	/**
	 * @return the codeTableEmployeur
	 */
	public static String getCodeTableEmployeur() {
		return CODE_TABLE_EMPLOYEUR;
	}

	/**
	 * @return the codeTableCompteIndiv
	 */
	public static String getCodeTableCompteIndiv() {
		return CODE_TABLE_COMPTE_INDIV;
	}

	/**
	 * @return the codeCiviliteMr
	 */
	public static Integer getCodeCiviliteMr() {
		return CODE_CIVILITE_MR;
	}

	/**
	 * @return the codeCiviliteMme
	 */
	public static Integer getCodeCiviliteMme() {
		return CODE_CIVILITE_MME;
	}

	/**
	 * @return the codeCiviliteMlle
	 */
	public static Integer getCodeCiviliteMlle() {
		return CODE_CIVILITE_MLLE;
	}

	/**
	 * @return the actionModofication
	 */

	/**
	 * @return the codeDirection
	 */
	public static int getCodeDirection() {
		return DirectionID;
	}

	/**
	 * @return the codeDivision
	 */
	public static int getCodeDivision() {
		return DivisionID;
	}

	/**
	 * @return the codeService
	 */
	public static int getCodeService() {
		return ServiceID;
	}

	/**
	 * @return the codVmPayDts
	 */
	public static String getCodVmPayDts() {
		return COD_VM_PAY_DTS;
	}

	/**
	 * @return the codVmTaxDof
	 */
	public static String getCodVmTaxDof() {
		return COD_VM_TAX_DOF;
	}

	/**
	 * @return the codVmPenDcl
	 */
	public static String getCodVmPenDcl() {
		return COD_VM_PEN_DCL;
	}

	/**
	 * @return the codVmPenPay
	 */
	public static String getCodVmPenPay() {
		return COD_VM_PEN_PAY;
	}

	/**
	 * @return the codVmPf
	 */
	public static String getCodVmPf() {
		return COD_VM_PF;
	}

	/**
	 * @return the codVmPvid
	 */
	public static String getCodVmPvid() {
		return COD_VM_PVID;
	}

	/**
	 * @return the codVmPrp
	 */
	public static String getCodVmPrp() {
		return COD_VM_PRP;
	}

	

	/**
	 * @return the codVcCt
	 */
	public static String getCodVcCt() {
		return COD_VC_CT;
	}

	/**
	 * @return the codVcAv
	 */
	public static String getCodVcAv() {
		return COD_VC_AV;
	}

	/**
	 * @return the codVcCs
	 */
	public static String getCodVcCse() {
		return COD_VC_CSE;
	}

	/**
	 * @return the codVcPf
	 */
	public static String getCodVcPf() {
		return COD_VC_PF;
	}

	/**
	 * @return the codVcPvid
	 */
	public static String getCodVcPvid() {
		return COD_VC_PVID;
	}

	/**
	 * @return the codVcRp
	 */
	public static String getCodVcRp() {
		return COD_VC_RP;
	}

	/**
	 * @return the codVcCnamgs
	 */
	public static String getCodVcCnamgs() {
		return COD_VC_CNAMGS;
	}

	/**
	 * @return the codCcEmp
	 */
	public static String getCodCcEmp() {
		return COD_CC_EMP;
	}

	/**
	 * @return the codCcCs
	 */
	public static String getCodCcCs() {
		return COD_CC_CS;
	}

	/**
	 * @return the codNatopDtsDcl
	 */
	public static String getCodNatopDtsDcl() {
		return COD_NATOP_DTS_DCL;
	}

	/**
	 * @return the codNatopDtsPay
	 */
	public static String getCodNatopDtsPay() {
		return COD_NATOP_DTS_PAY;
	}

	/**
	 * @return the codNatopPenDcl
	 */
	public static String getCodNatopPenDcl() {
		return COD_NATOP_PEN_DCL;
	}

	/**
	 * @return the eXISTE_REMISE_GRACIEUSE_PARCELLE
	 */
	public boolean isEXISTE_REMISE_GRACIEUSE_PARCELLE() {
		return EXISTE_REMISE_GRACIEUSE_PARCELLE;
	}

	/**
	 * @param eXISTE_REMISE_GRACIEUSE_PARCELLE
	 *            the eXISTE_REMISE_GRACIEUSE_PARCELLE to set
	 */
	public void setEXISTE_REMISE_GRACIEUSE_PARCELLE(
			boolean eXISTE_REMISE_GRACIEUSE_PARCELLE) {
		EXISTE_REMISE_GRACIEUSE_PARCELLE = eXISTE_REMISE_GRACIEUSE_PARCELLE;
	}

	/**
	 * @return the codCcCsint
	 */
	public static String getCodCcCsint() {
		return COD_CC_CSINT;
	}

	/**
	 * @return the codVcCsiEs
	 */
	public static String getCodVcCsiEs() {
		return COD_VC_CSI_ES;
	}

	/**
	 * @return the codeStatut
	 */
	public static String getCodeStatut() {
		return code_statut;
	}

	/**
	 * @return the sousCompteGeneralSimple
	 */
	public static String getSousCompteGeneralSimple() {
		return SOUS_COMPTE_GENERAL_SIMPLE;
	}

	/**
	 * @return the codeStatutSessionAvalrmis
	 */
	public static String getCodeStatutSessionAvalrmis() {
		return CODE_STATUT_SESSION_AVALRMIS;
	}

	/**
	 * @return the codeStatutSessionValrmis
	 */
	public static String getCodeStatutSessionValrmis() {
		return CODE_STATUT_SESSION_VALRMIS;
	}

	/**
	 * @return the sousCompteGeneralCollectif
	 */
	public static String getSousCompteGeneralCollectif() {
		return SOUS_COMPTE_GENERAL_COLLECTIF;
	}

	/**
	 * @return the sousCompteGeneralVentillable
	 */
	public static String getSousCompteGeneralVentillable() {
		return SOUS_COMPTE_GENERAL_VENTILLABLE;
	}

	public static String getCodeSexeMasculin() {
		return CODE_SEXE_MASCULIN;
	}

	public static String getCodeSexeFeminin() {
		return CODE_SEXE_FEMININ;
	}

	/**
	 * @return the codeStatutSessionEnAttentev
	 */
	public static String getCodeStatutSessionEnAttentev() {
		return CODE_STATUT_SESSION_EN_ATTENTEV;
	}

	/**
	 * @return the codeStatutSessionEnAttentec
	 */
	public static String getCodeStatutSessionEnAttentec() {
		return CODE_STATUT_SESSION_EN_ATTENTEC;
	}

}