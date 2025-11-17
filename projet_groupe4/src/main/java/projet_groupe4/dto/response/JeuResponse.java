package projet_groupe4.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.model.Jeu;
import projet_groupe4.view.Views;

public class JeuResponse {

    @JsonView(Views.Common.class)
    private Integer id;
    @JsonView(Views.Common.class)
    private String nom;

    @JsonView(Views.Common.class)
	private int ageMinimum;
    @JsonView(Views.Common.class)
	private int nbJoueurMinimum;
    @JsonView(Views.Common.class)
	private int nbJoueurMaximum;
    @JsonView(Views.Common.class)
	private int duree;
    @JsonView(Views.Common.class)
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

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public static JeuResponse convert(Jeu jeu){
        JeuResponse resp = new JeuResponse();
        resp.setId(jeu.getId());
        resp.setNom(jeu.getNom());
        resp.setAgeMinimum(jeu.getAgeMinimum());
        resp.setNbJoueurMinimum(jeu.getNbJoueurMinimum());
        resp.setNbJoueurMaximum(jeu.getNbJoueurMaximum());
        resp.setDuree(jeu.getDuree());
        resp.setNbExemplaire(jeu.getNbExemplaire());
        
        return resp;
        
    }
    

}
