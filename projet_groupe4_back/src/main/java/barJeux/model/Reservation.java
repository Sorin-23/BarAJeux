package barJeux.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class Reservation {
	
	private Integer id;
	private LocalDateTime datetimeDebut;
	private LocalDateTime datetimeFin;
	private int nbJoueur;
	private TableJeu table;
	private Jeu jeu;
	private StatutReservation statutReservation;
	private Client client;
	private Employe gameMaster;
	
	// Constructors
	public Reservation(Integer id, LocalDateTime datetimeDebut, LocalDateTime datetimeFin, int nbJoueur, TableJeu table,
			Jeu jeu, StatutReservation statutReservation, Client client, Employe gameMaster) {
		this.id = id;
		this.datetimeDebut = datetimeDebut;
		this.datetimeFin = datetimeFin;
		this.nbJoueur = nbJoueur;
		this.table = table;
		this.jeu = jeu;
		this.statutReservation = statutReservation;
		this.client = client;
		this.gameMaster = gameMaster;
	}
	public Reservation(LocalDateTime datetimeDebut, LocalDateTime datetimeFin, int nbJoueur, TableJeu table, Jeu jeu,
			 Client client, Employe gameMaster) {
		this.datetimeDebut = datetimeDebut;
		this.datetimeFin = datetimeFin;
		this.nbJoueur = nbJoueur;
		this.table = table;
		this.jeu = jeu;
		this.statutReservation = StatutReservation.confirmée;
		this.client = client;
		this.gameMaster = gameMaster;
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
	public TableJeu getTable() {
		return table;
	}
	public void setTable(TableJeu table) {
		this.table = table;
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

	public void confirmerResa() {
		
		if(!this.table.isDisponibilite()) {
			System.out.println("Table non disponible");
		}
		else {
			changerStatut(StatutReservation.confirmée);
			this.table.setDisponibilite(false);
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
			table.setDisponibilite(true);
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
		
		else if(!this.table.isDisponibilite()) {
			System.out.println("Table non disponible");
		}
		
		else {
			this.datetimeDebut = nvlDateDebut;
			this.datetimeFin = nvlDateFin;
		}
	}
	
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
				+ ", nbJoueur=" + nbJoueur + ", table=" + table.getNomTable() + ", jeu=" + jeu.getNom() + ", statutReservation="
				+ statutReservation + ", client=" + client.getNom() + " " + client.getPrenom() + ", gameMaster=" + (gameMaster != null ? gameMaster.getNom() + " " + gameMaster.getPrenom()  : "Pas de gameMaster") + "]";
	}
	
	
	
	
	
	
	

}
