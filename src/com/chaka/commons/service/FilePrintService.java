/**
 * 
 */
package com.chaka.commons.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.chaka.common.utils.ChakaUtils;
import com.chaka.constantes.Constantes;
import com.chaka.constantes.Constantes.ExportOption;
/*import com.chaka.parametrage.entity.fichier.SysFichier;
import com.chaka.parametrage.entity.fichier.SysSectionFichier;
import com.chaka.parametrage.entity.generaux.LienParente;*/
import com.chaka.projet.entity.Utilisateur;

/**
 * @author Gora
 *
 */
@Name("filePrintService")
@Scope(ScopeType.SESSION)
public class FilePrintService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 712785088206643892L;

	private Integer id = 1;

	
	private byte [] bytesContent;
	/**
	 * 
	 */
	public FilePrintService() {
		// TODO Auto-generated constructor stub
	}
	
	
    public void imprimer(String repertoire,String reportShortName, Map<String,Object> params, Collection  listeDonnees, Utilisateur utilisateur,ExportOption pdf){
    	id++;
    	if(Constantes.ExportOption.PDF.equals(pdf))
    	   bytesContent = ChakaUtils.chakaUtils.getContentBytesPdf(repertoire, reportShortName, params, listeDonnees, null, utilisateur);
    	else ChakaUtils.chakaUtils.generateOtherReport(repertoire, reportShortName, params, listeDonnees, null, utilisateur, pdf);
    	
    }
	
	public void paintPdf(OutputStream out,Object data) throws IOException {    	       	       	       	    	  	
  		if(bytesContent!=null)  						   			
			out.write(bytesContent);
  		  	
	}
	
	
	
	
	 /*public void ecrireFichierTri(List<LienParente> list, String nFichier,SysSectionFichier section){
		    String NomFichier = Constantes.CHEMIN_FICHIER_VIREMENT+nFichier;
		    ChakaUtils.println("#--------------------------------------------- Chemin =  "+Constantes.CHEMIN_FICHIER_VIREMENT+nFichier);
		  //  section.get
		    try{
		      PrintWriter out  = new PrintWriter(new FileWriter(NomFichier));
		      for (int i = 0; i < list.size(); i++)
		        out.println(  list.get(i).getLibelleCourt()+" " + list.get(i).getLibelle() +" ;");
		      out.close();
		    }
		    catch(Exception e){
		      e.printStackTrace();
		    }
	}*/
	 
	 /**
	  * 		 Créer un fichier excel

	  */
	/* @SuppressWarnings("deprecation")
	public void ecrireFichierExcel (List<LienParente> list){
        	 
        	 HSSFWorkbook wb = new HSSFWorkbook();
        	 FileOutputStream fileOut;
        	 HSSFSheet sheet = wb.createSheet("ma feuille");
        	 for (int i = 0; i < list.size(); i++){
        		 HSSFRow row = sheet.createRow(i);
        		 HSSFCell cell = row.createCell((short)0);
            	 cell.setCellValue(list.get(i).getLibelleCourt());
            	 row.createCell((short)1).setCellValue(list.get(i).getLibelle());
        	 }
 		       
        	
        	
        	 try {
        		 	fileOut = new FileOutputStream(Constantes.CHEMIN_FICHIER_VIREMENT+"monfichier.xls");
		        	 wb.write(fileOut);
		        	 fileOut.close();
		        	 } catch (FileNotFoundException e) {
		        	 e.printStackTrace();
		        	 } 
        	 catch (IOException e) {
        	 e.printStackTrace(); 
        	 }
		
		
       

	 }*/
	 
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}