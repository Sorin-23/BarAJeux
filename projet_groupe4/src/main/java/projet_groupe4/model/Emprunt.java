package projet_groupe4.model;

import java.time.DayOfWeek;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import projet_groupe4.view.Views;
@Entity
@Table(name="emprunt")
public class Emprunt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;
	
	@Column(name="date_emprunt", nullable=false)
	@JsonView(Views.Common.class)
	private LocalDate dateEmprunt;
	@Column(name="date_retour", nullable=false)
	@JsonView(Views.Common.class)
	private LocalDate dateRetour;
	@Enumerated(EnumType.STRING)
	@Column(name="statut_location",nullable = false,columnDefinition = "enum('rendu', 'enCours', 'enRetard', 'annulé')")
	@JsonView(Views.Common.class)
	private StatutLocation statutLocation;
	@Column(name="date_retour_reel")
	@JsonView(Views.Common.class)
	private LocalDate dateRetourReel;
	@ManyToOne
	@JoinColumn(name="client", nullable=false)
	@JsonView(Views.ClientWithEmprunt.class)
	private Client client ; 
	@ManyToOne
	@JoinColumn(name="jeu", nullable=false)
	@JsonView(Views.Emprunt.class)
	private Jeu jeu;
	
	// valeur générique 
	private static final int DUREE_EMPRUNT_STANDARD = 15;
	
	//Constructors
	public Emprunt() {}
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
