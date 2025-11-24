package projet_groupe4.dto.response;

import projet_groupe4.model.Badge;

public class BadgeResponse {

    private Integer id;

    private String nomBadge;

    private int pointMin;

    private String imgURL;

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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getPointMin() {
        return pointMin;
    }

    public void setPointMin(int pointMin) {
        this.pointMin = pointMin;
    }

    public static BadgeResponse convert(Badge badge) {
        BadgeResponse resp = new BadgeResponse();
        resp.setId(badge.getId());
        resp.setNomBadge(badge.getNomBadge());
        resp.setPointMin(badge.getPointMin());
        resp.setImgURL(badge.getImgURL());
        return resp;
    }

}
