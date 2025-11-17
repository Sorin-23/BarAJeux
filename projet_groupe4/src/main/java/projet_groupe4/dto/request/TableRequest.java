package projet_groupe4.dto.request;

import jakarta.validation.constraints.NotBlank;

public class TableRequest {

@NotBlank
private String nomTable;
@NotBlank
private int capacite;
public String getNomTable() {
return nomTable;
}
public void setNomTable(String nomTable) {
this.nomTable = nomTable;
}
public int getCapacite() {
return capacite;
}
public void setCapacite(int capacite) {
this.capacite = capacite;
}



}
