package projet_groupe4.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.view.Views;
import projet_groupe4.model.Emprunt;

public class EmpruntResponse {


    @JsonView(Views.Common.class)
    private Integer id;
    @JsonView(Views.Common.class)
    private LocalDate dateEmprunt;
    @JsonView(Views.Common.class)
    private LocalDate dateRetour;
    @JsonView(Views.Common.class)
    private LocalDate dateRetourReel;
    
    @JsonView(Views.Common.class)
    private Integer clientId; 
    @JsonView(Views.Common.class)
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


    public static EmpruntResponse convert(Emprunt emprunt) {
        EmpruntResponse resp = new EmpruntResponse();

        resp.setId(emprunt.getId());
        resp.setDateEmprunt(emprunt.getDateEmprunt());
        resp.setDateRetour(emprunt.getDateRetour());
        resp.setDateRetourReel(emprunt.getDateRetourReel());
        resp.setClientId(emprunt.getClient().getId());
        resp.setJeuId(emprunt.getJeu().getId());
        
        

        return resp;

    }
}
