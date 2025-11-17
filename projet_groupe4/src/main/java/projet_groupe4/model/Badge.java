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
@Table(name="badge")
public class Badge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private Integer id;
	@Column(name="nom_badge", columnDefinition="varchar(30)", nullable=false)
	@JsonView(Views.Common.class)
	private String nomBadge;
	@Column(name="point_min", nullable=false)
	@JsonView(Views.Common.class)
	private int pointMin;
	@Column(name="img_url")
	@JsonView(Views.Common.class)
	private String imgURL;
	
	// Constructors
	public Badge() {}
	public Badge(Integer id, String nomBadge, int pointMin, String imgURL) {
		this.id = id;
		this.nomBadge = nomBadge;
		this.pointMin = pointMin;
		this.imgURL = imgURL;
	}
	public Badge(String nomBadge, int pointMin, String imgURL) {
		this.nomBadge = nomBadge;
		this.pointMin = pointMin;
		this.imgURL = imgURL;
	}

	// Getters & Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomBadge() {
		return nomBadge;
	}
	public void setNomBadge(String nomBadge) {
		this.nomBadge = nomBadge;
	}
	public int getPointMin() {
		return pointMin;
	}
	public void setPointMin(int pointMin) {
		this.pointMin = pointMin;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	
	// toString
	@Override
	public String toString() {
		return "Badge [id=" + id + ", nomBadge=" + nomBadge + ", pointMin=" + pointMin + ", imgURL=" + imgURL + "]";
	}

}
