package projet_groupe4.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.model.Client;
import projet_groupe4.model.Emprunt;
import projet_groupe4.model.Reservation;
import projet_groupe4.view.Views;

public class ClientResponse {

    @JsonView(Views.Common.class)
	private Integer id;
	
	@JsonView(Views.Common.class)
	private String nom;
	
	@JsonView(Views.Common.class)
	private String prenom;
	
	@JsonView(Views.Common.class)
	private String mail;
	
	@JsonView(Views.Common.class)
	private String mdp;

	
	@JsonView(Views.Common.class)
	private String telephone;

    @JsonView(Views.Common.class)
	private int pointFidelite;

    @JsonView(Views.Common.class)
	private LocalDate dateCreation;

    @JsonView(Views.Common.class)
	private LocalDate dateLastConnexion;

    @JsonView(Views.ClientWithReservation.class)
	private List<Reservation> reservations = new ArrayList();
    @JsonView(Views.ClientWithEmprunt.class)
	private List<Emprunt> emprunts = new ArrayList(); 
    @JsonView(Views.Common.class)
	private String ville;
    @JsonView(Views.Common.class)
	private String codePostale;
    @JsonView(Views.Common.class)
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
    public List<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    public List<Emprunt> getEmprunts() {
        return emprunts;
    }
    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
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
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
    public static ClientResponse convert(Client client){
        ClientResponse resp = new ClientResponse();

        resp.setId(client.getId());
        resp.setNom(client.getNom());
        resp.setPrenom(client.getPrenom());
        resp.setMail(client.getMail());
        resp.setMdp(client.getMdp());
        resp.setTelephone(client.getTelephone());
        resp.setPointFidelite(client.getPointFidelite());
        resp.setDateCreation(client.getDateCreation());
        resp.setDateLastConnexion(client.getDateLastConnexion());
        resp.setVille(client.getVille());
        resp.setCodePostale(client.getCodePostale());
        resp.setAdresse(client.getAdresse());

        return resp;
    }

}
