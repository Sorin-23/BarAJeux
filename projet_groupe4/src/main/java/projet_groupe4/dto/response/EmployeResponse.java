package projet_groupe4.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.model.Employe;
import projet_groupe4.view.Views;

public class EmployeResponse {

     @JsonView(Views.Common.class)
	private Integer id;
	
	@JsonView(Views.Common.class)
	private String nom;
	
	@JsonView(Views.Common.class)
	private String prenom;
	
	@JsonView(Views.Common.class)
	private String mail;
	/*
	@JsonView(Views.Common.class)
	private String mdp;
*/
	
	@JsonView(Views.Common.class)
	private String telephone;

    @JsonView(Views.Common.class)
	private String job;
	@JsonView(Views.Common.class)
	private boolean gameMaster;
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
   /* public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }*/
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
    public boolean isGameMaster() {
        return gameMaster;
    }
    public void setGameMaster(boolean gameMaster) {
        this.gameMaster = gameMaster;
    }

    public static EmployeResponse convert(Employe employe){
        EmployeResponse resp = new EmployeResponse();
        resp.setId(employe.getId());
        resp.setNom(employe.getNom());
        resp.setPrenom(employe.getPrenom());
        resp.setMail(employe.getMail());
        //resp.setMdp(employe.getMdp());
        resp.setTelephone(employe.getTelephone());
        resp.setJob(employe.getJob());
        resp.setGameMaster(employe.isGameMaster());

        return resp;
    }

}
