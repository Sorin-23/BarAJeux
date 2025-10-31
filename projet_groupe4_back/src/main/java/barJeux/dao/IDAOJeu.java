package barJeux.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import barJeux.model.Jeu;

public interface IDAOJeu extends JpaRepository<Jeu,Integer> {

	//public List<Jeu> findByNomLike(String nom); 
	public List<Jeu> findByNomContaining(String nom);

}
