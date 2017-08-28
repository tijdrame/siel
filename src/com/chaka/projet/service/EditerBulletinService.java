package com.chaka.projet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.chaka.common.utils.ChakaUtils;
import com.chaka.commons.service.FilePrintService;
import com.chaka.constantes.Constantes;
import com.chaka.projet.entity.Utilisateur;
import com.tidiane.model.AnneeAcademique;
import com.tidiane.model.Bulletin;
import com.tidiane.model.Classe;
import com.tidiane.model.Cycle;
import com.tidiane.model.Semestre;

@Name("editerBulletinService")
@Scope(ScopeType.SESSION)
public class EditerBulletinService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@In
	private Session dataSource;

	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;
	
	ClasseService classeService = (ClasseService) Component.getInstance(ClasseService.class);
	
	BulletinService bulletinService = (BulletinService) Component.getInstance(BulletinService.class);
	InscriptionService inscriptionService = (InscriptionService) Component.getInstance(InscriptionService.class);
	@In 
	FacesMessages facesMessages;
	
	FilePrintService filePrintService = (FilePrintService)Component.getInstance(FilePrintService.class);
	
	
	private List<Bulletin> listBulletins = new ArrayList<Bulletin>();
    private Bulletin bulletin = new Bulletin();
    private Classe filtreClasse;
    private List<Classe> listClasses = new ArrayList<Classe>();
    private Cycle filtreCycle;
    private AnneeAcademique filtreAcademique;
    private Semestre filtreSemestre;
    private Boolean estSecondSem = false;
    
    public String versEditerBulletin()
	{
    	init();
		return "/pages/parametrage/editerBulletin.xhtml";
	}
    
    public List<Bulletin> findBulletin(){
        estSecondSem =false;
        if(filtreSemestre != null && filtreSemestre.getLibelleCourt().equals(Constantes.SECOND_SEMESTRE)) {
            estSecondSem = true;
            ChakaUtils.println("sec sem");
        }
        if(filtreClasse != null && filtreCycle != null && filtreAcademique != null&& filtreSemestre != null) {
            return listBulletins = bulletinService.findBulletins(filtreClasse, filtreSemestre, filtreAcademique);
        }else{
        	facesMessages.addToControlFromResourceBundle("erreurGenerique",
					"Tous les critères de recherche sont obligatoires!");
            return null;
        }
    }
    
    public void init(){
        bulletin = new Bulletin();
        filtreCycle = null;
        filtreClasse = null ;
        filtreAcademique =  null;
        filtreSemestre = null;
        listBulletins = new ArrayList<Bulletin>();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String imprimer(){
    	String nameJasper="";
        if(filtreSemestre.getLibelleCourt().equals(Constantes.PREMIER_SEMESTRE))nameJasper = "bulletinprems";
        else nameJasper = "bulletin";
		
		Map parametros = new HashMap();
        //parametros.put("dateDebut", dateDebut);
        //parametros.put("dateFin", dateFin);
		parametros.put("logo", null);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sc = (ServletContext) ec.getContext();
		String reportFile = sc.getRealPath("/report/school");
		ChakaUtils.println("sub rep="+reportFile);
		parametros.put("SUBREPORT_DIR", reportFile+"/");
		filePrintService.imprimer("school", nameJasper, parametros,
				listBulletins, utilisateur,Constantes.ExportOption.PDF);
		//for()
		return "";
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String imprim(Bulletin bull){
    	String nameJasper="";
        if(filtreSemestre.getLibelleCourt().equals(Constantes.PREMIER_SEMESTRE))nameJasper = "bulletinprems";
        else nameJasper = "bulletin";
		List<Bulletin> list = new ArrayList<Bulletin>();
		list.add(bull);
		Map parametros = new HashMap();
        //parametros.put("dateDebut", dateDebut);
        //parametros.put("dateFin", dateFin);
		parametros.put("logo", null);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sc = (ServletContext) ec.getContext();
		String reportFile = sc.getRealPath("/report/school");
		ChakaUtils.println("sub rep="+reportFile);
		parametros.put("SUBREPORT_DIR", reportFile+"/");
		filePrintService.imprimer("school", nameJasper, parametros,
				list, utilisateur,Constantes.ExportOption.PDF);
		//for()
		return "";
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
    
    public void consuler(Bulletin bull){
        bulletin = bulletinService.consuler(bull);
    }

    
	public List<Bulletin> getListBulletins() {
		return listBulletins;
	}
	public void setListBulletins(List<Bulletin> listBulletins) {
		this.listBulletins = listBulletins;
	}
	public Bulletin getBulletin() {
		return bulletin;
	}
	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}
	public Classe getFiltreClasse() {
		return filtreClasse;
	}
	public void setFiltreClasse(Classe filtreClasse) {
		this.filtreClasse = filtreClasse;
	}
	public List<Classe> getListClasses() {
		return listClasses;
	}
	public void setListClasses(List<Classe> listClasses) {
		this.listClasses = listClasses;
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
	public Boolean getEstSecondSem() {
		return estSecondSem;
	}
	public void setEstSecondSem(Boolean estSecondSem) {
		this.estSecondSem = estSecondSem;
	}

}
