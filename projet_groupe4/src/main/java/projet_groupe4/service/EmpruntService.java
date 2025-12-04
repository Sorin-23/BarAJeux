package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOEmprunt;
import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.EmpruntRequest;
import projet_groupe4.dto.response.TopJeuResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Emprunt;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Personne;

@Service
public class EmpruntService {
	private final IDAOEmprunt dao;
	private final IDAOPersonne personneDao;
	private final IDAOJeu jeuDao;
	private final static Logger log = LoggerFactory.getLogger(EmpruntService.class);

	public EmpruntService(IDAOEmprunt dao, IDAOPersonne personneDao, IDAOJeu jeuDao) {
		this.dao = dao;
		this.personneDao = personneDao;
		this.jeuDao = jeuDao;
	}

	public List<Emprunt> getAll() {
		log.debug("Liste des emprunts");
		return this.dao.findAll();
	}

	public Optional<Emprunt> getById(Integer id) {
		log.debug("Récupération de l'emprunt id {}", id);
		return this.dao.findById(id);
	}

	public Emprunt create(EmpruntRequest request) {
		log.debug("Création d'un nouveau emprunt");
		return this.save(new Emprunt(), request);
	}

	public Emprunt update(Integer id, EmpruntRequest request) {
		log.debug("Mise à jour de l'emprunt id {}", id);
		Emprunt emprunt = getById(id).orElseThrow(() -> new IdNotFoundException());
		return this.save(emprunt, request);
	}

	public void deleteById(Integer id) {
		log.debug("Suppression par id de l'emprunt id {}", id);
		this.dao.deleteById(id);
	}

	public void delete(Emprunt emprunt) {
		log.debug("Suppression de l'emprunt id {}", emprunt.getId());
		this.dao.delete(emprunt);
	}

	public List<TopJeuResponse> getTop3Emprunts() {
		log.debug("Les jeux les plus empruntés");
		List<Object[]> results = dao.findTopEmprunts();
		return results.stream()
				.map(r -> new TopJeuResponse((Jeu) r[0], ((Long) r[1]).doubleValue()))
				.limit(3)
				.toList();

	}

	private Emprunt save(Emprunt emprunt, EmpruntRequest request) {
		boolean isNew = (emprunt.getId() == null);

		emprunt.setDateEmprunt(request.getDateEmprunt());
		emprunt.setDateRetour(request.getDateRetour());
		emprunt.setDateRetourReel(request.getDateRetourReel());
		emprunt.setStatutLocation(request.getStatutLocation());
		Personne personne = this.personneDao.findById(request.getClientId())
				.orElseThrow(IdNotFoundException::new);

		if (!(personne instanceof Client client)) {
			throw new RuntimeException("L'id ne correspond pas à un client");
		}

		emprunt.setClient(client);
		emprunt.setJeu(this.jeuDao.getReferenceById(request.getJeuId()));

		Emprunt saved = this.dao.save(emprunt);

		// Si c'est un nouvel emprunt, on ajoute 10 points de fidélité
		if (isNew) {
			Integer currentPoints = client.getPointFidelite();
			if (currentPoints == null) {
				currentPoints = 0;
			}

			client.setPointFidelite(currentPoints + 10);
			this.personneDao.save(client);
		}

		return saved;
	}

}
