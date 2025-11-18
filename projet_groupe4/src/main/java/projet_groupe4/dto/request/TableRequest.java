package projet_groupe4.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TableRequest {

@NotBlank
private String nomTable;
@NotNull
@Min(1)
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
