package org.jboss.seam.pdf.ui;

import javax.faces.context.FacesContext;

import org.jboss.seam.pdf.ITextUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Element;
import com.lowagie.text.rtf.table.RtfCell;

public class UIRtfCell extends UIRectangle {

	public static final String COMPONENT_TYPE = "org.jboss.seam.pdf.ui.UIRtfCell";

	RtfCell cell;

	String horizontalAlignment;

	String verticalAlignment;

	Boolean useBorderPadding;

	Boolean noWrap;

	Integer colspan;

	Boolean useAscender;

	Float grayFill;

	public void setGrayFill(Float grayFill) {
		this.grayFill = grayFill;
	}

	public void setHorizontalAlignment(String horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public void setUseBorderPadding(Boolean useBorderPadding) {
		this.useBorderPadding = useBorderPadding;
	}

	public void setNoWrap(Boolean noWrap) {
		this.noWrap = noWrap;
	}

	public void setUseAscender(Boolean useAscender) {
		this.useAscender = useAscender;
	}

	@Override
	public Object getITextObject() {
		return cell;
	}

	@Override
	public void removeITextObject() {
		cell = null;
	}

	@Override
	public void createITextObject(FacesContext context) {
		RtfCell defaultCell = getDefaultCellFromTable();
		if (defaultCell != null) {
			try {
				cell = new RtfCell(defaultCell);
			} catch (BadElementException e) {
				throw new RuntimeException("Can't create "
						+ cell.getClass().getName() + " to cell");
			}
		} else {
			cell = new RtfCell();
		}

		horizontalAlignment = (String) valueBinding(context,
				"horizontalAlignment", horizontalAlignment);
		if (horizontalAlignment != null) {
			cell.setHorizontalAlignment(ITextUtils
					.alignmentValue(horizontalAlignment));
		}

		verticalAlignment = (String) valueBinding(context, "verticalAlignment",
				verticalAlignment);
		if (verticalAlignment != null) {
			cell.setVerticalAlignment(ITextUtils
					.alignmentValue(verticalAlignment));
		}

		useBorderPadding = (Boolean) valueBinding(context, "useBorderPadding",
				useBorderPadding);
		if (useBorderPadding != null) {
			cell.setUseBorderPadding(useBorderPadding);
		}

		/*
		noWrap = (Boolean) valueBinding(context, "noWrap", noWrap);
		if (noWrap != null) {
			cell.setNoWrap(noWrap);
		}*/

		colspan = (Integer) valueBinding(context, "colspan", colspan);
		if (colspan != null) {
			cell.setColspan(colspan);
		}

		useAscender = (Boolean) valueBinding(context, "useAscender",
				useAscender);
		if (useAscender != null) {
			cell.setUseAscender(useAscender);
		}

		grayFill = (Float) valueBinding(context, "grayFill", grayFill);
		if (grayFill != null) {
			cell.setGrayFill(grayFill);
		}

		applyRectangleProperties(context, cell);
	}

	private RtfCell getDefaultCellFromTable() {
		UIRtfTable parentTable = (UIRtfTable) findITextParent(this,
				UIRtfTable.class);
		if (parentTable != null) {
			return parentTable.getDefaultCellFacet();
		}
		return null;
	}

	@Override
	public void handleAdd(Object o) {
		if (o instanceof Element) {
			// calling addElement negates setPhrase, etc...
			try {
				cell.addElement((Element) o);
			} catch (BadElementException e) {
				throw new RuntimeException("Can't add "
						+ o.getClass().getName() + " to cell");
			}
		} else {
			throw new RuntimeException("Can't add " + o.getClass().getName()
					+ " to cell");
		}
	}
}
