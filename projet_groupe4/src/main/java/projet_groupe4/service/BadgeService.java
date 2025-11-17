package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOBadge;
import projet_groupe4.model.Badge;

@Service
public class BadgeService {
	@Autowired
	private IDAOBadge dao;
	
	public List<Badge> getAll(){
		return this.dao.findAll();
	}
	
	public Optional<Badge> getById(Integer id) {
		return this.dao.findById(id);
	}
	
	public Badge create(Badge badge) {
		return this.dao.save(badge);
	}
	
	public Badge update(Badge badge) {
		return this.dao.save(badge);
	}
	
	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}
	
	public void delete(Badge badge) {
		this.dao.delete(badge);
	}
}
