package projet_groupe4.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.model.Badge;
import projet_groupe4.view.Views;

public class BadgeResponse {
    @JsonView(Views.Common.class)
    private Integer id;
    @JsonView(Views.Common.class)
    private String nomBadge;
    @JsonView(Views.Common.class)       
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
    public static BadgeResponse convert(Badge badge){
        BadgeResponse resp = new BadgeResponse();
        resp.setId(badge.getId());
        resp.setNomBadge(badge.getNomBadge());
        resp.setImgURL(badge.getImgURL());
        return resp;
    }
    

}
