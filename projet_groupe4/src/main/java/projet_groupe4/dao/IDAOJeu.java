package projet_groupe4.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projet_groupe4.model.Jeu;

public interface IDAOJeu extends JpaRepository<Jeu,Integer> {

	//public List<Jeu> findByNomLike(String nom); 
	public List<Jeu> findByNomContaining(String nom);

}
