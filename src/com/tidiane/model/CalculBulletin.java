package com.tidiane.model;

import com.tidiane.model.Bulletin;
import com.tidiane.model.Matiere;
import com.tidiane.model.Notes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tidiane on 01/12/16.
 */
public class CalculBulletin {
    public CalculBulletin() {
    }
    private Bulletin bulletin = new Bulletin();
    private List<Notes> listNotes = new ArrayList<Notes>();
    private List<Matiere> listMatieres = new ArrayList<Matiere>();

    public Bulletin getBulletin() {
        return bulletin;
    }

    public void setBulletin(Bulletin bulletin) {
        this.bulletin = bulletin;
    }

    public List<Notes> getListNotes() {
        return listNotes;
    }

    public void setListNotes(List<Notes> listNotes) {
        this.listNotes = listNotes;
    }

    public List<Matiere> getListMatieres() {
        return listMatieres;
    }

    public void setListMatieres(List<Matiere> listMatieres) {
        this.listMatieres = listMatieres;
    }
}