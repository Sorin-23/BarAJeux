package projet_groupe4.model;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import projet_groupe4.view.Views;

@Entity
@Table(name="avis_client")
public class Avis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;
	@JsonView(Views.Common.class)
	private int note;
	@Column(name="titre",columnDefinition="varchar(20)", nullable=false)
	@JsonView(Views.Common.class)
	private String titre;
	@Column(name="commentaire",columnDefinition="varchar(200)")
	@JsonView(Views.Common.class)
	private String commentaire;
	@OneToOne
	@JoinColumn(name="reservation")
	@JsonView(Views.Avis.class)
	private Reservation reservation;
	
	// Constructors 
	public Avis() {}
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
