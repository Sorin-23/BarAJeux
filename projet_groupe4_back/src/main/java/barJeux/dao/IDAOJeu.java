package barJeux.dao;

import java.util.List;

import barJeux.model.Jeu;

public interface IDAOJeu extends IDAO<Jeu,Integer> {

	public List<Jeu> findByNomLike(String nom);

}
