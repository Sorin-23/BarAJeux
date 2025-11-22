package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOTableJeu;
import projet_groupe4.model.TableJeu;

@Service
public class TableJeuService {
	@Autowired
	private IDAOTableJeu dao;

	public List<TableJeu> getAll() {
		return this.dao.findAll();
	}

	public Optional<TableJeu> getById(Integer id) {
		return this.dao.findById(id);
	}

	public TableJeu create(TableJeu tableJeu) {
		return this.dao.save(tableJeu);
	}

	public TableJeu update(TableJeu tableJeu) {
		return this.dao.save(tableJeu);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(TableJeu tableJeu) {
		this.dao.delete(tableJeu);
	}

}
