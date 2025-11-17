package projet_groupe4.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="table_jeu")
public class TableJeu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="nom_table",columnDefinition="varchar(50)", nullable=false)
	private String nomTable;
	@Column(nullable=false)
	private int capacite;
	private boolean disponibilite;
	
	// Constructors
	public TableJeu() {}
	public TableJeu(Integer id, String nomTable, int capacite, boolean disponibilite) {
		this.id = id;
		this.nomTable = nomTable;
		this.capacite = capacite;
		this.disponibilite = disponibilite;
	}
	public TableJeu(String nomTable, int capacite, boolean disponibilite) {
		this.nomTable = nomTable;
		this.capacite = capacite;
		this.disponibilite = disponibilite;
	}
	
	// Getters & Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomTable() {
		return nomTable;
	}
	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public boolean isDisponibilite() {
		return disponibilite;
	}
	public void setDisponibilite(boolean disponibilite) {
		this.disponibilite = disponibilite;
	}
	
	// Méthodes utilitaires 
	public void reserver() {
	    disponibilite = false;
	}
	public void liberer() {
	    disponibilite = true;
	}

	// toString
	@Override
	public String toString() {
		return "Table [id=" + id + ", nomTable=" + nomTable + ", capacite=" + capacite + ", disponibilite="
				+ disponibilite + "]";
	}
	
	

}
