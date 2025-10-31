package barJeux.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import barJeux.model.Reservation;

public interface IDAOReservation extends JpaRepository<Reservation,Integer> {

}
