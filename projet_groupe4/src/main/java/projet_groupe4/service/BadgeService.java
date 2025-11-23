package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOBadge;
import projet_groupe4.dto.request.BadgeRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Badge;

@Service
public class BadgeService {
	private final IDAOBadge dao;

	public BadgeService(IDAOBadge dao) {
		this.dao = dao;
	}

	public List<Badge> getAll() {
		return this.dao.findAll();
	}

	public Optional<Badge> getById(Integer id) {
		return this.dao.findById(id);
	}

	public Badge create(BadgeRequest request) {
		return this.save(new Badge(), request);
	}

	public Badge update(Integer id, BadgeRequest request) {
		Badge badge = this.getById(id).orElseThrow(() -> new IdNotFoundException());
		return this.save(badge, request);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(Badge badge) {
		this.dao.delete(badge);
	}

	private Badge save(Badge badge, BadgeRequest request) {
		badge.setNomBadge(request.getNomBadge());
		badge.setPointMin(request.getPointMin());
		badge.setImgURL(request.getImgURL());

		return this.dao.save(badge);
	}

}
