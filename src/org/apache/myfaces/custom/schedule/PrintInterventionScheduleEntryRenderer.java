package org.apache.myfaces.custom.schedule;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.schedule.model.ScheduleDay;
import org.apache.myfaces.custom.schedule.model.ScheduleEntry;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.jboss.seam.annotations.Name;

@SuppressWarnings("deprecation")
@Name("printInterventionScheduleEntryRenderer")
public class PrintInterventionScheduleEntryRenderer extends DefaultScheduleEntryRenderer {

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

    protected void renderCompactContent(FacesContext context, ResponseWriter writer, HtmlSchedule schedule, ScheduleDay day, ScheduleEntry entry, boolean selected) throws IOException
    {
    	GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcal.setTime(entry.getEndTime());
    	
    	GregorianCalendar gcalDay = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcalDay.setTime(day.getDate());
    	
    	if (gcalDay.get(Calendar.DATE) == gcal.get(Calendar.DATE) && gcal.get(Calendar.HOUR_OF_DAY) == 0 && gcal.get(Calendar.MINUTE) == 0)
    	{
    		return;
    	}
    	
    	MyDefaultScheduleEntry myEntry = (MyDefaultScheduleEntry)entry;
    	
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
        text.append(myEntry.getSmallTitle());
         
       	writer.writeText(text.toString(), null);
       	
        writer.endElement(HTML.DIV_ELEM);

              
    }
    
    protected void renderDetailedContentText(FacesContext context, ResponseWriter writer,
            HtmlSchedule schedule, ScheduleDay day, ScheduleEntry entry, boolean selected) throws IOException
    {
    	GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcal.setTime(entry.getEndTime());
    	
    	GregorianCalendar gcalDay = (GregorianCalendar) GregorianCalendar.getInstance();
    	gcalDay.setTime(day.getDate());
    	
    	if (gcalDay.get(Calendar.DATE) == gcal.get(Calendar.DATE) && gcal.get(Calendar.HOUR_OF_DAY) == 0 && gcal.get(Calendar.MINUTE) == 0)
    	{
    		return;
    	}
    	
    	// THA : Ajout de case à cocher :
    	MyDefaultScheduleEntry myEntry = (MyDefaultScheduleEntry)entry;
    	
    	
    	
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

    	
    	// write the title of the entry
        if (myEntry.getSmallTitle() != null)
        {
        	writer.startElement(HTML.SPAN_ELEM, schedule);
        	writer.writeAttribute(HTML.CLASS_ATTR, getStyleClass(schedule, "title"), null);
        	
        	if (textColor != null)
        	{
        		writer.writeAttribute(HTML.STYLE_ATTR, "color : " + textColor + ";", null);
        		
        	} else {
        		if ("green".equals(entryColor) || "blue".equals(entryColor))
        		{
        			writer.writeAttribute(HTML.STYLE_ATTR, "color : white;", null);
        		}
        	}
        	
        	
        	DateFormat format = AbstractScheduleRenderer.getDateFormat(context, schedule, 
                    HtmlSchedule.HOUR_NOTATION_24.equals(schedule.getHourNotation()) ? "HH:mm" : "h:mma");
            
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
        	 
            text.append(format.format(startTime));
            if (!startTime.equals(endTime)) {
                text.append("-");
                text.append(format.format(endTime));
            }
            text.append(": ");
            
            text.append(myEntry.getSmallTitle());
                
                
        	writer.writeText(text.toString(), null);
        	writer.endElement(HTML.SPAN_ELEM);
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
        
        MyDefaultScheduleEntry myEntry = (MyDefaultScheduleEntry)entry;
        
        if (myEntry.getSmallTitle() != null)
        {
            buffer.append(escape(myEntry.getSmallTitle()));
        }

        buffer.append("', 'content', '<i>");


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
            String value = (String) binding.getValue(FacesContext
                    .getCurrentInstance());

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
