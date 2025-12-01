package projet_groupe4.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutReservation;

public class ReservationResponse {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime datetimeDebut;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime datetimeFin;

    private int nbJoueur;

    private ClientResponse client;

    private EmployeResponse gameMaster;

    private JeuResponse jeu;

    private StatutReservation statutReservation;

    private Integer tableId; 

     public Integer getTableId() { return tableId; }


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

    

	public JeuResponse getJeu() {
		return jeu;
	}

	public void setJeu(JeuResponse jeu) {
		this.jeu = jeu;
	}

	public ClientResponse getClient() {
		return client;
	}

	public void setClient(ClientResponse client) {
		this.client = client;
	}

	public EmployeResponse getGameMaster() {
		return gameMaster;
	}

	public void setGameMaster(EmployeResponse gameMaster) {
		this.gameMaster = gameMaster;
	}

	public StatutReservation getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(StatutReservation statutReservation) {
        this.statutReservation = statutReservation;
    }

    

    public static ReservationResponse convert(Reservation reservation) {
        ReservationResponse resp = new ReservationResponse();
        
        resp.id = reservation.getId();
        resp.datetimeDebut = reservation.getDatetimeDebut();
        resp.datetimeFin = reservation.getDatetimeFin();
        resp.nbJoueur = reservation.getNbJoueur();
        resp.tableId = reservation.getTableJeu() != null ? reservation.getTableJeu().getId() : null;
        resp.statutReservation = reservation.getStatutReservation();
        if (reservation.getJeu() != null) {
            resp.setJeu(JeuResponse.convert(reservation.getJeu())); 
        }

        return resp;

    }
}