package projet_groupe4.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class EmpruntRequest {

@NotBlank
private LocalDate dateEmprunt;
@NotBlank
private LocalDate dateRetour;
@NotBlank
private LocalDate dateRetourReel;
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
