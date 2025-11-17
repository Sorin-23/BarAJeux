package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOAvis;
import projet_groupe4.model.Avis;

@Service
public class AvisService {
	@Autowired
	private IDAOAvis dao;
	
	public List<Avis> getAll(){
		return this.dao.findAll();
	}
	
	public Optional<Avis> getById(Integer id) {
		return this.dao.findById(id);
	}
	
	public Avis create(Avis avis) {
		return this.dao.save(avis);
	}
	
	public Avis update(Avis avis) {
		return this.dao.save(avis);
	}
	
	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}
	
	public void delete(Avis avis) {
		this.dao.delete(avis);
	}
}
