package barJeux.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Emprunt {
	private Integer id;
	private LocalDate dateEmprunt;
	private LocalDate dateRetour;
	private StatutLocation statutLocation;
	private LocalDate dateRetourReel;
	private Client client ; 
	private Jeu jeu;
	
	// valeur générique 
	private static final int DUREE_EMPRUNT_STANDARD = 15;
	
	//Constructors
	public Emprunt(Integer id, LocalDate dateEmprunt, LocalDate dateRetour, StatutLocation statutLocation,
			LocalDate dateRetourReel, Client client, Jeu jeu) {
		this.id = id;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
		this.statutLocation = statutLocation;
		this.dateRetourReel = dateRetourReel;
		this.client = client;
		this.jeu = jeu;
	}

	public Emprunt(Client client, Jeu jeu) {
		this.dateEmprunt = LocalDate.now();
		this.dateRetour = calculerDateRetour(LocalDate.now());
		this.statutLocation = StatutLocation.enCours;
		this.dateRetourReel = null;
		this.client = client;
		this.jeu = jeu;
		
		client.addEmprunt(this);
	} // pour la création d'un emprunt

	// Getters & Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public LocalDate getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour = dateRetour;
	}
	public StatutLocation getStatutLocation() {
		return statutLocation;
	}
	public void setStatutLocation(StatutLocation statutLocation) {
		this.statutLocation = statutLocation;
	}
	public LocalDate getDateRetourReel() {
		return dateRetourReel;
	}
	public void setDateRetourReel(LocalDate dateRetourReel) {
		this.dateRetourReel = dateRetourReel;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Jeu getJeu() {
		return jeu;
	}
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	
	// Méthodes utilitaires 
	private LocalDate calculerDateRetour(LocalDate debut) {
        LocalDate retour = debut.plusDays(DUREE_EMPRUNT_STANDARD);
        if (retour.getDayOfWeek() == DayOfWeek.SUNDAY) {
            retour = retour.plusDays(1); // décale au lundi
        }
        return retour;
    }
	
    public void rendreJeu() {
        this.statutLocation = StatutLocation.rendu;
        this.dateRetourReel = LocalDate.now();
        // boutton avec option pour dire bien rendu coté admin 
    }

    public void verifierRetard() {

        if (this.statutLocation == StatutLocation.enCours && LocalDate.now().isAfter(dateRetour)) {
            this.statutLocation = StatutLocation.enRetard;

        }
    }
    

  
       
    
    
	// toString 
	@Override
	public String toString() {
		return "Emprunt [id=" + id + ", dateEmprunt=" + dateEmprunt + ", dateRetour=" + dateRetour + ", statutLocation="
				+ statutLocation + ", dateRetourReel=" + dateRetourReel + ", client=" + client.getNom() + " " + client.getPrenom()+ ", jeu=" + jeu.getNom()  + "]";
	}
	
	

}
