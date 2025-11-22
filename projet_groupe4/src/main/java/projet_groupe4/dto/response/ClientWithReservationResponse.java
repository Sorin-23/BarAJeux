package projet_groupe4.dto.response;

import java.util.List;

import projet_groupe4.model.Client;

public class ClientWithReservationResponse {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private List<ReservationResponse> reservations;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public List<ReservationResponse> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationResponse> reservations) {
        this.reservations = reservations;
    }

    public static ClientWithReservationResponse convert(Client client) {
        ClientWithReservationResponse resp = new ClientWithReservationResponse();

        resp.setId(client.getId());
        resp.setNom(client.getNom());
        resp.setPrenom(client.getPrenom());
        resp.setEmail(client.getMail());
        resp.setTelephone(client.getTelephone());
        resp.setReservations(client.getReservations().stream().map(ReservationResponse::convert).toList());

        return resp;
    }
}
