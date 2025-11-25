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

    private Integer tableJeuId;
    private String tableJeuNom;

    private Integer jeuId;
    private String jeuNom;

    private Integer clientId;
    private String clientNom;

    private Integer gameMasterId;
    private String gameMasterNom;

    private StatutReservation statut;

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

    public Integer getTableJeuId() {
        return tableJeuId;
    }

    public void setTableJeuId(Integer tableJeuId) {
        this.tableJeuId = tableJeuId;
    }

    public String getTableJeuNom() {
        return tableJeuNom;
    }

    public void setTableJeuNom(String tableJeuNom) {
        this.tableJeuNom = tableJeuNom;
    }

    public Integer getJeuId() {
        return jeuId;
    }

    public void setJeuId(Integer jeuId) {
        this.jeuId = jeuId;
    }

    public String getJeuNom() {
        return jeuNom;
    }

    public void setJeuNom(String jeuNom) {
        this.jeuNom = jeuNom;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public Integer getGameMasterId() {
        return gameMasterId;
    }

    public void setGameMasterId(Integer gameMasterId) {
        this.gameMasterId = gameMasterId;
    }

    public String getGameMasterNom() {
        return gameMasterNom;
    }

    public void setGameMasterNom(String gameMasterNom) {
        this.gameMasterNom = gameMasterNom;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public static ReservationResponse convert(Reservation reservation) {
        ReservationResponse resp = new ReservationResponse();

        resp.setId(reservation.getId());
        resp.setDatetimeDebut(reservation.getDatetimeDebut());
        resp.setDatetimeFin(reservation.getDatetimeFin());
        resp.setNbJoueur(reservation.getNbJoueur());
        resp.setStatut(reservation.getStatutReservation());

        if (reservation.getTableJeu() != null) {
            resp.setTableJeuId(reservation.getTableJeu().getId());
            resp.setTableJeuNom(reservation.getTableJeu().getNomTable());
        }
        if (reservation.getJeu() != null) {
            resp.setJeuId(reservation.getJeu().getId());
            resp.setJeuNom(reservation.getJeu().getNom());
        }

        if (reservation.getClient() != null) {
            resp.setClientId(reservation.getClient().getId());
            resp.setClientNom(reservation.getClient().getNom());
        }

        if (reservation.getGameMaster() != null) {
            resp.setGameMasterId(reservation.getGameMaster().getId());
            resp.setGameMasterNom(reservation.getGameMaster().getNom());
        }

        return resp;

    }
}