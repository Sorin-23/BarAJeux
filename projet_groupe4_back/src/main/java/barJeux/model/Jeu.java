package barJeux.model;

import java.util.List;

public class Jeu {
	
	private Integer id;
	private String nom;
	private List<typeJeu> typeJeux; // plateau carte dés
	private int ageMinimum;
	private int nbJoueurMinimum;
	private int nbJoueurMaximum;
	private int duree;
	private int nbExemplaire;
	private double note;
	private List<categorieJeu> categorieJeux;
	private String imgURL;
	private boolean besoinGameMaster;
	
	// Constructors
	public Jeu(Integer id, String nom, List<barJeux.model.typeJeu> typeJeux, int ageMinimum, int nbJoueurMinimum,
			int nbJoueurMaximum, int duree, int nbExemplaire, double note,
			List<barJeux.model.categorieJeu> categorieJeux, String imgURL, boolean besoinGameMaster ) {
		this.id = id;
		this.nom = nom;
		this.typeJeux = typeJeux;
		this.ageMinimum = ageMinimum;
		this.nbJoueurMinimum = nbJoueurMinimum;
		this.nbJoueurMaximum = nbJoueurMaximum;
		this.duree = duree;
		this.nbExemplaire = nbExemplaire;
		this.note = note;
		this.categorieJeux = categorieJeux;
		this.imgURL = imgURL;
		this.besoinGameMaster = besoinGameMaster;
	}
	public Jeu(String nom, List<barJeux.model.typeJeu> typeJeux, int ageMinimum, int nbJoueurMinimum,
			int nbJoueurMaximum, int duree, int nbExemplaire, double note,
			List<barJeux.model.categorieJeu> categorieJeux, String imgURL,  boolean besoinGameMaster) {
		this.nom = nom;
		this.typeJeux = typeJeux;
		this.ageMinimum = ageMinimum;
		this.nbJoueurMinimum = nbJoueurMinimum;
		this.nbJoueurMaximum = nbJoueurMaximum;
		this.duree = duree;
		this.nbExemplaire = nbExemplaire;
		this.note = note;
		this.categorieJeux = categorieJeux;
		this.imgURL = imgURL;
		this.besoinGameMaster = besoinGameMaster;
	}
	
	
	// Getters & Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public List<typeJeu> getTypeJeux() {
		return typeJeux;
	}
	public void setTypeJeux(List<typeJeu> typeJeux) {
		this.typeJeux = typeJeux;
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
	public List<categorieJeu> getCategorieJeux() {
		return categorieJeux;
	}
	public void setCategorieJeux(List<categorieJeu> categorieJeux) {
		this.categorieJeux = categorieJeux;
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
	
	// Méthodes utilitaires 
	public boolean estDisponible(List<Emprunt> empruntsActifs, List<Reservation> reservationsActives) {
	    int enUtilisation = 0;
	    
	    if (empruntsActifs != null) {
	        for (Emprunt e : empruntsActifs) {
	            if (e.getJeu().equals(this) &&
	                (e.getStatutLocation() == StatutLocation.enCours || e.getStatutLocation() == StatutLocation.enRetard)) {
	                enUtilisation++;
	            }
	        }
	    }
	    
	    if (reservationsActives != null) {
	        for (Reservation r : reservationsActives) {
	            if (r.getJeu().equals(this) && r.getStatutReservation() == StatutReservation.confirmée) {
	                enUtilisation++;
	            }
	        }
	    }
	    
	    return enUtilisation < nbExemplaire;
	}
	public void retirerExemplaires(int i) {
		// TODO Auto-generated method stub
		this.nbExemplaire--;
		
	}
	public void ajouterExemplaires(int i) {
		this.nbExemplaire++;
	}

	
	// toString
	@Override
	public String toString() {
		return "Jeux [id=" + id + ", nom=" + nom + ", typeJeux=" + typeJeux + ", ageMinimum=" + ageMinimum
				+ ", nbJoueurMinimum=" + nbJoueurMinimum + ", nbJoueurMaximum=" + nbJoueurMaximum + ", duree=" + duree
				+ ", nbExemplaire=" + nbExemplaire + ", note=" + note + ", categorieJeux=" + categorieJeux + "]";
	}
	
	

}
