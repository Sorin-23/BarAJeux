package projet_groupe4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.exception.ResourceNotFoundException;
import projet_groupe4.model.Jeu;

@Service
public class JeuService {
	@Autowired
	private IDAOJeu dao;
	
	public List<Jeu> getAll(){
		return this.dao.findAll();
	}
	
	public Jeu getById(Integer id) {
		return this.dao.findById(id).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	public Jeu create(Jeu jeu) {
		return this.dao.save(jeu);
	}
	
	public Jeu update(Jeu jeu) {
		return this.dao.save(jeu);
	}
	
	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}
	
	public void delete(Jeu jeu) {
		this.dao.delete(jeu);
	}
	
	public List<Jeu> getByNomContaining(String nom){
		if (nom == null || nom.trim().isEmpty()) {
	        return getAll();
	    }
		return this.dao.findByNomContaining(nom);
	}
}
