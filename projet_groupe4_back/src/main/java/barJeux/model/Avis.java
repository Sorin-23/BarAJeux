package barJeux.model;

public class Avis {
	
	private Integer id;
	private int note;
	private String titre;
	private String commentaire;
	private Reservation reservation;
	
	// Constructors 
	public Avis(Integer id, int note, String titre, String commentaire, Reservation reservation) {
		this.id = id;
		this.note = note;
		this.titre = titre;
		this.commentaire = commentaire;
		this.reservation = reservation;
	}
	public Avis(int note, String titre, String commentaire, Reservation reservation) {
		this.note = note;
		this.titre = titre;
		this.commentaire = commentaire;
		this.reservation = reservation;
	}
	
	
	// Getters & Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		if(note < 0) note = 0;
        if(note > 5) note = 5;
        this.note = note;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	// Méthodes utilitaires 


	
	// toString
	@Override
	public String toString() {
		return "Avis [id=" + id + ", note=" + note +  ", titre=" + titre + ", commentaire=" + commentaire + ", client=" + reservation.getClient().getNom() + " " + reservation.getClient().getPrenom() 
				+ ", jeu=" + reservation.getJeu().getNom() + "]";
	}
	
	
	

}
