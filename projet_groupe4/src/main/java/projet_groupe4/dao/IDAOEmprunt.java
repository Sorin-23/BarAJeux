package projet_groupe4.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import projet_groupe4.model.Emprunt;

public interface IDAOEmprunt extends JpaRepository<Emprunt,Integer> {

	@Query("SELECT e.jeu AS jeu, COUNT(e) AS nbEmprunts FROM Emprunt e GROUP BY e.jeu ORDER BY COUNT(e) DESC")
	public List<Object[]> findTopEmprunts();
}
