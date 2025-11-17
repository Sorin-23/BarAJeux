package projet_groupe4.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

public class ReservationRequest {

    @NotBlank
	private LocalDateTime datetimeDebut;
	@NotBlank
	private LocalDateTime datetimeFin;
	@NotBlank
	private int nbJoueur;

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
	
    
}
