/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.custom.schedule;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;


// Generated from class org.apache.myfaces.custom.schedule.AbstractUIScheduleBase.
//
// WARNING: This file was automatically generated. Do not edit it directly,
//          or you will lose your changes.
public class UIScheduleBase extends org.apache.myfaces.custom.schedule.AbstractUIScheduleBase
    implements java.io.Serializable, javax.faces.component.ValueHolder
{
    private static final long serialVersionUID = 5702081384947086911L; 

    static public final String COMPONENT_FAMILY =
        "javax.faces.Panel";
    static public final String COMPONENT_TYPE =
        "org.apache.myfaces.ScheduleBase";


    public UIScheduleBase()
    {
        setRendererType(null);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
    

    // Property: splitWeekend
    private boolean _splitWeekend;
    
    private boolean _splitWeekendSet;
    
    public boolean isSplitWeekend()
    {
        if (_splitWeekendSet)
        {
            return _splitWeekend;
        }
        ValueBinding vb = getValueBinding("splitWeekend");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return true; 
    }

    public void setSplitWeekend(boolean splitWeekend)
    {
        this._splitWeekend = splitWeekend;
        this._splitWeekendSet = true;        
    }
    // Property: submitOnClick
    private boolean _submitOnClick;
    
    private boolean _submitOnClickSet;
    
    public boolean isSubmitOnClick()
    {
        if (_submitOnClickSet)
        {
            return _submitOnClick;
        }
        ValueBinding vb = getValueBinding("submitOnClick");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return false; 
    }

    public void setSubmitOnClick(boolean submitOnClick)
    {
        this._submitOnClick = submitOnClick;
        this._submitOnClickSet = true;        
    }
    // Property: compactMonthRowHeight
    private int _compactMonthRowHeight;
    
    private boolean _compactMonthRowHeightSet;
    
    public int getCompactMonthRowHeight()
    {
        if (_compactMonthRowHeightSet)
        {
            return _compactMonthRowHeight;
        }
        ValueBinding vb = getValueBinding("compactMonthRowHeight");
        if (vb != null)
        {
            return ((Integer) vb.getValue(getFacesContext())).intValue();
        }
        return 120; 
    }

    public void setCompactMonthRowHeight(int compactMonthRowHeight)
    {
        this._compactMonthRowHeight = compactMonthRowHeight;
        this._compactMonthRowHeightSet = true;        
    }
    // Property: compactWeekRowHeight
    private int _compactWeekRowHeight;
    
    private boolean _compactWeekRowHeightSet;
    
    public int getCompactWeekRowHeight()
    {
        if (_compactWeekRowHeightSet)
        {
            return _compactWeekRowHeight;
        }
        ValueBinding vb = getValueBinding("compactWeekRowHeight");
        if (vb != null)
        {
            return ((Integer) vb.getValue(getFacesContext())).intValue();
        }
        return 200; 
    }

    public void setCompactWeekRowHeight(int compactWeekRowHeight)
    {
        this._compactWeekRowHeight = compactWeekRowHeight;
        this._compactWeekRowHeightSet = true;        
    }
    // Property: converter
    private Converter _converter;
    
    public Converter getConverter()
    {
        if (_converter != null)
        {
            return _converter;
        }
        ValueBinding vb = getValueBinding("converter");
        if (vb != null)
        {
            return (Converter) vb.getValue(getFacesContext());
        }
        return null;
    }

    public void setConverter(Converter converter)
    {
        this._converter = converter;
    }
    // Property: detailedRowHeight
    private int _detailedRowHeight;
    
    private boolean _detailedRowHeightSet;
    
    public int getDetailedRowHeight()
    {
        if (_detailedRowHeightSet)
        {
            return _detailedRowHeight;
        }
        ValueBinding vb = getValueBinding("detailedRowHeight");
        if (vb != null)
        {
            return ((Integer) vb.getValue(getFacesContext())).intValue();
        }
        return 22; 
    }

    public void setDetailedRowHeight(int detailedRowHeight)
    {
        this._detailedRowHeight = detailedRowHeight;
        this._detailedRowHeightSet = true;        
    }
    // Property: expandToFitEntries
    private boolean _expandToFitEntries;
    
    private boolean _expandToFitEntriesSet;
    
    public boolean isExpandToFitEntries()
    {
        if (_expandToFitEntriesSet)
        {
            return _expandToFitEntries;
        }
        ValueBinding vb = getValueBinding("expandToFitEntries");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return false; 
    }

    public void setExpandToFitEntries(boolean expandToFitEntries)
    {
        this._expandToFitEntries = expandToFitEntries;
        this._expandToFitEntriesSet = true;        
    }
    // Property: headerDateFormat
    private String _headerDateFormat;
    
    public String getHeaderDateFormat()
    {
        if (_headerDateFormat != null)
        {
            return _headerDateFormat;
        }
        ValueBinding vb = getValueBinding("headerDateFormat");
        if (vb != null)
        {
            return (String) vb.getValue(getFacesContext()).toString();
        }
        return null;
    }

    public void setHeaderDateFormat(String headerDateFormat)
    {
        this._headerDateFormat = headerDateFormat;
    }
    
    
 // Property: headerDateFormat
    private String _ajoutPossible;
    
    public String getAjoutPossible()
    {
        if (_ajoutPossible != null)
        {
            return _ajoutPossible;
        }
        ValueBinding vb = getValueBinding("ajoutPossible");
        if (vb != null)
        {
            return (String) vb.getValue(getFacesContext()).toString();
        }
        return null;
    }

    public void setAjoutPossible(String ajoutPossible)
    {
        this._ajoutPossible = ajoutPossible;
    }
    
    
    private String _planRoute;
    
    public String getPlanRoute()
    {
        if (_planRoute != null)
        {
            return _planRoute;
        }
        ValueBinding vb = getValueBinding("planRoute");
        if (vb != null)
        {
            return (String) vb.getValue(getFacesContext()).toString();
        }
        return null;
    }

    public void setPlanRoute(String planRoute)
    {
        this._planRoute = planRoute;
    }
    
    
    
    
    // Property: afficherCaseACocher
    private boolean _afficherCaseACocher;
    
    private boolean _afficherCaseACocherSet;
    
    public boolean isAfficherCaseACocher()
    {
        if (_afficherCaseACocherSet)
        {
            return _afficherCaseACocher;
        }
        ValueBinding vb = getValueBinding("afficherCaseACocher");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return false; 
    }

    public void setAfficherCaseACocher(boolean afficherCaseACocher)
    {
        this._afficherCaseACocher = afficherCaseACocher;
        this._afficherCaseACocherSet = true;        
    }
    
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Property: immediate
    private boolean _immediate;
    
    private boolean _immediateSet;
    
    public boolean isImmediate()
    {
        if (_immediateSet)
        {
            return _immediate;
        }
        ValueBinding vb = getValueBinding("immediate");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return false; 
    }

    public void setImmediate(boolean immediate)
    {
        this._immediate = immediate;
        this._immediateSet = true;        
    }
    
    
    
    
    // Property: readonly
    private boolean _readonly;
    
    private boolean _readonlySet;
    
    public boolean isReadonly()
    {
        if (_readonlySet)
        {
            return _readonly;
        }
        ValueBinding vb = getValueBinding("readonly");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return false; 
    }

    public void setReadonly(boolean readonly)
    {
        this._readonly = readonly;
        this._readonlySet = true;        
    }
    // Property: renderZeroLengthEntries
    private boolean _renderZeroLengthEntries;
    
    private boolean _renderZeroLengthEntriesSet;
    
    public boolean isRenderZeroLengthEntries()
    {
        if (_renderZeroLengthEntriesSet)
        {
            return _renderZeroLengthEntries;
        }
        ValueBinding vb = getValueBinding("renderZeroLengthEntries");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return false;
    }

    public void setRenderZeroLengthEntries(boolean renderZeroLengthEntries)
    {
        this._renderZeroLengthEntries = renderZeroLengthEntries;
        this._renderZeroLengthEntriesSet = true;        
    }
    // Property: theme
    private String _theme;
    
    public String getTheme()
    {
        if (_theme != null)
        {
            return _theme;
        }
        ValueBinding vb = getValueBinding("theme");
        if (vb != null)
        {
            return (String) vb.getValue(getFacesContext()).toString();
        }
        return "default"; 
    }

    public void setTheme(String theme)
    {
        this._theme = theme;
    }
    // Property: tooltip
    private boolean _tooltip;
    
    private boolean _tooltipSet;
    
    public boolean isTooltip()
    {
        if (_tooltipSet)
        {
            return _tooltip;
        }
        ValueBinding vb = getValueBinding("tooltip");
        if (vb != null)
        {
            return ((Boolean) vb.getValue(getFacesContext())).booleanValue();
        }
        return false; 
    }

    public void setTooltip(boolean tooltip)
    {
        this._tooltip = tooltip;
        this._tooltipSet = true;        
    }
    // Property: value
    private Object _value;
    
    final public Object getLocalValue()
    {
        return _value;
    }
     
    public Object getValue()
    {
        if (_value != null)
        {
            return _value;
        }
        ValueBinding vb = getValueBinding("value");
        if (vb != null)
        {
            return  vb.getValue(getFacesContext());
        }
        return null;
    }

    public void setValue(Object value)
    {
        this._value = value;
    }
    // Property: visibleEndHour
    private int _visibleEndHour;
    
    private boolean _visibleEndHourSet;
    
    public int getVisibleEndHour()
    {
        if (_visibleEndHourSet)
        {
            return _visibleEndHour;
        }
        ValueBinding vb = getValueBinding("visibleEndHour");
        if (vb != null)
        {
            return ((Integer) vb.getValue(getFacesContext())).intValue();
        }
        return 20; 
    }

    public void setVisibleEndHour(int visibleEndHour)
    {
        this._visibleEndHour = visibleEndHour;
        this._visibleEndHourSet = true;        
    }
    // Property: visibleStartHour
    private int _visibleStartHour;
    
    private boolean _visibleStartHourSet;
    
    public int getVisibleStartHour()
    {
        if (_visibleStartHourSet)
        {
            return _visibleStartHour;
        }
        ValueBinding vb = getValueBinding("visibleStartHour");
        if (vb != null)
        {
            return ((Integer) vb.getValue(getFacesContext())).intValue();
        }
        return 8; 
    }

    public void setVisibleStartHour(int visibleStartHour)
    {
        this._visibleStartHour = visibleStartHour;
        this._visibleStartHourSet = true;        
    }
    // Property: workingEndHour
    private int _workingEndHour;
    
    private boolean _workingEndHourSet;
    
    public int getWorkingEndHour()
    {
        if (_workingEndHourSet)
        {
            return _workingEndHour;
        }
        ValueBinding vb = getValueBinding("workingEndHour");
        if (vb != null)
        {
            return ((Integer) vb.getValue(getFacesContext())).intValue();
        }
        return 17; 
    }

    public void setWorkingEndHour(int workingEndHour)
    {
        this._workingEndHour = workingEndHour;
        this._workingEndHourSet = true;        
    }
    // Property: workingStartHour
    private int _workingStartHour;
    
    private boolean _workingStartHourSet;
    
    public int getWorkingStartHour()
    {
        if (_workingStartHourSet)
        {
            return _workingStartHour;
        }
        ValueBinding vb = getValueBinding("workingStartHour");
        if (vb != null)
        {
            return ((Integer) vb.getValue(getFacesContext())).intValue();
        }
        return 9; 
    }

    public void setWorkingStartHour(int workingStartHour)
    {
        this._workingStartHour = workingStartHour;
        this._workingStartHourSet = true;        
    }
    // Property: hourNotation
    private String _hourNotation;
    
    public String getHourNotation()
    {
        if (_hourNotation != null)
        {
            return _hourNotation;
        }
        ValueBinding vb = getValueBinding("hourNotation");
        if (vb != null)
        {
            return (String) vb.getValue(getFacesContext()).toString();
        }
        return null;
    }

    public void setHourNotation(String hourNotation)
    {
        this._hourNotation = hourNotation;
    }
    // Property: compactMonthDayOfWeekDateFormat
    private String _compactMonthDayOfWeekDateFormat;
    
    public String getCompactMonthDayOfWeekDateFormat()
    {
        if (_compactMonthDayOfWeekDateFormat != null)
        {
            return _compactMonthDayOfWeekDateFormat;
        }
        ValueBinding vb = getValueBinding("compactMonthDayOfWeekDateFormat");
        if (vb != null)
        {
            return (String) vb.getValue(getFacesContext()).toString();
        }
        return null;
    }

    public void setCompactMonthDayOfWeekDateFormat(String compactMonthDayOfWeekDateFormat)
    {
        this._compactMonthDayOfWeekDateFormat = compactMonthDayOfWeekDateFormat;
    }

    public Object saveState(FacesContext facesContext)
    {
        Object[] values = new Object[39];
        values[0] = super.saveState(facesContext);
        values[1] = Boolean.valueOf(_splitWeekend);
        values[2] = Boolean.valueOf(_splitWeekendSet);
        values[3] = Boolean.valueOf(_submitOnClick);
        values[4] = Boolean.valueOf(_submitOnClickSet);
        values[5] = new Integer(_compactMonthRowHeight);
        values[6] = Boolean.valueOf(_compactMonthRowHeightSet);
        values[7] = new Integer(_compactWeekRowHeight);
        values[8] = Boolean.valueOf(_compactWeekRowHeightSet);
        values[9] = _converter;
        values[10] = new Integer(_detailedRowHeight);
        values[11] = Boolean.valueOf(_detailedRowHeightSet);
        values[12] = Boolean.valueOf(_expandToFitEntries);
        values[13] = Boolean.valueOf(_expandToFitEntriesSet);
        values[14] = _headerDateFormat;
        values[15] = Boolean.valueOf(_immediate);
        values[16] = Boolean.valueOf(_immediateSet);
        values[17] = Boolean.valueOf(_readonly);
        values[18] = Boolean.valueOf(_readonlySet);
        values[19] = Boolean.valueOf(_renderZeroLengthEntries);
        values[20] = Boolean.valueOf(_renderZeroLengthEntriesSet);
        values[21] = _theme;
        values[22] = Boolean.valueOf(_tooltip);
        values[23] = Boolean.valueOf(_tooltipSet);
        values[24] = _value;
        values[25] = new Integer(_visibleEndHour);
        values[26] = Boolean.valueOf(_visibleEndHourSet);
        values[27] = new Integer(_visibleStartHour);
        values[28] = Boolean.valueOf(_visibleStartHourSet);
        values[29] = new Integer(_workingEndHour);
        values[30] = Boolean.valueOf(_workingEndHourSet);
        values[31] = new Integer(_workingStartHour);
        values[32] = Boolean.valueOf(_workingStartHourSet);
        values[33] = _hourNotation;
        values[34] = _compactMonthDayOfWeekDateFormat;
        values[35] = _ajoutPossible;
        values[36] = _planRoute;
        values[37] = Boolean.valueOf(_afficherCaseACocher);
        values[38] = Boolean.valueOf(_afficherCaseACocherSet);
        return values; 
    }

    public void restoreState(FacesContext facesContext, Object state)
    {
        Object[] values = (Object[])state;
        super.restoreState(facesContext,values[0]);
        _splitWeekend = ((Boolean) values[1]).booleanValue();
        _splitWeekendSet = ((Boolean) values[2]).booleanValue();
        _submitOnClick = ((Boolean) values[3]).booleanValue();
        _submitOnClickSet = ((Boolean) values[4]).booleanValue();
        _compactMonthRowHeight = ((Integer) values[5]).intValue();
        _compactMonthRowHeightSet = ((Boolean) values[6]).booleanValue();
        _compactWeekRowHeight = ((Integer) values[7]).intValue();
        _compactWeekRowHeightSet = ((Boolean) values[8]).booleanValue();
        _converter = (Converter) restoreAttachedState(facesContext,values[9]);
        _detailedRowHeight = ((Integer) values[10]).intValue();
        _detailedRowHeightSet = ((Boolean) values[11]).booleanValue();
        _expandToFitEntries = ((Boolean) values[12]).booleanValue();
        _expandToFitEntriesSet = ((Boolean) values[13]).booleanValue();
        _headerDateFormat = (java.lang.String) values[14];
        _immediate = ((Boolean) values[15]).booleanValue();
        _immediateSet = ((Boolean) values[16]).booleanValue();
        _readonly = ((Boolean) values[17]).booleanValue();
        _readonlySet = ((Boolean) values[18]).booleanValue();
        _renderZeroLengthEntries = ((Boolean) values[19]).booleanValue();
        _renderZeroLengthEntriesSet = ((Boolean) values[20]).booleanValue();
        _theme = (java.lang.String) values[21];
        _tooltip = ((Boolean) values[22]).booleanValue();
        _tooltipSet = ((Boolean) values[23]).booleanValue();
        _value =  values[24];
        _visibleEndHour = ((Integer) values[25]).intValue();
        _visibleEndHourSet = ((Boolean) values[26]).booleanValue();
        _visibleStartHour = ((Integer) values[27]).intValue();
        _visibleStartHourSet = ((Boolean) values[28]).booleanValue();
        _workingEndHour = ((Integer) values[29]).intValue();
        _workingEndHourSet = ((Boolean) values[30]).booleanValue();
        _workingStartHour = ((Integer) values[31]).intValue();
        _workingStartHourSet = ((Boolean) values[32]).booleanValue();
        _hourNotation = (java.lang.String) values[33];
        _compactMonthDayOfWeekDateFormat = (java.lang.String) values[34];
        _ajoutPossible = (java.lang.String) values[35];
        _planRoute = (java.lang.String) values[36];
        _afficherCaseACocher = ((Boolean) values[37]).booleanValue();
        _afficherCaseACocherSet = ((Boolean) values[38]).booleanValue();
    }
}
