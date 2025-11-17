package projet_groupe4.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;


public class SubcribeClientRequest {

	private int pointFidelite;
	@NotBlank
	private LocalDate dateCreation;
	@NotBlank
	private LocalDate dateLastConnexion;
	
	@NotBlank
	private String ville;
    @NotBlank
	private String codePostale;
    @NotBlank
	private String adresse;
    public int getPointFidelite() {
        return pointFidelite;
    }
    public void setPointFidelite(int pointFidelite) {
        this.pointFidelite = pointFidelite;
    }
    public LocalDate getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
    public LocalDate getDateLastConnexion() {
        return dateLastConnexion;
    }
    public void setDateLastConnexion(LocalDate dateLastConnexion) {
        this.dateLastConnexion = dateLastConnexion;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getCodePostale() {
        return codePostale;
    }
    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    



}
