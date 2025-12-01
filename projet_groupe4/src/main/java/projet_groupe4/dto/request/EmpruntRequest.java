package projet_groupe4.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import projet_groupe4.model.StatutLocation;

public class EmpruntRequest {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEmprunt;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRetour;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRetourReel;

    @NotNull
    private StatutLocation statutLocation;

    private Integer clientId;
    private Integer jeuId;

    public StatutLocation getStatutLocation() {
        return statutLocation;
    }

    public void setStatutLocation(StatutLocation statutLocation) {
        this.statutLocation = statutLocation;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getJeuId() {
        return jeuId;
    }

    public void setJeuId(Integer jeuId) {
        this.jeuId = jeuId;
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

    public LocalDate getDateRetourReel() {
        return dateRetourReel;
    }

    public void setDateRetourReel(LocalDate dateRetourReel) {
        this.dateRetourReel = dateRetourReel;
    }

}
