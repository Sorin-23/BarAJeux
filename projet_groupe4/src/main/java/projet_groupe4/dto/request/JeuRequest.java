package projet_groupe4.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JeuRequest {
    @NotBlank
	private String nom;

    @NotNull
    @Min(0)
	private int ageMinimum;
    @NotNull
    @Min(1)
	private int nbJoueurMinimum;
    @NotNull
    @Min(1)
	private int nbJoueurMaximum;
    @NotNull
    @Min(1)
	private int duree;
    @NotNull
    @Min(1)
	private int nbExemplaire;
	
	public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAgeMinimum() {
        return ageMinimum;
    }

    public void setAgeMinimum(int ageMinimum) {
        this.ageMinimum = ageMinimum;
    }

    public int getNbJoueurMinimum() {
        return nbJoueurMinimum;
    }

    public void setNbJoueurMinimum(int nbJoueurMinimum) {
        this.nbJoueurMinimum = nbJoueurMinimum;
    }

    public int getNbJoueurMaximum() {
        return nbJoueurMaximum;
    }

    public void setNbJoueurMaximum(int nbJoueurMaximum) {
        this.nbJoueurMaximum = nbJoueurMaximum;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNbExemplaire() {
        return nbExemplaire;
    }

    public void setNbExemplaire(int nbExemplaire) {
        this.nbExemplaire = nbExemplaire;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public boolean isBesoinGameMaster() {
        return besoinGameMaster;
    }

    public void setBesoinGameMaster(boolean besoinGameMaster) {
        this.besoinGameMaster = besoinGameMaster;
    }

    private double note;
	
	private String imgURL;
	
	private boolean besoinGameMaster;

    



}
