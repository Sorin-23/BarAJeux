package projet_groupe4.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import projet_groupe4.model.Jeu;

public interface IDAOJeu extends JpaRepository<Jeu,Integer> {

	//public List<Jeu> findByNomLike(String nom); 
	public List<Jeu> findByNomContaining(String nom);
	@Query("SELECT j FROM Jeu j ORDER BY j.note DESC")
    List<Jeu> findTopByNote();

}
