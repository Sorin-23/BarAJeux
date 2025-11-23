package projet_groupe4.dto.response;

import projet_groupe4.model.Employe;

public class EmployeResponse {

    private Integer id;

    private String nom;

    private String prenom;

    private String mail;
    /*
     * 
     * private String mdp;
     */

    private String telephone;

    private String job;

    private Boolean gameMaster;

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

    /*
     * public String getMdp() {
     * return mdp;
     * }
     * public void setMdp(String mdp) {
     * this.mdp = mdp;
     * }
     */
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Boolean isGameMaster() {
        return gameMaster;
    }

    public void setGameMaster(Boolean gameMaster) {
        this.gameMaster = gameMaster;
    }

    public static EmployeResponse convert(Employe employe) {
        EmployeResponse resp = new EmployeResponse();
        resp.setId(employe.getId());
        resp.setNom(employe.getNom());
        resp.setPrenom(employe.getPrenom());
        resp.setMail(employe.getMail());
        // resp.setMdp(employe.getMdp());
        resp.setTelephone(employe.getTelephone());
        resp.setJob(employe.getJob());
        resp.setGameMaster(employe.isGameMaster());

        return resp;
    }

}
