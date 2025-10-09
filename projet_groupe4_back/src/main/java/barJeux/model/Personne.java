package barJeux.model;

public abstract class Personne {
	private Integer id;
	private String nom;
	private String prenom;
	private String mail;
	private String mdp;
	private String telephone;

	
	// Constructors
	public Personne(String nom, String prenom, String mail, String mdp, String telephone) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.mdp = mdp;
		this.telephone = telephone;
		
	}
	public Personne(Integer id, String nom, String prenom, String mail, String mdp, String telephone) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.mdp = mdp;
		this.telephone = telephone;

	}
	
	// Getters & Setters
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


	
	// toString
	@Override
	public String toString() {
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", mdp= ***" 
				+ ", telephone=" + telephone +  "]";
	}
	
	

}
