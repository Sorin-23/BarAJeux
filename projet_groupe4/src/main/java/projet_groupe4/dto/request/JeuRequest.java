package projet_groupe4.dto.request;

import java.util.Set;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import projet_groupe4.model.CategorieJeu;
import projet_groupe4.model.TypeJeu;

public class JeuRequest {
    @NotBlank
    private String nom;

    @NotEmpty
    private Set<TypeJeu> typesJeux;

    @Min(0)
    private int ageMinimum;

    @Min(1)
    private int nbJoueurMinimum;

    @Min(1)
    private int nbJoueurMaximum;

    @Min(1)
    private int duree;

    @Min(0)
    private int nbExemplaire;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private double note;

    @NotEmpty
    private Set<CategorieJeu> categoriesJeux;

    private String imgURL;

    private boolean besoinGameMaster;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<TypeJeu> getTypesJeux() {
        return typesJeux;
    }

    public void setTypesJeux(Set<TypeJeu> typesJeux) {
        this.typesJeux = typesJeux;
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

    public Set<CategorieJeu> getCategoriesJeux() {
        return categoriesJeux;
    }

    public void setCategoriesJeux(Set<CategorieJeu> categoriesJeux) {
        this.categoriesJeux = categoriesJeux;
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

}
