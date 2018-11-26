package fr.enssat.hemerylievin.androidvideoplayer.models;

import java.util.ArrayList;

public class Chapitres {
    private ArrayList<Chapitre> chapitres;

    public Chapitres() {
        this.chapitres = new ArrayList<>();
    }

    public void add(Chapitre chapitre) {
        this.chapitres.add(chapitre);
    }

    public ArrayList<Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(ArrayList<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }

    public String toString() {
        String text = "";
        for (Chapitre chapitre : this.chapitres) {
            text = text + chapitre.getNumero() + chapitre.getTitre() + chapitre.getMarque();
        }
        return text;
    }
}
