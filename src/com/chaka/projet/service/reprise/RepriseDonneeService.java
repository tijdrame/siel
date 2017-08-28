package com.chaka.projet.service.reprise;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.chaka.projet.service.utils.ServiceUtils;

@Name("repriseDonneeService")
@Scope(ScopeType.SESSION)
public class RepriseDonneeService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2455665850183246397L;

	private byte[] data;

	private String nomPieceJointe;

	private String contentType;

	private String nomTable;

	private Integer nbFeuil;
	
	private Integer nbColDonnees;
	
	private Integer nbLigneDonnees;
	
	private Integer nbLigneDonneesFin;
	
	private Integer nbLigneLabel;
	
	private Integer nbColLabel;
	
	private Long idCodeAgence;
	
	private List<ExportErreur> erreurs;

	@In
	private Session dataSource;

	public void readXlsFile(){
		boolean erreur = false;
		Sheet sheet = null;
		erreurs = new ArrayList<ExportErreur>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Workbook workbook;
		try {
			if(data == null && (contentType == null || contentType.isEmpty())){
				FacesMessages.instance().addToControl("erreurGenerique", "L'insertion du fichier xl est obligatoire ! Insérer le fichier xl de l'export.");
				return;
			}
			workbook = Workbook.getWorkbook((new ByteArrayDataSource(data, contentType)).getInputStream());
			if(nbFeuil -1 <0 && nbFeuil > workbook.getNumberOfSheets()){
				FacesMessages.instance().addToControl("erreurGenerique", "Le numéro de la feuille doit être entre 1 et " + workbook.getNumberOfSheets());
				return;
			}
			sheet = workbook.getSheet(nbFeuil -1);
			if(nbColDonnees -1 <0 && nbColDonnees > sheet.getColumns()){
				FacesMessages.instance().addToControl("erreurGenerique", "La colonne à partir de laquelle les données commencent doit être entre 1 et " + sheet.getColumns());
				return;
			}
			
			if(nbColLabel -1 <0 && nbColLabel > sheet.getColumns()){
				FacesMessages.instance().addToControl("erreurGenerique", "La colonne des libellés doit être entre 1 et " + sheet.getColumns());
				return;
			}
			
			if(nbLigneDonnees -1 <0 && nbLigneDonnees > sheet.getRows()){
				FacesMessages.instance().addToControl("erreurGenerique", "La ligne à partir de laquelle les données commencent doit être entre 1 et " + sheet.getColumns());
				return;
			}
			
			
			if(nbLigneLabel -1 <0 && nbLigneLabel > sheet.getRows()){
				FacesMessages.instance().addToControl("erreurGenerique", "La ligne des libellés doit être entre 1 et " + sheet.getRows());
				return;
			}
			Integer nbLigne = null;
			if(nbLigneDonneesFin!=null){
				nbLigne = nbLigneDonneesFin;
			}else{
				nbLigne = sheet.getRows();
			}
			String sql=" values (";
			String nomCol = " (";
			for (int j = nbColDonnees-1 ; j < sheet.getColumns() ; j++) {
				Cell cell = sheet.getCell(j, nbLigneLabel-1);
				
				if(cell.getContents().contains("(")){
					nomCol += cell.getContents().substring(0,cell.getContents().indexOf("(")).trim();
				}else{
					nomCol += cell.getContents().trim();
				}
				if( j==sheet.getColumns()-1){
					sql += "?";
				}else{
					nomCol += ",";
					sql += "?,";
				}
			}
			if(idCodeAgence !=null && idCodeAgence>0){
				nomCol += ",idCodeAgence)";
				sql += ",?)";
			}else{
				nomCol += ")";
				sql += ")";
			}
			sql = "insert into "+ nomTable + nomCol +  sql;
			for (int i = nbLigneDonnees-1; i < nbLigne; i++) {
				Query query = this.dataSource.createSQLQuery(sql);
				int firstCol = nbColDonnees-1;
				int k =0;
				for (int j = firstCol; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
					if (type == CellType.LABEL || type == CellType.STRING_FORMULA) {
						query.setString(j-firstCol,cell.getContents().trim());
					}else if (type == CellType.NUMBER || type == CellType.NUMBER_FORMULA) {
						NumberCell numberCell = (NumberCell) cell;
						Double value = numberCell.getValue();
						if((value - value.intValue()) == 0d)
						{
							query.setLong(j-firstCol, Math.round(numberCell.getValue()));
						}else{
							query.setDouble(j-firstCol,ServiceUtils.arrondir(numberCell.getValue(),2));
						}
					}
					else if(type== CellType.DATE_FORMULA || type== CellType.DATE){
						DateCell dCell = (DateCell) cell;
						String value = sdf.format(dCell.getDate());
						query.setString(j-firstCol,value);	
					}else if(type== CellType.EMPTY || type== CellType.ERROR || type== CellType.FORMULA_ERROR){
						query.setString(j-firstCol,null);
					}else if(type == CellType.BOOLEAN || type == CellType.BOOLEAN){
						BooleanCell bCell = (BooleanCell) cell;
						query.setBoolean(j-firstCol, bCell.getValue());
					}
					k++;
				}
				try {
					if(idCodeAgence !=null && idCodeAgence>0){
						query.setLong(k, idCodeAgence);
					}
					query.executeUpdate();
					this.dataSource.flush();
				} catch (HibernateException e) {
					erreurs.add(new ExportErreur(e.getCause().getMessage(),nbFeuil, i+1, null));
					erreur = true;
				}
			}
			
		} catch (BiffException e) {
			erreurs.add(new ExportErreur(e.getMessage(),nbFeuil, null, null));
			erreur = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			erreurs.add(new ExportErreur(e.getMessage(),nbFeuil, null, null));
			erreur = true;
		}
		if(!erreur && sheet !=null){
			FacesMessages.instance().addToControl("infoGenerique", "L'export des données dans la table " + nomTable +" est terminé avec succès");
		}
	}

	public String versRepriseDonnees()
	{
		supprimerPieceJointe();
		nbColDonnees = null;
		nbFeuil = null;
		nbLigneDonnees = null;
		nbLigneDonneesFin = null;
		nbColLabel = null;
		nbLigneLabel = null;
		nomTable = null;
		idCodeAgence = null;
		erreurs = new ArrayList<ExportErreur>();
		return "/pages/parametrage/repriseDonnees.xhtml";
	}

	public String afficherPieceJointe() throws  IOException {

		byte[] bytes =  this.getData();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=\"" + this.getNomPieceJointe() + "\"");
		response.setContentLength(bytes.length);
		response.getOutputStream().write(bytes);
		response.setContentType(this.getContentType());
		context.responseComplete();
		return null;
	}

	public void pieceJointeUploadListener(UploadEvent event) {
		if (event == null) {
			// logger.warn("Invalid upload event");
		} else {
			UploadItem uploadItem = event.getUploadItem();
			String fileName = uploadItem.getFileName();
			int index = fileName.lastIndexOf('\\');
			if (index != -1)
			{
				fileName = fileName.substring(index + 1);
			}
			this.setContentType(uploadItem.getContentType());
			this.setNomPieceJointe(fileName);
			this.setData(uploadItem.getData());
		}

	}

	public void supprimerPieceJointe()
	{
		this.contentType = "";
		this.data = null;
		this.nomPieceJointe = "";
	}


	public void validerRepriseDonnees() {
		readXlsFile();
	}


	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getNomPieceJointe() {
		return nomPieceJointe;
	}

	public void setNomPieceJointe(String nomPieceJointe) {
		this.nomPieceJointe = nomPieceJointe;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}

	public Integer getNbFeuil() {
		return nbFeuil;
	}

	public void setNbFeuil(Integer nbFeuil) {
		this.nbFeuil = nbFeuil;
	}

	public Integer getNbColDonnees() {
		return nbColDonnees;
	}

	public void setNbColDonnees(Integer nbColDonnees) {
		this.nbColDonnees = nbColDonnees;
	}

	public Integer getNbLigneDonnees() {
		return nbLigneDonnees;
	}

	public void setNbLigneDonnees(Integer nbLigneDonnees) {
		this.nbLigneDonnees = nbLigneDonnees;
	}

	public Integer getNbLigneLabel() {
		return nbLigneLabel;
	}

	public void setNbLigneLabel(Integer nbLigneLabel) {
		this.nbLigneLabel = nbLigneLabel;
	}

	public Integer getNbColLabel() {
		return nbColLabel;
	}

	public void setNbColLabel(Integer nbColLabel) {
		this.nbColLabel = nbColLabel;
	}

	public List<ExportErreur> getErreurs() {
		return erreurs;
	}

	public void setErreurs(List<ExportErreur> erreurs) {
		this.erreurs = erreurs;
	}

	public Integer getNbLigneDonneesFin() {
		return nbLigneDonneesFin;
	}

	public void setNbLigneDonneesFin(Integer nbLigneDonneesFin) {
		this.nbLigneDonneesFin = nbLigneDonneesFin;
	}

	public Long getIdCodeAgence() {
		return idCodeAgence;
	}

	public void setIdCodeAgence(Long idCodeAgence) {
		this.idCodeAgence = idCodeAgence;
	}
	
}
