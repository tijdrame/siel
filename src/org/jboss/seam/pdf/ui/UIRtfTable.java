package org.jboss.seam.pdf.ui;

import java.awt.Color;

import javax.faces.context.FacesContext;

import org.jboss.seam.pdf.ITextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.table.RtfCell;

public class UIRtfTable extends ITextComponent {

	 public static final String COMPONENT_TYPE   = "org.jboss.seam.pdf.ui.UIRtfTable";

	    Table table;
	    String  widths;
	    int     columns    = 1;
	    Float   width;
	    Integer horizontalAlignment;
	    Integer borderWidth;
	    String borderColor;


	    public void setWidths(String widths) {
	    	this.widths = widths;
	    }
	    
	    public void setColumns(int columns) {
	        this.columns = columns;
	    }
	    
	    public void setTable(Table table) {
	        this.table = table;
	    }
	    
	    public void setWidthPercentage(Float widthPercentage) {
	        this.width = widthPercentage;
	    }
		
	    @Override
	    public Object getITextObject() {
	        return table;
	    }
	    
	    @Override
	    public void removeITextObject() {
	        table = null;
	    }
	   
	    @Override
	    public void createITextObject(FacesContext context) {
	        columns = (Integer) valueBinding(context, "columns", columns);
	        try {
				table = new Table(columns);
			} catch (BadElementException e1) {
				throw new RuntimeException(e1);
			}
	        
	        widths = (String) valueBinding(context, "widths", widths);
	        if (widths != null) {
	        	try {
					table.setWidths(ITextUtils.stringToFloatArray(widths));
				} catch (DocumentException e) {
					throw new RuntimeException(e);
				}       	
	        }
	        
	        width = (Float) valueBinding(context, "width", width);
	        if (width!=null) {
	            table.setWidth(width);
	        }
	        
	        borderWidth = (Integer) valueBinding(context, "borderWidth", borderWidth);
	        if (borderWidth != null) {
	            table.setBorderWidth(borderWidth);
	        }
	        
	        borderColor = (String) valueBinding(context, "borderColor", borderColor);
	        if (borderColor != null && borderColor.equalsIgnoreCase("black")) {
	            table.setBorderColor(Color.BLACK);
	        }
	        
	        horizontalAlignment = (Integer) valueBinding(context, "horizontalAlignment", horizontalAlignment);
	        if (horizontalAlignment!=null) {
	            table.setAlignment(horizontalAlignment);
	        }

	    }
	    
	    @Override
	    public void handleAdd(Object o) {
	        if (o instanceof RtfCell) {
	            table.addCell((RtfCell) o);
	        } else if (o instanceof Phrase) {
	            try {
					table.addCell((Phrase) o);
				} catch (BadElementException e) {
					throw new RuntimeException("Can't add " + o.getClass().getName() +
                    " to table");
				}
	        } else {
	            throw new RuntimeException("Can't add " + o.getClass().getName() +
	                                       " to table");
	        }
	    }

		public RtfCell getDefaultCellFacet() {
	        Object facet = processFacet("defaultCell");
	        
			if (facet != null) {
			    if (!(facet instanceof RtfCell)) {
			    	throw new RuntimeException("UITable defaultCell facet must be a PdfPCell - found " + facet.getClass());
			    }
			    return (RtfCell) facet;
	 		}
			return null;
		}

		public int getBorderWidth() {
			return borderWidth;
		}

		public void setBorderWidth(int borderWidth) {
			this.borderWidth = borderWidth;
		}

		public String getBorderColor() {
			return borderColor;
		}

		public void setBorderColor(String borderColor) {
			this.borderColor = borderColor;
		}
}
