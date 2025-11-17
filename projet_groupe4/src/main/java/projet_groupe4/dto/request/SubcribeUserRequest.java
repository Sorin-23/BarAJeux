package projet_groupe4.dto.request;
import jakarta.validation.constraints.NotBlank;
public class SubcribeUserRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String mail;
    @NotBlank
    private String mdp;
    private String telephone;
    
    public String getNom() {
      return nom;
    }
    public void setNom(String nom) {
      this.nom = nom;
    }
    public String getPrenom() {
      return prenom;
    }
    public void setPrenom(String prenom) {
      this.prenom = prenom;
    }
    public String getMail() {
      return mail;
    }
    public void setMail(String mail) {
      this.mail = mail;
    }
    public String getMdp() {
      return mdp;
    }
    public void setMdp(String mdp) {
      this.mdp = mdp;
    }
    public String getTelephone() {
      return telephone;
    }
    public void setTelephone(String telephone) {
      this.telephone = telephone;
    }

    
    
    
    /*this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.mdp = mdp;
		this.telephone = telephone; */

}
