package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

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

	public AvisService(IDAOAvis dao, IDAOReservation reservationDao) {
		this.dao = dao;
		this.reservationDao = reservationDao;
	}

	public List<Avis> getAll() {
		return this.dao.findAll();
	}

	public Optional<Avis> getById(Integer id) {
		return this.dao.findById(id);
	}

	public Avis create(AvisRequest request) {
		return this.save(new Avis(), request);
	}

	public Avis update(Integer id, AvisRequest request) {
		Avis avis = this.getById(id).orElseThrow(IdNotFoundException::new);
		return this.save(avis, request);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(Avis avis) {
		this.dao.delete(avis);
	}

	private Avis save(Avis avis, AvisRequest request) {
		avis.setNote(request.getNote());
		avis.setTitre(request.getTitre());
		avis.setCommentaire(request.getCommentaire());
		avis.setReservation(this.reservationDao.getReferenceById(request.getReservationId()));

		return this.dao.save(avis);
	}
}
