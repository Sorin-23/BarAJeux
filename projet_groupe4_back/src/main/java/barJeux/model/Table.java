package barJeux.model;

public class Table {
	
	private Integer id;
	private String nomTable;
	private int capacite;
	private boolean disponibilite;
	
	// Constructors
	public Table(Integer id, String nomTable, int capacite, boolean disponibilite) {
		this.id = id;
		this.nomTable = nomTable;
		this.capacite = capacite;
		this.disponibilite = disponibilite;
	}
	public Table(String nomTable, int capacite, boolean disponibilite) {
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
