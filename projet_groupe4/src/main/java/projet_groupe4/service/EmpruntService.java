package projet_groupe4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOEmprunt;
import projet_groupe4.exception.ResourceNotFoundException;
import projet_groupe4.model.Emprunt;

@Service
public class EmpruntService {
	@Autowired
	private IDAOEmprunt dao;
	
	public List<Emprunt> getAll(){
		return this.dao.findAll();
	}
	
	public Emprunt getById(Integer id) {
		return this.dao.findById(id).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	public Emprunt create(Emprunt emprunt) {
		return this.dao.save(emprunt);
	}
	
	public Emprunt update(Emprunt emprunt) {
		return this.dao.save(emprunt);
	}
	
	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}
	
	public void delete(Emprunt emprunt) {
		this.dao.delete(emprunt);
	}
}
