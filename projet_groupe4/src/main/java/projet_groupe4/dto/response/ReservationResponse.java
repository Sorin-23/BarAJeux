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

    private TableResponse tableJeu;
    private JeuResponse jeu;
    private ClientResponse client;
    private EmployeResponse gameMaster;

    private StatutReservation statutReservation;

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

    
    public TableResponse getTableJeu() {
		return tableJeu;
	}

	public void setTableJeu(TableResponse tableJeu) {
		this.tableJeu = tableJeu;
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

        resp.setId(reservation.getId());
        resp.setDatetimeDebut(reservation.getDatetimeDebut());
        resp.setDatetimeFin(reservation.getDatetimeFin());
        resp.setNbJoueur(reservation.getNbJoueur());
        resp.setStatutReservation(reservation.getStatutReservation());

        if (reservation.getTableJeu() != null)
            resp.tableJeu = TableResponse.convert(reservation.getTableJeu());

        if (reservation.getJeu() != null)
            resp.jeu = JeuResponse.convert(reservation.getJeu());

        if (reservation.getClient() != null)
            resp.client = ClientResponse.convert(reservation.getClient());

        if (reservation.getGameMaster() != null)
            resp.gameMaster = EmployeResponse.convert(reservation.getGameMaster());

        return resp;

    }
}