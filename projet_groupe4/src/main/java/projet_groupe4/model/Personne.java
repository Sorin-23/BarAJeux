package projet_groupe4.model;



import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;


@Entity
@Table(name="compte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_compte",columnDefinition = "ENUM('Client','Employe')")
public abstract class Personne {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	@Column(columnDefinition="varchar(20)", nullable=false)
	
	private String nom;
	@Column(columnDefinition="varchar(20)", nullable=false)
	
	private String prenom;
	@Column(name="mail",columnDefinition="varchar(100)", nullable=false, unique = true)
	
	private String mail;
	@Column(name ="mot_de_passe",nullable = false,columnDefinition = "VARCHAR(120)")
	
	private String mdp;
	@Column(name="numero_telephone")
	
	private String telephone;

	
	// Constructors
	public Personne() {}
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

	public String getTypeCompte() {
    DiscriminatorValue dv = this.getClass().getAnnotation(DiscriminatorValue.class);
    return dv != null ? dv.value() : null;
}
	
	// toString
	@Override
	public String toString() {
		return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", mdp= ***" 
				+ ", telephone=" + telephone +  "]";
	}
	
	

}
