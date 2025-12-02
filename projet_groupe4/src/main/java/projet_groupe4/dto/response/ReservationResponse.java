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

    private Integer tableID; 
    

    


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

        System.out.println(">>>>>>ICI problème<<<<<<<<<");

        resp.id = reservation.getId();
        resp.datetimeDebut = reservation.getDatetimeDebut();
        resp.datetimeFin = reservation.getDatetimeFin();
        resp.nbJoueur = reservation.getNbJoueur();
        resp.tableID = reservation.getTableJeu() != null ? reservation.getTableJeu().getId() : null;
        resp.statutReservation = reservation.getStatutReservation();
        System.out.println(reservation.getStatutReservation());

        /*if(reservation.getTableJeu() != null){
            resp.setTableJeu(TableResponse.convert(reservation.getTableJeu()));
        }*/

        if(reservation.getClient() != null){
            resp.setClient(ClientResponse.convert(reservation.getClient()));
        }

        if (reservation.getJeu() != null) {
            resp.setJeu(JeuResponse.convert(reservation.getJeu())); 
        }
        if (reservation.getGameMaster()!=null){
            resp.setGameMaster(EmployeResponse.convert(reservation.getGameMaster()));
        }

        return resp;

    }

    public Integer getTableID() {
        return tableID;
    }

    public void setTableID(Integer tableID) {
        this.tableID = tableID;
    }

    /*public TableResponse getTableJeu() {
        return tableJeu;
    }

    public void setTableJeu(TableResponse tableJeu) {
        this.tableJeu = tableJeu;
    }*/
}