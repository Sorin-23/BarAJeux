package projet_groupe4.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import projet_groupe4.model.StatutReservation;

public class ReservationRequest {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime datetimeDebut;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime datetimeFin;

    @Min(1)
    private int nbJoueur;

    @NotNull
    private Integer tableJeuId;

    @NotNull
    private Integer jeuId;

    @NotNull
    private Integer clientId;
    
    private Integer gameMasterId;

    private StatutReservation statutReservation;

    public Integer getTableJeuId() {
        return tableJeuId;
    }

    public void setTableJeuId(Integer tableJeuId) {
        this.tableJeuId = tableJeuId;
    }

    public Integer getJeuId() {
        return jeuId;
    }

    public void setJeuId(Integer jeuId) {
        this.jeuId = jeuId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getGameMasterId() {
        return gameMasterId;
    }

    public void setGameMasterId(Integer gameMasterId) {
        this.gameMasterId = gameMasterId;
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

    public StatutReservation getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(StatutReservation statutReservation) {
        this.statutReservation = statutReservation;
    }

}
