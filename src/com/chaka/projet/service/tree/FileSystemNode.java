package com.chaka.projet.service.tree;

import java.io.File;

public class FileSystemNode {
	
    private String path;
   
	private static FileSystemNode[] CHILDREN_ABSENT = new FileSystemNode[0];
    
    private FileSystemNode[] children;

    private String shortPath;
    
    public FileSystemNode(String path) {
    	
    	
    	
        this.path = path;
        int idx = path.lastIndexOf(File.separator);
        if (idx != -1) {
            shortPath = path.substring(idx + 1);
        } else {
            shortPath = path;
        }
    }
    
   
    
    
    
    
    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    
    

    public String getShortPath() {
		return shortPath;
	}










	public void setShortPath(String shortPath) {
		this.shortPath = shortPath;
	}










	public synchronized FileSystemNode[] getNodes() {
        if (children == null) {
        	
        	
        	File chemin = new File(this.path);
        	
        	Object[] nodes = (Object[]) chemin.listFiles();
        	
            //if (resourcePaths != null)
        	if (nodes != null)
        	{
                 // maListe.toArray();
                children = new FileSystemNode[nodes.length];
                
                for (int i = 0; i < nodes.length; i++) {
                    String nodePath = nodes[i].toString();
                    if (nodePath.endsWith("/")) {
                        nodePath = nodePath.substring(0, nodePath.length() - 1);
                    }
                    children[i] = new FileSystemNode(nodePath);
                }
            } 
            else {
                children = CHILDREN_ABSENT;
            }
        }

        return children;
    }
    
    public String toString() {
        return shortPath;
    }

}