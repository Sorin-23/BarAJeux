package projet_groupe4.dto.request;

import jakarta.validation.constraints.NotBlank;

public class BadgeRequest {

@NotBlank
private String nomBadge;
@NotBlank
private int pointMin;
@NotBlank
private String imgURL;
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

}
