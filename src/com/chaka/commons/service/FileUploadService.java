/**
 * 
 */
package com.chaka.commons.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.document.ChakaDocumentStore;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.chaka.common.utils.ChakaUtils;

/**
 * @author Gora
 *
 */
@Name("fileUploadService")
@Scope(ScopeType.SESSION)
//@AutoCreate
public class FileUploadService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 712785088206643892L;

	/****
	 * contenu
	 */
	private byte [] contenu;
	
	private File file;

	/****
	 * nom du fichier
	 */
	private String nomFichier = null;
	/****
	 * extension du fichier
	 */
	private String extension;
	/***
	 * 
	 */
	private Integer id = 1;

	
	public FileUploadService() {
		// TODO Auto-generated constructor stub
	}

	
	public void fileUploadListenerDoc(UploadEvent event) {
		id++;
		ChakaUtils.println("dans upload3");
		try{
			if (event == null) {
			
			} else {
				// on recupere l'item envoye
				UploadItem uploadItem = event.getUploadItem();						
				//if(nomFichier == null || nomFichier.isEmpty()){
					contenu = uploadItem.getData();
					nomFichier = uploadItem.getFileName();
				//}
				
				//if(nomFichier.contains("."))
				//	extension =  nomFichier.substring(nomFichier.lastIndexOf("."));
		}
		}catch(Exception e){
			//e.printStackTrace();
		}
	}
		
	public void paint(OutputStream out,Object data) throws IOException {  
		//ChakaUtils.println("dans paint");
  		if(contenu!=null){
			out.write(contenu);
  		}
  		  	
	}


	/**
	 * @return the contenu
	 */
	public byte[] getContenu() {
		return contenu;
	}

	/**
	 * @param contenu the contenu to set
	 */
	public void setContenu(byte[] contenu) {
		this.contenu = contenu;
	}

	/**
	 * @return the nomFichier
	 */
	public String getNomFichier() {
		return nomFichier;
	}

	/**
	 * @param nomFichier the nomFichier to set
	 */
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

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


	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}


	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}


	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}


	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}


}
