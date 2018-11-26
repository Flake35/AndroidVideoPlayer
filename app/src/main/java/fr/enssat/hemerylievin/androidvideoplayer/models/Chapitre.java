package fr.enssat.hemerylievin.androidvideoplayer.models;

public class Chapitre {
    private int numero;
    private String titre;
    private double marque;

    public Chapitre(int numero, String titre, double marque) {
        this.numero = numero;
        this.titre = titre;
        this.marque = marque;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public double getMarque() {
        return marque;
    }

    public void setMarque(double marque) {
        this.marque = marque;
    }
}
