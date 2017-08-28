package com.chaka.projet.service.tree;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.chaka.projet.service.utils.ServiceUtils;



@Name("fileSystemBean")
@Scope(ScopeType.SESSION)
public class FileSystemBean implements Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6933911092714166078L;

    private FileSystemNode[] srcRoots;
    
    
    public synchronized FileSystemNode[] getSourceRoots() {
    
    	String root = ServiceUtils.chargerParametre("qualicertRoot");
    	srcRoots = new FileSystemNode(root).getNodes();
            
        return srcRoots;
    }
    
}