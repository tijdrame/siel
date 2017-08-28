package com.chaka.projet.service.tree;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.component.state.TreeStateAdvisor;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeRowKey;


@Name("treeDemoStateAdvisor")
@Scope(ScopeType.SESSION)
public class TreeDemoStateAdvisor implements TreeStateAdvisor, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5970148122592930298L;


	@SuppressWarnings("rawtypes")
	public Boolean adviseNodeOpened(UITree tree) {
        if (!PostbackPhaseListener.isPostback()) {
            Object key = tree.getRowKey();
            TreeRowKey treeRowKey = (TreeRowKey) key;
            if (treeRowKey == null || treeRowKey.depth() <= 2) {
                return Boolean.FALSE;
            }
        }
        
        return null;
    }

    public Boolean adviseNodeSelected(UITree tree) {
        return null;
    }
    
    
   

    
    public void processSelection(NodeSelectedEvent event) {
        org.richfaces.component.html.HtmlTree tree = (HtmlTree) event.getComponent();
        FileSystemNode nodeTitle = (FileSystemNode) tree.getRowData();
        
        
        
        
        if (tree.isLeaf())
        {
        	File file = null;
        	
			try {
				file = new File(new String(nodeTitle.getPath().getBytes(), "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
    		
    		if (file != null && file.isFile())
    		{
    			FacesContext context = FacesContext.getCurrentInstance();
    	    	
        		HttpServletResponse response = (HttpServletResponse) context
        				.getExternalContext().getResponse();
        		
        		try {
    				response.sendRedirect("/chaka/qualicertRenderer?name=" + nodeTitle.getPath());
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
        	
    		
    	
    		return ;
			
        }
        
        
        
    }
 
   
}