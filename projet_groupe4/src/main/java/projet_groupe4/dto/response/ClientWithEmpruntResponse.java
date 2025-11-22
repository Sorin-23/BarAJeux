package projet_groupe4.dto.response;

import java.util.List;

import projet_groupe4.model.Client;

public class ClientWithEmpruntResponse {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private List<EmpruntResponse> emprunts;

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

    public List<EmpruntResponse> getEmprunts() {
        return emprunts;
    }

    public void setEmprunts(List<EmpruntResponse> emprunts) {
        this.emprunts = emprunts;
    }

    public static ClientWithEmpruntResponse convert(Client client) {
        ClientWithEmpruntResponse resp = new ClientWithEmpruntResponse();

        resp.setId(client.getId());
        resp.setNom(client.getNom());
        resp.setPrenom(client.getPrenom());
        resp.setEmail(client.getMail());
        resp.setTelephone(client.getTelephone());
        resp.setEmprunts(client.getEmprunts().stream().map(EmpruntResponse::convert).toList());

        return resp;
    }
}
