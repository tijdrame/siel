package org.jboss.seam.document;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("org.jboss.seam.document.documentStore")
@Scope(ScopeType.CONVERSATION)
@Install(precedence = Install.APPLICATION)
public class ChakaDocumentStore extends DocumentStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7656794961690426202L;
	
	
	@Override
	protected String baseUrlForContent(String baseName, String extension) {
		String baseUrl =  super.baseUrlForContent(baseName, extension);
		
		if ("pdf".equalsIgnoreCase(extension)){
			baseUrl += "2";
		}
		
		return baseUrl;
	}
	
	public void setNextId(long nextId)
	{
		this.nextId = nextId;
	}
	
	public void clearDataStore()
	{
		if (this.dataStore != null)
			this.dataStore.clear();
	}
	
	


}
