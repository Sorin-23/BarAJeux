package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOTableJeu;
import projet_groupe4.dto.request.TableRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.TableJeu;

@Service
public class TableJeuService {
	private final IDAOTableJeu dao;

	public TableJeuService(IDAOTableJeu dao) {
		this.dao = dao;
	}

	public List<TableJeu> getAll() {
		return this.dao.findAll();
	}

	public Optional<TableJeu> getById(Integer id) {
		return this.dao.findById(id);
	}

	public TableJeu create(TableRequest request) {
		return this.save(new TableJeu(), request);
	}

	public TableJeu update(Integer id, TableRequest request) {
		TableJeu tableJeu = this.getById(id).orElseThrow(IdNotFoundException::new);
		return this.save(tableJeu, request);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(TableJeu tableJeu) {
		this.dao.delete(tableJeu);
	}

	private TableJeu save(TableJeu tableJeu, TableRequest request) {
		tableJeu.setNomTable(request.getNomTable());
		tableJeu.setCapacite(request.getCapacite());
		tableJeu.setImgUrl(request.getImgUrl());

		return this.dao.save(tableJeu);
	}

}
