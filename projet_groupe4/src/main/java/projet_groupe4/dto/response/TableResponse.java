package projet_groupe4.dto.response;

import com.fasterxml.jackson.annotation.JsonView;

import projet_groupe4.view.Views;
import projet_groupe4.model.TableJeu;


public class TableResponse {


    @JsonView(Views.Common.class)
    private Integer id;
    @JsonView(Views.Common.class)
    private String nomTable;
    @JsonView(Views.Common.class)
    private int capacite;
    @JsonView(Views.Common.class)
    private String imgUrl;



    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }



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
    


    public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public static TableResponse convert(TableJeu table) {
        TableResponse resp = new TableResponse();

        resp.setId(table.getId());
        resp.setNomTable(table.getNomTable());
        resp.setCapacite(table.getCapacite());
        resp.setImgUrl(table.getImgUrl());

        return resp;

    }
}
