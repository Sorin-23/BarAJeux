package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOEmprunt;
import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.EmpruntRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Emprunt;

@Service
public class EmpruntService {
	private final IDAOEmprunt dao;
	private final IDAOPersonne personneDao;
	private final IDAOJeu jeuDao;

	public EmpruntService(IDAOEmprunt dao, IDAOPersonne personneDao, IDAOJeu jeuDao) {
		this.dao = dao;
		this.personneDao = personneDao;
		this.jeuDao = jeuDao;
	}

	public List<Emprunt> getAll() {
		return this.dao.findAll();
	}

	public Optional<Emprunt> getById(Integer id) {
		return this.dao.findById(id);
	}

	public Emprunt create(EmpruntRequest request) {
		return this.save(new Emprunt(), request);
	}

	public Emprunt update(Integer id, EmpruntRequest request) {
		Emprunt emprunt = getById(id).orElseThrow(() -> new IdNotFoundException());
		return this.save(emprunt, request);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(Emprunt emprunt) {
		this.dao.delete(emprunt);
	}

	private Emprunt save(Emprunt emprunt, EmpruntRequest request) {
		emprunt.setDateEmprunt(request.getDateEmprunt());
		emprunt.setDateRetour(request.getDateRetour());
		emprunt.setDateRetourReel(request.getDateRetourReel());
		emprunt.setStatutLocation(request.getStatutLocation());
		emprunt.setClient((Client) this.personneDao.getReferenceById(request.getClientId()));
		emprunt.setJeu(this.jeuDao.getReferenceById(request.getJeuId()));

		return this.dao.save(emprunt);

	}
}
