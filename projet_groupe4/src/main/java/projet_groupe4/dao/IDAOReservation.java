package projet_groupe4.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import projet_groupe4.model.Reservation;

public interface IDAOReservation extends JpaRepository<Reservation,Integer> {

}
