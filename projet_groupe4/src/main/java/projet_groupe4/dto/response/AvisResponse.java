package projet_groupe4.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.view.Views;
import projet_groupe4.model.Avis;

public class AvisResponse {


    @JsonView(Views.Common.class)
    private Integer id;
    @JsonView(Views.Common.class)
    private int note;
    @JsonView(Views.Common.class)
    private String titre;
    @JsonView(Views.Common.class)
    private String commentaire;



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


    public static AvisResponse convert(Avis avis) {
        AvisResponse resp = new AvisResponse();

        resp.setId(avis.getId());
        resp.setNote(avis.getNote());
        resp.setTitre(avis.getTitre());
        resp.setCommentaire(avis.getCommentaire());

        return resp;

    }
}