package barJeux.model;

public class Employe extends Personne{
	
	private String job;
	private boolean gameMaster;
	
	// Constructors
	public Employe(Integer id, String nom, String prenom, String mail, String mdp, String telephone, String job,
			boolean gameMaster) {
		super(id, nom, prenom, mail, mdp, telephone);
		this.job = job;
		this.gameMaster = gameMaster;
	}
	
	public Employe(String nom, String prenom, String mail, String mdp, String telephone,  String job,
			boolean gameMaster) {
		super(nom, prenom, mail, mdp, telephone);
		this.job = job;
		this.gameMaster = gameMaster;
	}

	// Getters & Setters
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
	
	// toString
	@Override
	public String toString() {
	    return "Employe [" + super.toString() + ", job=" + job + ", gameMaster=" + gameMaster + "]";
	}
	
	
	

}
