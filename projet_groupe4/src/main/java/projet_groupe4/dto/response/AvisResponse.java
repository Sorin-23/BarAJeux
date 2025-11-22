package projet_groupe4.dto.response;

import projet_groupe4.model.Avis;

public class AvisResponse {

    private Integer id;
    private int note;
    private String titre;
    private String commentaire;
    private Integer reservationId;

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

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public static AvisResponse convert(Avis avis) {
        AvisResponse resp = new AvisResponse();

        resp.setId(avis.getId());
        resp.setNote(avis.getNote());
        resp.setTitre(avis.getTitre());
        resp.setCommentaire(avis.getCommentaire());

        if (avis.getReservation() != null) {
            resp.setReservationId(avis.getReservation().getId());
        }

        return resp;

    }
}