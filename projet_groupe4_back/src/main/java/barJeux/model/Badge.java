package barJeux.model;

public class Badge {
	
	private Integer id;
	private String nomBadge;
	private int pointMin;
	private String imgURL;
	
	// Constructors
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
