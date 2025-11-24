package projet_groupe4.dto.response;

import java.time.LocalDate;

import projet_groupe4.model.Emprunt;
import projet_groupe4.model.StatutLocation;

public class EmpruntResponse {

    private Integer id;

    private LocalDate dateEmprunt;

    private LocalDate dateRetour;

    private LocalDate dateRetourReel;

    private StatutLocation statutLocation;

    private Integer clientId;

    private Integer jeuId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public StatutLocation getStatutLocation() {
        return statutLocation;
    }

    public void setStatutLocation(StatutLocation statutLocation) {
        this.statutLocation = statutLocation;
    }

    public static EmpruntResponse convert(Emprunt emprunt) {
        EmpruntResponse resp = new EmpruntResponse();

        resp.setId(emprunt.getId());
        resp.setDateEmprunt(emprunt.getDateEmprunt());
        resp.setDateRetour(emprunt.getDateRetour());
        resp.setDateRetourReel(emprunt.getDateRetourReel());
        resp.setStatutLocation(emprunt.getStatutLocation());

        if (emprunt.getClient() != null) {
            resp.setClientId(emprunt.getClient().getId());
        }
        if (emprunt.getJeu() != null) {
            resp.setJeuId(emprunt.getJeu().getId());
        }

        return resp;

    }

}
