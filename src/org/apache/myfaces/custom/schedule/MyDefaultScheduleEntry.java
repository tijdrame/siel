/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.custom.schedule;

import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;


/**
 * <p>
 * A default implementation of a Schedule entry
 * </p>
 *
 * @author Jurgen Lust (latest modification by $Author: tariq $)
 * @version $Revision: 1.1 $
 */
public class MyDefaultScheduleEntry
    extends DefaultScheduleEntry
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8783993952976228638L;
    
    private Long idPlageHoraire;
    
    /*
     * 0: intervention
     * 1: planning intervenant
     * 2: planning beneficiaire
     */
    private short modeAffichage;

	private String color;
	
	private String image;
	
	private String textColor;
	
	private String smallTitle;
	
	private boolean sansIntervenant;
	
	private boolean nuit;

	private boolean indisponibilite;
	
	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	private Boolean nonModifiable;

	public Boolean getNonModifiable() {
		return nonModifiable;
	}

	public void setNonModifiable(Boolean nonModifiable) {
		this.nonModifiable = nonModifiable;
	}

	public short getModeAffichage() {
		return modeAffichage;
	}

	public void setModeAffichage(short modeAffichage) {
		this.modeAffichage = modeAffichage;
	}

	public void setSmallTitle(String smallTitle) {
		this.smallTitle = smallTitle;
	}

	public String getSmallTitle() {
		return smallTitle;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setNuit(boolean nuit) {
		this.nuit = nuit;
	}

	public boolean isNuit() {
		return nuit;
	}

	public void setSansIntervenant(boolean sansIntervenant) {
		this.sansIntervenant = sansIntervenant;
	}

	public boolean isSansIntervenant() {
		return sansIntervenant;
	}
	
	
	public Long getIdPlageHoraire() {
		return idPlageHoraire;
	}

	public void setIdPlageHoraire(Long idPlageHoraire) {
		this.idPlageHoraire = idPlageHoraire;
	}

	public boolean isIndisponibilite() {
		return indisponibilite;
	}

	public void setIndisponibilite(boolean indisponibilite) {
		this.indisponibilite = indisponibilite;
	}
	
	

	
    
    
	
	
	
}
//The End
