package projet_groupe4.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_jeu")
public class TableJeu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	@Column(name = "nom_table", columnDefinition = "varchar(50)", nullable = false)

	private String nomTable;
	@Column(nullable = false)

	private int capacite;
	@OneToMany(mappedBy = "tableJeu")
	private List<Reservation> reservations = new ArrayList<Reservation>();
	@Column(name="img_url", columnDefinition="varchar(100)")
	private String imgUrl;
	// Constructors
	public TableJeu() {
	}

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
	public String getImgUrl() { return imgUrl; }
	public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

	// toString
	@Override
	public String toString() {
		return "Table [id=" + id + ", nomTable=" + nomTable + ", capacite=" + capacite + "]";
	}
	
	

}
