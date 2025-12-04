package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOTableJeu;
import projet_groupe4.dto.request.TableRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.TableJeu;

@Service
public class TableJeuService {
	private final IDAOTableJeu dao;
	private final static Logger log = LoggerFactory.getLogger(TableJeuService.class);

	public TableJeuService(IDAOTableJeu dao) {
		this.dao = dao;
	}

	public List<TableJeu> getAll() {
		log.debug("Liste des tables de jeu");
		return this.dao.findAllWithReservations();
	}
	/*public List<TableJeu> findAllWithReservations() {
        return this.dao.findAllWithReservations();
    }*/

	public Optional<TableJeu> getById(Integer id) {
		log.debug("Récupération de la table id {}",id);
		return this.dao.findById(id);
	}

	public TableJeu create(TableRequest request) {
		log.debug("Création d'une nouvelle table {}",request.getNomTable());
		return this.save(new TableJeu(), request);
	}

	public TableJeu update(Integer id, TableRequest request) {
		log.debug("Mise à jour de la table id {}",id);
		TableJeu tableJeu = this.getById(id).orElseThrow(IdNotFoundException::new);
		return this.save(tableJeu, request);
	}

	public void deleteById(Integer id) {
		log.debug("Suppression de la table id {}",id);
		this.dao.deleteById(id);
	}

	public void delete(TableJeu tableJeu) {
		log.debug("Suppression de la table {}",tableJeu.getNomTable());
		this.dao.delete(tableJeu);
	}

	private TableJeu save(TableJeu tableJeu, TableRequest request) {
		log.debug("Création/Mise à jour de la table {}",request.getNomTable());
		tableJeu.setNomTable(request.getNomTable());
		tableJeu.setCapacite(request.getCapacite());
		tableJeu.setImgUrl(request.getImgUrl());

		return this.dao.save(tableJeu);
	}

}
