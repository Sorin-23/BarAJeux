package projet_groupe4.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import projet_groupe4.model.Reservation;

public interface IDAOReservation extends JpaRepository<Reservation,Integer> {
	
	@Query("SELECT r.jeu AS jeu, COUNT(r) AS nbReservations FROM Reservation r GROUP BY r.jeu ORDER BY COUNT(r) DESC")
	public List<Object[]> findTopReservations();

}
