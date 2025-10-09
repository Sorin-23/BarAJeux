package barJeux.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends Personne {

	private int pointFidelite;
	private LocalDate dateCreation;
	private LocalDate dateLastConnexion;
	private LocalDate dateLastReservation;
	private List<Reservation> reservations;
	private List<Emprunt> emprunts; 
	private String ville;
	private String codePostale;
	private String adresse;

	// Constructor
	public Client(Integer id, String nom, String prenom, String mail, String mdp, String telephone, int pointFidelite,
			LocalDate dateCreation, LocalDate dateLastConnexion, LocalDate dateLastReservation, String ville, String codePostale, String adresse) {
		super(id, nom, prenom, mail, mdp, telephone);
		this.pointFidelite = pointFidelite;
		this.dateCreation = dateCreation;
		this.dateLastConnexion = dateLastConnexion;
		this.dateLastReservation = dateLastReservation;
		this.ville = ville;
		this.codePostale = codePostale;
		this.adresse = adresse;
	}
	public Client(String nom, String prenom, String mail, String mdp, String telephone, String ville, String codePostale, String adresse) {
	    super(nom, prenom, mail, mdp, telephone);
	    this.pointFidelite = 0;
	    this.dateCreation = LocalDate.now();
	    this.dateLastConnexion = LocalDate.now();
	    this.dateLastReservation = null;
	    this.reservations = new ArrayList();
	    this.emprunts = new ArrayList();
		this.ville = ville;
		this.codePostale = codePostale;
		this.adresse = adresse;
	}
	

	
	// Getters & setters
	public int getPointFidelite() {
		return pointFidelite;
	}

	public void setPointFidelite(int pointFidelite) {
		this.pointFidelite = pointFidelite;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDate getDateLastConnexion() {
		return dateLastConnexion;
	}

	public void setDateLastConnexion(LocalDate dateLastConnexion) {
		this.dateLastConnexion = dateLastConnexion;
	}

	public LocalDate getDateLastReservation() {
		return dateLastReservation;
	}

	public void setDateLastReservation(LocalDate dateLastReservation) {
		this.dateLastReservation = dateLastReservation;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Emprunt> getEmprunts() {
		return emprunts;
	}
	public void setEmprunts(List<Emprunt> emprunts) {
		this.emprunts = emprunts;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getCodePostale() {
		return codePostale;
	}
	public void setCodePostale(String codePostale) {
		this.codePostale = codePostale;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	// Méthodes utilitaires
	public void addReservation(Reservation r) {
		reservations.add(r);
		r.setClient(this);
	}

	public void annulerReservation(Reservation r) {
        if(reservations.contains(r)) {
            r.setStatutReservation(StatutReservation.annulée);
        }
    }
	
	public void addEmprunt(Emprunt e) {
	    emprunts.add(e);
	    e.setClient(this);
	}

	public void annulerEmprunt(Emprunt e) {
        if(emprunts.contains(e)) {
            e.setStatutLocation(StatutLocation.annulé); 
        }
    }
	
	public void mettreAJourConnexion() {
	    this.dateLastConnexion = LocalDate.now();
	}
	
	public void mettreAJourDerniereReservation() {
	    this.dateLastReservation = LocalDate.now();
	}
	
	public int calculerPointsFidelite() {
	    int points = 0;
	    // Chaque réservation vaut 1 point par heure de jeu 
	    for (Reservation r : reservations) {
	        long heures = java.time.Duration.between(r.getDatetimeDebut(), r.getDatetimeFin()).toHours();
	        points += heures;
	    }
	    // Chaque emprunt vaut 2 points par jeu emprunté
	    if(emprunts != null) {
	    	points += emprunts.size()*2;
	    }
	    this.pointFidelite = points;
	    return points;
	}
	
	public Badge getBadgeActuel(List<Badge> badges) {
        Badge current = null;
        for (Badge b : badges) {
            if (this.pointFidelite >= b.getPointMin()) {
                current = b; // garde le dernier badge atteint
            } else break;
        }
        return current;
    }

	public int pointsAvantProchainBadge(List<Badge> badges) {
	    // badges triés par pointMin croissant
	    for (Badge b : badges) {
	        if (this.pointFidelite < b.getPointMin()) {
	            return b.getPointMin() - this.pointFidelite;
	        }
	    }
	    return 0; // déjà tous les badges obtenus
	}
	
	public List<Reservation> getReservationsActives(StatutReservation statut) {
	    List<Reservation> actives = new ArrayList();
	    for (Reservation r : reservations) {
	        if (r.getStatutReservation() == statut) {
	            actives.add(r);
	        }
	    }
	    return actives;
	}
	
	public List<Emprunt> getEmpruntsStatut(StatutLocation statut) {
	    List<Emprunt> actifs = new ArrayList();
	    for (Emprunt e : emprunts) {
	        if (e.getStatutLocation() == statut) {
	            actifs.add(e);
	        }
	    }
	    return actifs;
	}


	// toString
	@Override
	public String toString() {
		return "Client [pointFidelite=" + pointFidelite 
				+ ", dateCreation=" + dateCreation 
				+ ", dateLastConnexion=" + dateLastConnexion
				+ ", dateLastReservation=" + dateLastReservation 
				+ ", nbReservations=" + reservations.size()
				+ ", nbEmprunts=" + emprunts.size()
				+ ", adresse=" + adresse
				+ ", ville=" + ville
				+ ", CP=" + codePostale
				+ "]";
	}
}
