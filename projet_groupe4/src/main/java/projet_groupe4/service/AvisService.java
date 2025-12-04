package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOAvis;
import projet_groupe4.dao.IDAOReservation;
import projet_groupe4.dto.request.AvisRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Avis;

@Service
public class AvisService {

	private final IDAOAvis dao;
	private final IDAOReservation reservationDao;
	private final static Logger log = LoggerFactory.getLogger(AvisService.class);

	public AvisService(IDAOAvis dao, IDAOReservation reservationDao) {
		this.dao = dao;
		this.reservationDao = reservationDao;
	}

	public List<Avis> getAll() {
		log.debug("Liste des avis");
		return this.dao.findAll();
	}

	public Optional<Avis> getById(Integer id) {
		log.debug("Récupération de l'avis {}",id);
		return this.dao.findById(id);
	}

	public Avis create(AvisRequest request) {
		log.debug("Création de l'avis pour la réservation {}",request.getReservationId());
		return this.save(new Avis(), request);
	}

	public Avis update(Integer id, AvisRequest request) {
		log.debug("Mise à jour de l'avis {}",id);
		Avis avis = this.getById(id).orElseThrow(IdNotFoundException::new);
		return this.save(avis, request);
	}

	public void deleteById(Integer id) {
		log.debug("Suppression par id de l'avis {}",id);
		this.dao.deleteById(id);
	}

	public void delete(Avis avis) {
		log.debug("Suppression de l'avis {}",avis.getId());
		this.dao.delete(avis);
	}
	public Optional<Avis> getByReservationId(Integer reservationId) {
		log.debug("L'avis de la réservation id {}",reservationId);
	    return dao.findByReservationId(reservationId);
	}

	private Avis save(Avis avis, AvisRequest request) {
		log.debug("Ajouter avis");
		avis.setNote(request.getNote());
		avis.setTitre(request.getTitre());
		avis.setCommentaire(request.getCommentaire());
		avis.setReservation(this.reservationDao.getReferenceById(request.getReservationId()));

		return this.dao.save(avis);
	}
}
