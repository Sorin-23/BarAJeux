package projet_groupe4.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import projet_groupe4.view.Views;
@Entity
@Table(name="jeu")
public class Jeu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;
	@Column(name="nom_jeu", columnDefinition="varchar(50)", nullable=false)
	@JsonView(Views.Common.class)
	private String nom;

	@ElementCollection(targetClass = TypeJeu.class)
	@CollectionTable(name = "jeu_types", joinColumns = @JoinColumn(name = "jeu_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "type_jeu", nullable = false)
	@JsonView(Views.Common.class)
	private Set<TypeJeu> typesJeux;

	@Column(name="age_minimum")
	@JsonView(Views.Common.class)
	private int ageMinimum;
	@Column(name="nombre_joueur_minimum")
	@JsonView(Views.Common.class)
	private int nbJoueurMinimum;
	@Column(name="nombre_joueur_maximum")
	@JsonView(Views.Common.class)
	private int nbJoueurMaximum;
	@Column(name="durée")
	@JsonView(Views.Common.class)
	private int duree;
	@Column(name="nombre_exemplaire")
	@JsonView(Views.Common.class)
	private int nbExemplaire;
	@Column(columnDefinition="DECIMAL(2,1) CHECK (note >= 0 AND note <= 5)")
	@JsonView(Views.Common.class)
	private double note;
	
	@ElementCollection(targetClass = CategorieJeu.class)
	@CollectionTable(name = "jeu_categories", joinColumns = @JoinColumn(name = "jeu_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "categorie", nullable = false) // juste le nom de la colonne
    @JsonView(Views.Common.class)
	private Set<CategorieJeu> categoriesJeux;
	@Column(name="img_url")
	@JsonView(Views.Common.class)
	private String imgURL;
	@Column(name="besoin_game_master")
	@JsonView(Views.Common.class)
	private boolean besoinGameMaster;
	
	// Constructors
	

	public Jeu(Integer id, String nom, Set<TypeJeu> typesJeux, int ageMinimum, int nbJoueurMinimum, int nbJoueurMaximum,
			int duree, int nbExemplaire, double note, Set<CategorieJeu> categoriesJeux, String imgURL,
			boolean besoinGameMaster) {
		this.id = id;
		this.nom = nom;
		this.typesJeux = typesJeux;
		this.ageMinimum = ageMinimum;
		this.nbJoueurMinimum = nbJoueurMinimum;
		this.nbJoueurMaximum = nbJoueurMaximum;
		this.duree = duree;
		this.nbExemplaire = nbExemplaire;
		this.note = note;
		this.categoriesJeux = categoriesJeux;
		this.imgURL = imgURL;
		this.besoinGameMaster = besoinGameMaster;
	}
	public Jeu(String nom, Set<TypeJeu> typesJeux, int ageMinimum, int nbJoueurMinimum, int nbJoueurMaximum, int duree,
			int nbExemplaire, double note, Set<CategorieJeu> categoriesJeux, String imgURL, boolean besoinGameMaster) {
		this.nom = nom;
		this.typesJeux = typesJeux;
		this.ageMinimum = ageMinimum;
		this.nbJoueurMinimum = nbJoueurMinimum;
		this.nbJoueurMaximum = nbJoueurMaximum;
		this.duree = duree;
		this.nbExemplaire = nbExemplaire;
		this.note = note;
		this.categoriesJeux = categoriesJeux;
		this.imgURL = imgURL;
		this.besoinGameMaster = besoinGameMaster;
	}

	
	
	public Jeu() {
        //TODO Auto-generated constructor stub
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
	public void retirerExemplaires(int i) {
		// TODO Auto-generated method stub
		this.nbExemplaire-=i;
		
	}
	public void ajouterExemplaires(int i) {
		this.nbExemplaire+=i;
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
	

	
	// toString
	@Override
	public String toString() {
		return "Jeu [id=" + id + ", nom=" + nom + ", typesJeux=" + typesJeux + ", ageMinimum=" + ageMinimum
				+ ", nbJoueurMinimum=" + nbJoueurMinimum + ", nbJoueurMaximum=" + nbJoueurMaximum + ", duree=" + duree
				+ ", nbExemplaire=" + nbExemplaire + ", note=" + note + ", categoriesJeux=" + categoriesJeux
				+ ", imgURL=" + imgURL + ", besoinGameMaster=" + besoinGameMaster + "]";
	}
	
	

}
