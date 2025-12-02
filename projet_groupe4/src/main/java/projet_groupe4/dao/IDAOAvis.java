package projet_groupe4.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projet_groupe4.model.Avis;

public interface IDAOAvis extends JpaRepository<Avis,Integer> {

	 Optional<Avis> findByReservationId(Integer reservationId);
}
