package barJeux.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
@Entity
@Table(name="jeu")
public class Jeu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="nom_jeu", columnDefinition="varchar(50)", nullable=false)
	private String nom;

	@ElementCollection(targetClass = TypeJeu.class)
	@CollectionTable(name = "jeu_types", joinColumns = @JoinColumn(name = "jeu_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "type_jeu", nullable = false)
	private Set<TypeJeu> typesJeux;

	@Column(name="age_minimum")
	private int ageMinimum;
	@Column(name="nombre_joueur_minimum")
	private int nbJoueurMinimum;
	@Column(name="nombre_joueur_maximum")
	private int nbJoueurMaximum;
	@Column(name="durée")
	private int duree;
	@Column(name="nombre_exemplaire")
	private int nbExemplaire;
	@Column(columnDefinition="DECIMAL(2,1) CHECK (note >= 0 AND note <= 5)")
	private double note;
	
	@ElementCollection(targetClass = CategorieJeu.class)
	@CollectionTable(name = "jeu_categories", joinColumns = @JoinColumn(name = "jeu_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "categorie", nullable = false) // juste le nom de la colonne
    private Set<CategorieJeu> categoriesJeux;
	@Column(name="img_url")
	private String imgURL;
	@Column(name="besoin_game_master")
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
	
	public Jeu() {}

	
	
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
