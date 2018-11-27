package fr.enssat.hemerylievin.androidvideoplayer.models;

public class Chapitre {
    private int numero;
    private String titre;
    private int marque;
    private int nextMarque;

    public Chapitre(int numero, String titre, int marque, int nextMarque) {
        this.numero = numero;
        this.titre = titre;
        this.marque = marque;
        this.nextMarque = nextMarque;
    }

    public Chapitre() {}

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

    public int getMarque() {
        return marque;
    }

    public void setMarque(int marque) {
        this.marque = marque;
    }

    public int getNextMarque() {
        return nextMarque;
    }

    public void setNextMarque(int nextMarque) {
        this.nextMarque = nextMarque;
    }
}
