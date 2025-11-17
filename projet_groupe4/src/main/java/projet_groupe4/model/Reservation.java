package projet_groupe4.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
@Table(name="reservation")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;
	@Column(name="date_debut", nullable = false)
	@JsonView(Views.Common.class)
	private LocalDateTime datetimeDebut;
	@Column(name="date_fin", nullable = false)
	@JsonView(Views.Common.class)
	private LocalDateTime datetimeFin;
	@Column(name="nombre_joueur")
	@JsonView(Views.Common.class)
	private int nbJoueur;
	@ManyToOne
	@JoinColumn(name="table_jeu")
	@JsonView(Views.TableJeu.class)
	private TableJeu tableJeu;
	@ManyToOne// many car on gère un stock ? 
	@JoinColumn(name="jeu", nullable=false)
	@JsonView(Views.Jeu.class)
	private Jeu jeu;
	@Enumerated(EnumType.STRING)
	@Column(name="statut_reservation",nullable = false,columnDefinition = "enum('terminée', 'annulée', 'confirmée')")
	@JsonView(Views.Common.class)
	private StatutReservation statutReservation;
	@ManyToOne
	@JoinColumn(name="client", nullable=false)
	@JsonView(Views.ClientWithReservation.class)
	private Client client;
	@ManyToOne
	@JoinColumn(name="game_master", nullable=false)
	@JsonView(Views.Employe.class)
	private Employe gameMaster;
	
	// Constructors
	public Reservation(Integer id, LocalDateTime datetimeDebut, LocalDateTime datetimeFin, int nbJoueur, TableJeu tableJeu,
			Jeu jeu, StatutReservation statutReservation, Client client, Employe gameMaster) {
		this.id = id;
		this.datetimeDebut = datetimeDebut;
		this.datetimeFin = datetimeFin;
		this.nbJoueur = nbJoueur;
		this.tableJeu = tableJeu;
		this.jeu = jeu;
		this.statutReservation = statutReservation;
		this.client = client;
		this.gameMaster = gameMaster;
	}
	public Reservation(LocalDateTime datetimeDebut, LocalDateTime datetimeFin, int nbJoueur, TableJeu tableJeu, Jeu jeu,
			 Client client, Employe gameMaster) {
		this.datetimeDebut = datetimeDebut;
		this.datetimeFin = datetimeFin;
		this.nbJoueur = nbJoueur;
		this.tableJeu = tableJeu;
		this.jeu = jeu;
		this.statutReservation = StatutReservation.confirmée;
		this.client = client;
		this.gameMaster = gameMaster;
	}
	
	public Reservation() {
        //TODO Auto-generated constructor stub
    }
    // Getters & Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getDatetimeDebut() {
		return datetimeDebut;
	}
	public void setDatetimeDebut(LocalDateTime datetimeDebut) {
		this.datetimeDebut = datetimeDebut;
	}
	public LocalDateTime getDatetimeFin() {
		return datetimeFin;
	}
	public void setDatetimeFin(LocalDateTime datetimeFin) {
		this.datetimeFin = datetimeFin;
	}
	public int getNbJoueur() {
		return nbJoueur;
	}
	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}
	public TableJeu getTableJeu() {
		return tableJeu;
	}
	public void setTableJeu(TableJeu tableJeu) {
		this.tableJeu = tableJeu;
	}
	public Jeu getJeu() {
		return jeu;
	}
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	public StatutReservation getStatutReservation() {
		return statutReservation;
	}
	public void setStatutReservation(StatutReservation statutReservation) {
		this.statutReservation = statutReservation;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Employe getGameMaster() {
		return gameMaster;
	}
	public void setGameMaster(Employe gameMaster) {
		this.gameMaster = gameMaster;
	}
	
	// Méthodes utilitaires
	public void annuler() { 
		this.statutReservation = StatutReservation.annulée;
	}
	public void terminer() {
		this.statutReservation = StatutReservation.terminée; 
	}

	/*public void confirmerResa() {
		
		if(!this.tableJeu.isDisponibilite()) {
			System.out.println("Table non disponible");
		}
		else {
			changerStatut(StatutReservation.confirmée);
			this.tableJeu.setDisponibilite(false);
			this.jeu.retirerExemplaires(1);
			
			System.out.println("Reservation confirmée avec succès");
		}
	}
	
	public void annulerResa() {
		if(this.statutReservation == StatutReservation.annulée) {
			System.out.println("Reservation deja annulée");
		}
		else {
			changerStatut(StatutReservation.annulée);
			tableJeu.setDisponibilite(true);
			jeu.ajouterExemplaires(1);
			
			System.out.println("Reservation annulée avec succès");
		}
	}
	
	public void modifierResa(LocalDateTime nvlDateDebut, LocalDateTime nvlDateFin) {
		
		if(nvlDateDebut == null || nvlDateFin == null) {
			System.out.println("Dates invalides");
		}
		
		else if(nvlDateFin.compareTo(nvlDateDebut) < 0) {
			System.out.println("Date fin doit etre apres date debut");
		}
		
		else if(!this.tableJeu.isDisponibilite()) {
			System.out.println("Table non disponible");
		}
		
		else {
			this.datetimeDebut = nvlDateDebut;
			this.datetimeFin = nvlDateFin;
		}
	}*/
	
	public int calculerDuree() {
		return (int) ChronoUnit.DAYS.between(this.datetimeDebut, this.datetimeFin);
	}
	
	public void changerStatut(StatutReservation statut) {
		if (statut == null) {
	        throw new IllegalArgumentException("Statut invalide");
	    }

	    if (this.statutReservation != statut) {
	        this.statutReservation = statut;
	    }
	}

	// toString
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", datetimeDebut=" + datetimeDebut + ", datetimeFin=" + datetimeFin
				+ ", nbJoueur=" + nbJoueur + ", table=" + tableJeu.getNomTable() + ", jeu=" + jeu.getNom() + ", statutReservation="
				+ statutReservation + ", client=" + client.getNom() + " " + client.getPrenom() + ", gameMaster=" + (gameMaster != null ? gameMaster.getNom() + " " + gameMaster.getPrenom()  : "Pas de gameMaster") + "]";
	}
	
	
	
	
	
	
	

}
