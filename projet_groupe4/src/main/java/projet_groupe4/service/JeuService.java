package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dto.request.JeuRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Jeu;

@Service
public class JeuService {
	private final IDAOJeu dao;

	public JeuService(IDAOJeu dao) {
		this.dao = dao;
	}

	public List<Jeu> getAll() {
		return this.dao.findAll();
	}

	public Optional<Jeu> getById(Integer id) {
		return this.dao.findById(id);
	}

	public Jeu create(JeuRequest request) {
		return this.save(new Jeu(), request);
	}

	public Jeu update(Integer id, JeuRequest request) {
		Jeu jeu = getById(id).orElseThrow(() -> new IdNotFoundException());
		return this.save(jeu, request);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(Jeu jeu) {
		this.dao.delete(jeu);
	}

	public List<Jeu> getByNomContaining(String nom) {
		if (nom == null || nom.trim().isEmpty()) {
			return getAll();
		}
		return this.dao.findByNomContaining(nom);
	}

	private Jeu save(Jeu jeu, JeuRequest request) {
		jeu.setNom(request.getNom());
		jeu.setTypesJeux(request.getTypesJeux());
		jeu.setAgeMinimum(request.getAgeMinimum());
		jeu.setNbJoueurMinimum(request.getNbJoueurMinimum());
		jeu.setNbJoueurMaximum(request.getNbJoueurMaximum());
		jeu.setDuree(request.getDuree());
		jeu.setNbExemplaire(request.getNbExemplaire());
		jeu.setNote(request.getNote());
		jeu.setCategoriesJeux(request.getCategoriesJeux());
		jeu.setImgURL(request.getImgURL());
		jeu.setBesoinGameMaster(request.isBesoinGameMaster());

		return this.dao.save(jeu);
	}

}
