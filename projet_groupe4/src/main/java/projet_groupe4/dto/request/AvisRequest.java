package projet_groupe4.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AvisRequest {

@Min(0)
private int note;
@NotBlank
private String titre;

private String commentaire;

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



}