package com.chaka.projet.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang.StringUtils;
import org.richfaces.component.html.HtmlCalendar;

public class StylePhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2830633510935973269L;
	
	
	private final String ORIGINAL_STYLE = "com.chaka.original.style";

	public PhaseId getPhaseId() {

		return PhaseId.RENDER_RESPONSE;

	}

	
	public void beforePhase(PhaseEvent phaseEvent) {

		FacesContext context = FacesContext.getCurrentInstance();
		
		UIViewRoot root = context.getViewRoot();

		restoreOriginalStyles(context, root);

		Iterator<String> i = context.getClientIdsWithMessages();
		

		while (i.hasNext()) {

			String id = i.next();
			
		
			
			
			
			
			
			if (id != null)
			{
				String smallId = "";
				
				if (!id.contains(":"))
				{
					smallId = id;
				} else {
					
					
					String[] listeIds = StringUtils.split(id, ':');
					
					smallId = listeIds[listeIds.length - 1];
					
					
				}
				
				itererElement(root,context, smallId);
				
				UIComponent component = root.findComponent(id);
				
				if (component != null)
				{
					if (component instanceof UIInput) {
						
						String style = "";
						
						if (component instanceof HtmlCalendar)
						{
							style = (String) component.getAttributes().get("inputClass");
							style = style == null ? "" : " " + style;

							component.getAttributes().put("inputClass", "inputError" + style);
							
						} else {
							
							style = (String) component.getAttributes().get("styleClass");
							style = style == null ? "" : " " + style;

							component.getAttributes().put("styleClass", "inputError" + style);
						}


						saveOriginalStyle(id, style, context);

					} else {
						System.err.println(component.getClass());
					}
				} else {
					
					
					
					// On le cherche par une autre méthode :
					
					/*
					context.getViewRoot().invokeOnComponent(context, id,
					      new ContextCallback(){
						        
							@Override
							public void invokeContextCallback(FacesContext context, UIComponent component) {
								
								System.err.println(component.getClientId(context));
								
								String style = "";
								
								if (component instanceof HtmlCalendar)
								{
									style = (String) component.getAttributes().get("inputClass");
									style = style == null ? "" : " " + style;

									component.getAttributes().put("inputClass", "inputError" + style);
									
								} else {
									
									style = (String) component.getAttributes().get("styleClass");
									style = style == null ? "" : " " + style;

									component.getAttributes().put("styleClass", "inputError" + style);
									
								}


								saveOriginalStyle(component.getClientId(context), style, context);
								
									//System.err.println(arg1.getClientId(arg0) + " trouvé !!!");
									
								}
								
					});
					
					
					*/
				}
				
			}
		}

	}

	private void itererElement(UIComponent elt, FacesContext context, String idCherche) {
		
		if (elt != null)
		{
			List<UIComponent> listeFils = elt.getChildren();
			
			
			for (UIComponent comp : listeFils)
			{
				
				if (comp instanceof HtmlOutputLabel)
				{
					HtmlOutputLabel label = (HtmlOutputLabel) comp;
					
					
					String labelFor = label.getFor();
					
					if (labelFor != null)
					{
						labelFor = labelFor.replace("InputDate", "");
					}
					
					
					if (idCherche.equals(labelFor))
					{
						//
						String style = "";
						style = (String) comp.getAttributes().get("styleClass");
						style = style == null ? "" : " " + style;

						comp.getAttributes().put("styleClass", "labelError" + style);
						
						saveOriginalStyle(label.getClientId(context), style, context);
						
					}	
					
				}
				
				
				
				itererElement(comp, context, idCherche);
			}
			
			
			/*Iterator<UIComponent> ui = elt.getFacetsAndChildren();
			
			
			while(ui.hasNext())
			{
				UIComponent comp = ui.next();
				System.err.println(comp.getId() + " client : " + comp.getClientId(context));
				
				itererElement(comp, context);
			}
			*/
		}
	}
	
	
	
	//private String oldStyleValue;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void restoreOriginalStyles(FacesContext context, UIViewRoot root) {

		Map session = context.getExternalContext().getSessionMap();

		if (session.containsKey(ORIGINAL_STYLE)) {

			List<Map> list = (List) session.get(ORIGINAL_STYLE);

			for (Map item : list) {

				Map.Entry entry = (Map.Entry) item.entrySet().iterator().next();

				UIComponent component = root.findComponent((String) entry.getKey());

				if (component != null) {
					
					if (component instanceof HtmlCalendar)
					{
						component.getAttributes().put("inputClass", entry.getValue());
						
					} else {
						component.getAttributes().put("styleClass", entry.getValue());
					}

					

				} else {
					
					/*
					
					oldStyleValue = (String)entry.getValue();
					
					context.getViewRoot().invokeOnComponent(context, (String) entry.getKey(),
						      new ContextCallback(){
							        
								@Override
								public void invokeContextCallback(FacesContext context, UIComponent component) {
									
									
									if (component instanceof HtmlCalendar)
									{
										component.getAttributes().put("inputClass", oldStyleValue);
										
									} else {
										component.getAttributes().put("styleClass", oldStyleValue);
									}
									
								}
					
					});
					
					*/
					
				}

			}

			session.remove(ORIGINAL_STYLE);

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveOriginalStyle(String id, String style, FacesContext context) {

		Map session = context.getExternalContext().getSessionMap();

		Map<String, String> originalStyle = new HashMap<String, String>();

		originalStyle.put(id, style);

		if (session.get(ORIGINAL_STYLE) == null) {

			session.put(ORIGINAL_STYLE, new ArrayList());

		}

		((List) session.get(ORIGINAL_STYLE)).add(originalStyle);

	}

	public void afterPhase(PhaseEvent phaseEvent) {
	}

}
