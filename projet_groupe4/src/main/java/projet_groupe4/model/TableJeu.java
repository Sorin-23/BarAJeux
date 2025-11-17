package projet_groupe4.model;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import projet_groupe4.view.Views;

@Entity
@Table(name="table_jeu")
public class TableJeu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;
	@Column(name="nom_table",columnDefinition="varchar(50)", nullable=false)
	@JsonView(Views.Common.class)
	private String nomTable;
	@Column(nullable=false)
	@JsonView(Views.Common.class)
	private int capacite;
	
	// Constructors
	public TableJeu() {}
	public TableJeu(Integer id, String nomTable, int capacite) {
		this.id = id;
		this.nomTable = nomTable;
		this.capacite = capacite;
	}
	public TableJeu(String nomTable, int capacite) {
		this.nomTable = nomTable;
		this.capacite = capacite;
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
	
	


	// toString
	@Override
	public String toString() {
		return "Table [id=" + id + ", nomTable=" + nomTable + ", capacite=" + capacite + "]";
	}
	
	

}
