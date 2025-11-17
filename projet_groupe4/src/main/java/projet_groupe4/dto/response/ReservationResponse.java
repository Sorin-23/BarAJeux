package projet_groupe4.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.view.Views;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutReservation;
import projet_groupe4.model.TableJeu;

public class ReservationResponse {

    @JsonView(Views.Common.class)
    private Integer id;
    @JsonView(Views.Common.class)
    private LocalDateTime datetimeDebut;
    @JsonView(Views.Common.class)
    private LocalDateTime datetimeFin;
    @JsonView(Views.Common.class)
    private int nbJoueur;

    @JsonView(Views.TableJeu.class)
    private TableJeu tableJeu;
    @JsonView(Views.Jeu.class)
    private Jeu jeu;
    @JsonView(Views.Common.class)
    private StatutReservation statutReservation;
    @JsonView(Views.ClientWithReservation.class)
    private Client client;
    @JsonView(Views.Employe.class)
    private Employe gameMaster;

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

    public static ReservationResponse convert(Reservation reservation) {
        ReservationResponse resp = new ReservationResponse();

        resp.setId(reservation.getId());
        resp.setDatetimeDebut(reservation.getDatetimeDebut());
        resp.setDatetimeFin(reservation.getDatetimeFin());
        resp.setNbJoueur(reservation.getNbJoueur());
        resp.setTableJeu(reservation.getTableJeu());
        resp.setJeu(reservation.getJeu());
        resp.setStatutReservation(reservation.getStatutReservation());
        resp.setClient(reservation.getClient());
        resp.setGameMaster(reservation.getGameMaster());

        return resp;

    }
}