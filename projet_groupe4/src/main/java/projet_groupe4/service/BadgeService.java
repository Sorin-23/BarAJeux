package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOBadge;
import projet_groupe4.dto.request.BadgeRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Badge;

@Service
public class BadgeService {
	private final IDAOBadge dao;
	private final static Logger log = LoggerFactory.getLogger(BadgeService.class);

	public BadgeService(IDAOBadge dao) {
		this.dao = dao;
	}

	public List<Badge> getAll() {
		log.debug("Liste des badges");
		return this.dao.findAll();
	}

	public Optional<Badge> getById(Integer id) {
		log.debug("Récupération du badge id {}",id);
		return this.dao.findById(id);
	}

	public Badge create(BadgeRequest request) {
		log.debug("Création du badge {} ",request.getNomBadge());
		return this.save(new Badge(), request);
	}

	public Badge update(Integer id, BadgeRequest request) {
		log.debug("Mise à jour du badge {}",id);
		Badge badge = this.getById(id).orElseThrow(() -> new IdNotFoundException());
		return this.save(badge, request);
	}

	public void deleteById(Integer id) {
		log.debug("Suppression par id du badge {}",id);
		this.dao.deleteById(id);
	}

	public void delete(Badge badge) {
		log.debug("Suppression du badge {}",badge.getId());
		this.dao.delete(badge);
	}

	private Badge save(Badge badge, BadgeRequest request) {
		log.debug("Ajouter un nouveau badge {}",request.getNomBadge());
		badge.setNomBadge(request.getNomBadge());
		badge.setPointMin(request.getPointMin());
		badge.setImgURL(request.getImgURL());

		return this.dao.save(badge);
	}

}
