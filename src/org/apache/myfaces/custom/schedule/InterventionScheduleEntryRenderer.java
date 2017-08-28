package org.apache.myfaces.custom.schedule;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.component.html.ext.HtmlSelectBooleanCheckbox;
import org.apache.myfaces.custom.schedule.model.ScheduleDay;
import org.apache.myfaces.custom.schedule.model.ScheduleEntry;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.jboss.seam.annotations.Name;
import org.richfaces.component.html.ContextMenu;
import org.richfaces.component.html.HtmlMenuItem;

@SuppressWarnings("deprecation")
@Name("interventionScheduleEntryRenderer")
public class InterventionScheduleEntryRenderer extends DefaultScheduleEntryRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3454002659152799624L;
	
	
	/**
     * @see org.apache.myfaces.custom.schedule.ScheduleEntryRenderer#renderContent(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, org.apache.myfaces.custom.schedule.HtmlSchedule, org.apache.myfaces.custom.schedule.model.ScheduleDay, org.apache.myfaces.custom.schedule.model.ScheduleEntry, boolean, boolean)
     */
    public void renderContent(FacesContext context, ResponseWriter writer,
                              HtmlSchedule schedule, ScheduleDay day, ScheduleEntry entry,
                              boolean compact, boolean selected) throws IOException
    {
        if (compact)
        {
            renderCompactContent(context, writer, schedule, day, entry, selected);
        } else
        {
            if (selected)
            {
                StringBuffer entryStyle = new StringBuffer();
                entryStyle.append("height: 100%; width: 100%;");
                //the left border of a selected entry should have the same
                //color as the entry border
                String entryColor = getColor(context, schedule, entry, selected);
                String textColor = getTextColor(context, schedule, entry, selected);
                
                
                if (entryColor != null) {
                    entryStyle.append("border-color: ");
                    entryStyle.append(entryColor);
                    entryStyle.append(";");
                    
                    entryStyle.append("background-color: ");
                    entryStyle.append(entryColor);
                    entryStyle.append(";");
                    
                    
                    
                    if (textColor != null)
                    {
                    	entryStyle.append("color: ");
                        entryStyle.append(textColor);
                        entryStyle.append(";");
                    } else {
                    	
                    	if ("green".equals(entryColor) || "blue".equals(entryColor))
                        {
                        	entryStyle.append("color: ");
                            entryStyle.append("white");
                            entryStyle.append(";");
                        }
                    }
                    
                    
                    
                   
                    entryStyle.append("border-width:5px ");
                    entryStyle.append(";");
                    
                }
                // draw the contents of the selected entry
                writer.startElement(HTML.DIV_ELEM, null);
                //writer.writeAttribute(HTML.CLASS_ATTR, getStyleClass(schedule,
                //                                                     "text"), null);
                writer.writeAttribute(HTML.STYLE_ATTR,entryStyle.toString(), null);
                
                renderDetailedContentText(context, writer, schedule, day, entry, selected);
                
                writer.endElement(HTML.DIV_ELEM);
            } else
            {
                renderDetailedContentText(context, writer, schedule, day, entry, selected);
            }
        }

    }
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    protected void renderCompactContent(FacesContext context, ResponseWriter writer, HtmlSchedule schedule, ScheduleDay day, ScheduleEntry entry, boolean selected) throws IOException
    {
    	// THA : Ajout de case à cocher :
    	MyDefaultScheduleEntry myEntry = (MyDefaultScheduleEntry)entry;
    	
    	GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcal.setTime(entry.getEndTime());
    	
    	GregorianCalendar gcalDay = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcalDay.setTime(day.getDate());
    	
    	if (!myEntry.isIndisponibilite() && gcalDay.get(Calendar.DATE) == gcal.get(Calendar.DATE) && gcal.get(Calendar.HOUR_OF_DAY) == 0 && gcal.get(Calendar.MINUTE) == 0)
    	{
    		return;
    	}
    	
    	
    	
    	
    	String serviceName = null;
    	
    	switch (myEntry.getModeAffichage()) {
		case 0:
			serviceName = "interventionService";
			break;
		case 1:
			serviceName = "planningIntervenantService";
			break;
		case 2:
			serviceName = "planningBeneficiaireService";
			break;
		case 3:
			serviceName = "planningAgenceService";
			break;
		case 4:
			serviceName = "gardeEnfantService";
			break;

		default:
			break;
		}
    	
    	StringBuffer entryStyle = new StringBuffer();
        entryStyle.append("height: 100%; width: 100%;");
        //the left border of a selected entry should have the same
        //color as the entry border
        String entryColor = getColor(context, schedule, entry, selected);
        String textColor = getTextColor(context, schedule, entry, selected);
        if (entryColor != null) {
            entryStyle.append("border-color: ");
            entryStyle.append(entryColor);
            entryStyle.append(";");
            
            entryStyle.append("background-color: ");
            entryStyle.append(entryColor);
            entryStyle.append(";");
            
            
            if (textColor != null)
            {
            	entryStyle.append("color: ");
                entryStyle.append(textColor);
                entryStyle.append(";");
                
            } else {
            	if ("green".equals(entryColor) || "blue".equals(entryColor))
                {
                	entryStyle.append("color: ");
                    entryStyle.append("white");
                    entryStyle.append(";");
                }
            }
            
            
            
            entryStyle.append("border-width:5px ");
            entryStyle.append(";");
            
        }
        
        writer.startElement(HTML.DIV_ELEM, null);
        writer.writeAttribute(HTML.STYLE_ATTR,entryStyle.toString(), null);
        
        String lastCheckBoxId = "";
    	
    	if (myEntry.getNonModifiable() == null || !myEntry.getNonModifiable() ){
    		
    		String jour1 = sdf.format(myEntry.getStartTime());
    		
    		String jour2 = sdf.format(day.getDate());
    		
    		if (jour1.equals(jour2))
    		{
    			HtmlSelectBooleanCheckbox htmlCheckbox = new HtmlSelectBooleanCheckbox();
            	htmlCheckbox.setId("_chk_id_" + checkId++);
            	
            	lastCheckBoxId = htmlCheckbox.getId();
            	
            	ELContext elContext = context.getELContext();
            	Application app = context.getApplication();
            	ExpressionFactory elFactory = app.getExpressionFactory(); 
            	
            	
            	
            	ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{" + serviceName + ".mapPlagesSelections[" + myEntry.getIdPlageHoraire() + "]}", Object.class);
            	htmlCheckbox.setValueExpression("value", valueExp);
            	htmlCheckbox.setParent(schedule);
            	schedule.getChildren().add(htmlCheckbox);
            	htmlCheckbox.encodeAll(context);
    		}
    		
    			
    	}
    	
        StringBuffer text = new StringBuffer();
        Date startTime = entry.getStartTime();

        if (day.getDayStart().after(entry.getStartTime()))
        {
            startTime = day.getDayStart();
        }

        Date endTime = entry.getEndTime();

        if (day.getDayEnd().before(entry.getEndTime()))
        {
            endTime = day.getDayEnd();
        }

        if (!entry.isAllDay())
        {
            DateFormat format = AbstractScheduleRenderer.getDateFormat(context, schedule, 
                    HtmlSchedule.HOUR_NOTATION_24.equals(schedule.getHourNotation()) ? "HH:mm" : "h:mma");
            
            text.append(format.format(startTime));
            if (!startTime.equals(endTime)) {
                text.append("-");
                text.append(format.format(endTime));
            }
            text.append(": ");
        }
        text.append(entry.getTitle());
        
        
        if (StringUtils.isNotBlank(myEntry.getImage()))
        {
        	HtmlGraphicImage image = new HtmlGraphicImage();
        	image.setId("_img_id_" + checkId++);
        	image.setValue(myEntry.getImage());
        	image.setWidth("16");
        	image.setHeight("16");
        	image.setParent(schedule);
        	schedule.getChildren().add(image);
        	image.encodeAll(context);
        }
        
        if ( myEntry.getModeAffichage() != 0 && (myEntry.getNonModifiable() == null || !myEntry.getNonModifiable()) ){
        	
        	HtmlAjaxCommandLink ajaxLink = new HtmlAjaxCommandLink();
        	ajaxLink.setId("_modif_lnk_id_" + checkId++);
        	ajaxLink.setValue(text.toString());
        	ajaxLink.setStyle("color: black;");
        	
        	
        	MethodExpression me = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        		createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".modifierIntervention("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
        	ajaxLink.setActionExpression(me);
        	ajaxLink.setOncomplete("javascript:Richfaces.showModalPanel('panelModification',{width:530, top:10, height:560})");
        	ajaxLink.setReRender("ModificationIntervention");
        	ajaxLink.setStatus("waitStatus");
        	ajaxLink.setOnclick("if (!verifierCibleOriginale(event)) return false;");
        	
        	if ("green".equals(entryColor) || "blue".equals(entryColor))
            {
        		ajaxLink.setStyle("color : white;");
            }
        	
        	if ("planningBeneficiaireService".equals(serviceName))
        	{
        		// On peut ajouter un context menu avec lien vers fiche intervenant, planning intervenant et dupliquer intervention
        		ContextMenu cm = new ContextMenu();
        		cm.setId("_cm_id_" + checkId++);
        		cm.setDisableDefaultMenu(true);
        		cm.setEvent("oncontextmenu");
        		cm.setSubmitMode("ajax");
        		cm.setAttached(true);
        		cm.setAttachTo(ajaxLink.getId());
        		cm.setOnexpand("contextMenuExpand();");
        		
        		if(!myEntry.isSansIntervenant()){
        		HtmlMenuItem hmi = new HtmlMenuItem();
        		hmi.setId("_hmi_id_" + checkId++);
        		hmi.setIcon("/picto/user_red.png");
        		hmi.setStyleClass("classique");
        		hmi.setStatus("waitStatus");
        		hmi.setValue("Vers fiche intervenant");
        		
        		
        		HtmlMenuItem hmi2 = new HtmlMenuItem();
        		hmi2.setId("_hmi_id_" + checkId++);
        		hmi2.setIcon("/picto/calendar_view_day.png");
        		hmi2.setStyleClass("classique");
        		hmi2.setStatus("waitStatus");
        		hmi2.setValue("Vers planning intervenant");
        		MethodExpression meFicheIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
            			createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versFicheIntervenantDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi.setActionExpression(meFicheIntervenant);
            		
            	MethodExpression mePlanningIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versPlanningIntervenantDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi2.setActionExpression(mePlanningIntervenant);
            	
            	cm.getChildren().add(hmi);
            	cm.getChildren().add(hmi2);
        		}
        		
        		HtmlMenuItem hmi3 = new HtmlMenuItem();
        		hmi3.setId("_hmi_id_" + checkId++);
        		hmi3.setIcon("/picto/arrow_out.png");
        		hmi3.setStyleClass("classique");
        		hmi3.setStatus("waitStatus");
        		hmi3.setValue("Dupliquer l'intervention");
        		hmi3.setOncomplete("javascript:Richfaces.showModalPanel('panelDuplication',{width:530, top:10, height:520})");
        		hmi3.setReRender("DuplicationIntervention");
        		
        		
        		
        		
        		MethodExpression meDuplication = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versDuplicationDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi3.setActionExpression(meDuplication);
        		
            	cm.getChildren().add(hmi3);
        		
        		
            	ajaxLink.getChildren().add(cm);
        	}
        	
        	
        	if ("planningIntervenantService".equals(serviceName) && !myEntry.isSansIntervenant())
        	{
        		// On peut ajouter un context menu avec lien vers fiche intervenant et planning intervenant :
        		ContextMenu cm = new ContextMenu();
        		cm.setId("_cm_id_" + checkId++);
        		cm.setDisableDefaultMenu(true);
        		cm.setEvent("oncontextmenu");
        		cm.setSubmitMode("ajax");
        		cm.setAttached(true);
        		cm.setAttachTo(ajaxLink.getId());
        		cm.setOnexpand("contextMenuExpand();");
        		
        		
        		HtmlMenuItem hmi = new HtmlMenuItem();
        		hmi.setId("_hmi_id_" + checkId++);
        		hmi.setIcon("/picto/user_suit.png");
        		hmi.setStyleClass("classique");
        		hmi.setStatus("waitStatus");
        		hmi.setValue("Vers fiche client");
        		
        		
        		HtmlMenuItem hmi2 = new HtmlMenuItem();
        		hmi2.setId("_hmi_id_" + checkId++);
        		hmi2.setIcon("/picto/calendar_view_day.png");
        		hmi2.setStyleClass("classique");
        		hmi2.setStatus("waitStatus");
        		hmi2.setValue("Vers planning client");
        		
        		/*
        		HtmlMenuItem hmi3 = new HtmlMenuItem();
        		hmi3.setId("_hmi_id_" + checkId++);
        		hmi3.setIcon("/picto/arrow_out.png");
        		hmi3.setStyleClass("classique");
        		hmi3.setStatus("waitStatus");
        		hmi3.setValue("Dupliquer l'intervention");
        		hmi3.setOncomplete("javascript:Richfaces.showModalPanel('panelDuplication',{width:530, top:10, height:520})");
        		hmi3.setReRender("DuplicationIntervention");
        		*/
        		
        		MethodExpression meFicheIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        			createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versFicheBeneficiaireDepuisPlanningIntervenant("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
        		hmi.setActionExpression(meFicheIntervenant);
        		
        		MethodExpression mePlanningIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
    				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versPlanningBeneficiaireDepuisPlanningIntervenant("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
        		hmi2.setActionExpression(mePlanningIntervenant);
        		
        		/*
        		MethodExpression meDuplication = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versDuplicationDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi3.setActionExpression(meDuplication);
        		*/
        		
            	cm.getChildren().add(hmi);
            	cm.getChildren().add(hmi2);
            	//cm.getChildren().add(hmi3);
        		
        		
            	ajaxLink.getChildren().add(cm);
        	}
        	
        	
        	
        	
        	schedule.getChildren().add(ajaxLink);
        	ajaxLink.encodeAll(context);
        	
        	
        	
        } else {
        	
        	HtmlOutputLabel myLabel = new HtmlOutputLabel();
        	myLabel.setId("_mylabel_id_" + checkId++);
        	myLabel.setValue(text.toString());
        	myLabel.setFor(lastCheckBoxId);
        	
        	myLabel.setParent(schedule);
        	schedule.getChildren().add(myLabel);
        	myLabel.encodeAll(context);
        	
        	//writer.writeText(text.toString(), null);
        }
        
        writer.endElement(HTML.DIV_ELEM);

              
    }
    
    private static int checkId = 0;
    
    protected void renderDetailedContentText(FacesContext context, ResponseWriter writer,
            HtmlSchedule schedule, ScheduleDay day, ScheduleEntry entry, boolean selected) throws IOException
    {
    	// THA : Ajout de case à cocher :
    	MyDefaultScheduleEntry myEntry = (MyDefaultScheduleEntry)entry;
    	
    	GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcal.setTime(entry.getEndTime());
    	
    	GregorianCalendar gcalDay = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcalDay.setTime(day.getDate());
    	
    	if (!myEntry.isIndisponibilite() && gcalDay.get(Calendar.DATE) == gcal.get(Calendar.DATE) && gcal.get(Calendar.HOUR_OF_DAY) == 0 && gcal.get(Calendar.MINUTE) == 0)
    	{
    		return;
    	}
    	
    	
    	
    	String serviceName = null;
    	
    	switch (myEntry.getModeAffichage()) {
		case 0:
			serviceName = "interventionService";
			break;
		case 1:
			serviceName = "planningIntervenantService";
			break;
		case 2:
			serviceName = "planningBeneficiaireService";
			break;
		case 3:
			serviceName = "planningAgenceService";
			break;
		case 4:
			serviceName = "gardeEnfantService";
			break;
		default:
			break;
		}
    	
    	StringBuffer entryStyle = new StringBuffer();
        entryStyle.append("height: 100%; width: 100%;");
        //the left border of a selected entry should have the same
        //color as the entry border
        String entryColor = getColor(context, schedule, entry, selected);
        if (entryColor != null) {
            entryStyle.append("border-color: ");
            entryStyle.append(entryColor);
            entryStyle.append(";");
            
            entryStyle.append("background-color: ");
            entryStyle.append(entryColor);
            entryStyle.append(";");
            
            
            entryStyle.append("border-width:5px ");
            entryStyle.append(";");
            
        }
        
        String textColor = getTextColor(context, schedule, entry, selected);
        if (textColor != null)
        {
        	entryStyle.append("color: ");
            entryStyle.append(textColor);
            entryStyle.append(";");
            
        }
        
        writer.startElement(HTML.DIV_ELEM, null);
        writer.writeAttribute(HTML.STYLE_ATTR,entryStyle.toString(), null);
    	
        String lastCheckBoxId = "";
    	
    	
    	
    	
    	if (myEntry.getNonModifiable() == null || !myEntry.getNonModifiable() ){
    		
    		
    		String jour1 = sdf.format(myEntry.getStartTime());
    		
    		String jour2 = sdf.format(day.getDate());
    		
    		if (jour1.equals(jour2))
    		{
    			HtmlSelectBooleanCheckbox htmlCheckbox = new HtmlSelectBooleanCheckbox();
            	htmlCheckbox.setId("_chk_id_" + checkId++);
            	
            	lastCheckBoxId = htmlCheckbox.getId();
            	
            	ELContext elContext = context.getELContext();
            	Application app = context.getApplication();
            	ExpressionFactory elFactory = app.getExpressionFactory(); 
            	
            	
            	ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{" + serviceName + ".mapPlagesSelections[" + myEntry.getIdPlageHoraire() + "]}", Object.class);
            	htmlCheckbox.setValueExpression("value", valueExp);
            	htmlCheckbox.setParent(schedule);
            	schedule.getChildren().add(htmlCheckbox);
            	htmlCheckbox.encodeAll(context);	
    		}
    		
    	}
    	
    	if (StringUtils.isNotBlank(myEntry.getImage()))
        {
        	HtmlGraphicImage image = new HtmlGraphicImage();
        	image.setId("_img_id_" + checkId++);
        	image.setValue(myEntry.getImage());
        	image.setWidth("16");
        	image.setHeight("16");
        	image.setParent(schedule);
        	schedule.getChildren().add(image);
        	image.encodeAll(context);
        }
    	
    	if (myEntry.getModeAffichage() != 0 && (myEntry.getNonModifiable() == null || !myEntry.getNonModifiable()) ){
    		
    		
    		StringBuffer text = new StringBuffer();
            Date startTime = entry.getStartTime();

            if (day.getDayStart().after(entry.getStartTime()))
            {
                startTime = day.getDayStart();
            }

            Date endTime = entry.getEndTime();

            if (day.getDayEnd().before(entry.getEndTime()))
            {
                endTime = day.getDayEnd();
            }

            if (!entry.isAllDay())
            {
                DateFormat format = AbstractScheduleRenderer.getDateFormat(context, schedule, 
                        HtmlSchedule.HOUR_NOTATION_24.equals(schedule.getHourNotation()) ? "HH:mm" : "h:mma");
                
                text.append(format.format(startTime));
                if (!startTime.equals(endTime)) {
                    text.append("-");
                    text.append(format.format(endTime));
                }
                text.append(": ");
            }
            text.append(entry.getTitle());
    		
    		
    		HtmlAjaxCommandLink ajaxLink = new HtmlAjaxCommandLink();
        	ajaxLink.setId("_modif_lnk_id_" + checkId);
        	ajaxLink.setValue(text.toString());
        	ajaxLink.setStyle("color: black;");
        	
        	
        	MethodExpression me = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        		createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".modifierIntervention("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
        	ajaxLink.setActionExpression(me);
        	ajaxLink.setOncomplete("javascript:Richfaces.showModalPanel('panelModification',{width:530, top:10, height:560})");
        	ajaxLink.setReRender("ModificationIntervention");
        	ajaxLink.setStatus("waitStatus");
        	ajaxLink.setOnclick("if (!verifierCibleOriginale(event)) return false;");
        	
            if (textColor != null)
            {
            	ajaxLink.setStyle("color : " + textColor + ";");
            	
            } else {
            	if ("green".equals(entryColor) || "blue".equals(entryColor))
                {
            		ajaxLink.setStyle("color : white;");
                }	
            }
        	
        	
    		
        	if ("planningBeneficiaireService".equals(serviceName))
        	{
        		/*
        		HtmlGraphicImage image = new HtmlGraphicImage();
            	image.setId("_img_id_" + checkId++);
            	image.setValue("/picto/folder_go.png");
            	image.setWidth("16");
            	image.setHeight("16");
            	image.setParent(schedule);
            	image.setTitle("");
            	image.setStyle("z-index: 2000;");
            	image.setStyleClass("boutonPopupCss");
            	*/
        		
        		// On peut ajouter un context menu avec lien vers fiche intervenant, planning intervenant et dupliquer intervention
        		ContextMenu cm = new ContextMenu();
        		cm.setId("_cm_id_" + checkId++);
        		cm.setDisableDefaultMenu(true);
        		cm.setEvent("oncontextmenu");
        		cm.setSubmitMode("ajax");
        		cm.setAttached(true);
        		cm.setAttachTo(ajaxLink.getId());
        		cm.setOnexpand("contextMenuExpand();");
        		
        	    if(!myEntry.isSansIntervenant()){
        		HtmlMenuItem hmi = new HtmlMenuItem();
        		hmi.setId("_hmi_id_" + checkId++);
        		hmi.setIcon("/picto/user_red.png");
        		hmi.setStyleClass("classique");
        		hmi.setStatus("waitStatus");
        		hmi.setValue("Vers fiche intervenant");
        		
        		
        		HtmlMenuItem hmi2 = new HtmlMenuItem();
        		hmi2.setId("_hmi_id_" + checkId++);
        		hmi2.setIcon("/picto/calendar_view_day.png");
        		hmi2.setStyleClass("classique");
        		hmi2.setStatus("waitStatus");
        		hmi2.setValue("Vers planning intervenant");
        		
        		MethodExpression meFicheIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
            			createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versFicheIntervenantDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi.setActionExpression(meFicheIntervenant);
            		
            	MethodExpression mePlanningIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versPlanningIntervenantDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi2.setActionExpression(mePlanningIntervenant);
            	
            	cm.getChildren().add(hmi);
            	cm.getChildren().add(hmi2);
        	    }
        		
        		HtmlMenuItem hmi3 = new HtmlMenuItem();
        		hmi3.setId("_hmi_id_" + checkId++);
        		hmi3.setIcon("/picto/arrow_out.png");
        		hmi3.setStyleClass("classique");
        		hmi3.setStatus("waitStatus");
        		hmi3.setValue("Dupliquer l'intervention");
        		hmi3.setOncomplete("javascript:Richfaces.showModalPanel('panelDuplication',{width:530, top:10, height:520})");
        		hmi3.setReRender("DuplicationIntervention");
        		
        		
        		
        		MethodExpression meDuplication = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versDuplicationDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi3.setActionExpression(meDuplication);
        		
            	//hmi.setParent(cm);
            	cm.getChildren().add(hmi3);
        		//hmi.encodeAll(context);
        		
        		
        		//cm.setParent(ajaxLink);
            	//image.getChildren().add(cm);
            	
            	ajaxLink.getChildren().add(cm);
            	//ajaxLink.encodeAll(context);
        		//cm.encodeAll(context);
            	
            	//schedule.getChildren().add(image);
            	//image.encodeAll(context);
            	
        	}
        	
        	if ("planningIntervenantService".equals(serviceName) && !myEntry.isSansIntervenant())
        	{
        		// On peut ajouter un context menu avec lien vers fiche intervenant et planning intervenant :
        		ContextMenu cm = new ContextMenu();
        		cm.setId("_cm_id_" + checkId++);
        		cm.setDisableDefaultMenu(true);
        		cm.setEvent("oncontextmenu");
        		cm.setSubmitMode("ajax");
        		cm.setAttached(true);
        		cm.setAttachTo(ajaxLink.getId());
        		cm.setOnexpand("contextMenuExpand();");
        		
        		
        		HtmlMenuItem hmi = new HtmlMenuItem();
        		hmi.setId("_hmi_id_" + checkId++);
        		hmi.setIcon("/picto/user_suit.png");
        		hmi.setStyleClass("classique");
        		hmi.setStatus("waitStatus");
        		hmi.setValue("Vers fiche client");
        		
        		
        		HtmlMenuItem hmi2 = new HtmlMenuItem();
        		hmi2.setId("_hmi_id_" + checkId++);
        		hmi2.setIcon("/picto/calendar_view_day.png");
        		hmi2.setStyleClass("classique");
        		hmi2.setStatus("waitStatus");
        		hmi2.setValue("Vers planning client");
        		
        		/*
        		HtmlMenuItem hmi3 = new HtmlMenuItem();
        		hmi3.setId("_hmi_id_" + checkId++);
        		hmi3.setIcon("/picto/arrow_out.png");
        		hmi3.setStyleClass("classique");
        		hmi3.setStatus("waitStatus");
        		hmi3.setValue("Dupliquer l'intervention");
        		hmi3.setOncomplete("javascript:Richfaces.showModalPanel('panelDuplication',{width:530, top:10, height:520})");
        		hmi3.setReRender("DuplicationIntervention");
        		*/
        		
        		MethodExpression meFicheIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        			createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versFicheBeneficiaireDepuisPlanningIntervenant("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
        		hmi.setActionExpression(meFicheIntervenant);
        		
        		MethodExpression mePlanningIntervenant = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
    				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versPlanningBeneficiaireDepuisPlanningIntervenant("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
        		hmi2.setActionExpression(mePlanningIntervenant);
        		
        		/*
        		MethodExpression meDuplication = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().
        				createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{" + serviceName + ".versDuplicationDepuisPlanningBeneficiaire("+ myEntry.getIdPlageHoraire() +")}", null, new Class[]{Long.class} );
            	hmi3.setActionExpression(meDuplication);
        		*/
        		
            	cm.getChildren().add(hmi);
            	cm.getChildren().add(hmi2);
            	//cm.getChildren().add(hmi3);
        		
        		
            	ajaxLink.getChildren().add(cm);
        	}
        	
        	schedule.getChildren().add(ajaxLink);
        	ajaxLink.encodeAll(context);

        	
        } else {
        	// write the title of the entry
            if (entry.getTitle() != null)
            {
                writer.startElement(HTML.SPAN_ELEM, schedule);
                writer.writeAttribute(HTML.CLASS_ATTR, getStyleClass(
                        schedule, "title"), null);
                
                if (textColor != null)
                {
                	writer.writeAttribute(HTML.STYLE_ATTR, "color : " + textColor + ";", null);
                    
                } else {
                	if ("green".equals(entryColor) || "blue".equals(entryColor))
                    {
                    	writer.writeAttribute(HTML.STYLE_ATTR, "color : white;", null);
                    }
                }
                
                
                StringBuffer text = new StringBuffer();
                Date startTime = entry.getStartTime();

                if (day.getDayStart().after(entry.getStartTime()))
                {
                    startTime = day.getDayStart();
                }

                Date endTime = entry.getEndTime();

                if (day.getDayEnd().before(entry.getEndTime()))
                {
                    endTime = day.getDayEnd();
                }

                if (!entry.isAllDay())
                {
                    DateFormat format = AbstractScheduleRenderer.getDateFormat(context, schedule, 
                            HtmlSchedule.HOUR_NOTATION_24.equals(schedule.getHourNotation()) ? "HH:mm" : "h:mma");
                    
                    text.append(format.format(startTime));
                    if (!startTime.equals(endTime)) {
                        text.append("-");
                        text.append(format.format(endTime));
                    }
                    text.append(": ");
                }
                text.append(entry.getTitle());
                
                HtmlOutputLabel myLabel = new HtmlOutputLabel();
                myLabel.setId("_mylabel_id_" + checkId++);
            	myLabel.setValue(text.toString());
            	myLabel.setFor(lastCheckBoxId);
            	
            	myLabel.setParent(schedule);
            	schedule.getChildren().add(myLabel);
            	myLabel.encodeAll(context);
                
                //writer.writeText(text.toString(), null);
                writer.endElement(HTML.SPAN_ELEM);
            }
            
           
        }
    	
    	
           
        
        writer.endElement(HTML.DIV_ELEM);
    }
    
    /**
     * @see org.apache.myfaces.custom.schedule.ScheduleEntryRenderer#getColor(javax.faces.context.FacesContext, org.apache.myfaces.custom.schedule.HtmlSchedule, org.apache.myfaces.custom.schedule.model.ScheduleEntry, boolean)
     */
    public String getColor(FacesContext context, HtmlSchedule schedule,
                           ScheduleEntry entry, boolean selected)
    {
    	if (entry != null && entry instanceof MyDefaultScheduleEntry){
    		
    		MyDefaultScheduleEntry myEntry = (MyDefaultScheduleEntry)entry;
    		if (myEntry.getColor() != null && myEntry.getColor().trim().length()> 0){
    			return myEntry.getColor();
    		}
    		
    	}
        return null;
    }
    
    public String getTextColor(FacesContext context, HtmlSchedule schedule,
            ScheduleEntry entry, boolean selected)
    {
    	if (entry != null && entry instanceof MyDefaultScheduleEntry){
    		
    		MyDefaultScheduleEntry myEntry = (MyDefaultScheduleEntry)entry;
    		if (myEntry.getTextColor() != null && myEntry.getTextColor().trim().length()> 0){
    			return myEntry.getTextColor();
    		}
    		
    	}
    	return null;
    }
    

    /**
     * @see org.apache.myfaces.custom.schedule.ScheduleEntryRenderer#renderToolTip(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, org.apache.myfaces.custom.schedule.HtmlSchedule, org.apache.myfaces.custom.schedule.model.ScheduleEntry, boolean)
     */
    public void renderToolTip(FacesContext context, ResponseWriter writer,
                              HtmlSchedule schedule, ScheduleEntry entry, boolean selected)
            throws IOException
    {
        StringBuffer buffer = new StringBuffer();
        buffer
                .append("return makeTrue(domTT_activate(this, event, 'caption', '");

        if (entry.getTitle() != null)
        {
            buffer.append(escape(entry.getTitle()));
        }

        buffer.append("', 'content', '<i>");

        if (entry.getSubtitle() != null)
        {
            buffer.append(escape(entry.getSubtitle()));
        }

        buffer.append("</i>");

        if (entry.getDescription() != null)
        {
            buffer.append("<br/>");
            buffer.append(escape(entry.getDescription()));
        }

        buffer.append("', 'trail', true));");
        writer.writeAttribute("onmouseover", buffer.toString(), null);
    }

    private String escape(String text)
    {
        if (text == null)
        {
            return null;
        }

        return text.replaceAll("'", "\\\\\'").replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
    }

    /**
     * @see org.apache.myfaces.custom.schedule.ScheduleEntryRenderer#getEntryClass(org.apache.myfaces.custom.schedule.HtmlSchedule, org.apache.myfaces.custom.schedule.model.ScheduleEntry)
     */
    public String getEntryClass(HtmlSchedule schedule, ScheduleEntry entry)
    {
        return getStyleClass(schedule, "entry");
    }
    
    /**
     * <p>
     * Allow the developer to specify custom CSS classnames for the schedule
     * component.
     * </p>
     * 
     * @param component
     *            the component
     * @param className
     *            the default CSS classname
     * @return the custom classname
     */
    @SuppressWarnings("rawtypes")
	protected String getStyleClass(UIComponent component, String className)
    {
        // first check if the styleClass property is a value binding expression
        ValueBinding binding = component.getValueBinding(className);
        if (binding != null)
        {
            String value = (String) binding.getValue(FacesContext.getCurrentInstance());

            if (value != null)
            {
                return value;
            }
        }
        // it's not a value binding expression, so check for the string value
        // in the attributes
        Map attributes = component.getAttributes();
        String returnValue = (String) attributes.get(className + "Class");
        return returnValue == null ? className : returnValue;
    }
	
	
	
	

}
