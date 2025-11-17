package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOReservation;
import projet_groupe4.model.Reservation;

@Service
public class ReservationService {
	@Autowired
	private IDAOReservation dao;
	
	public List<Reservation> getAll(){
		return this.dao.findAll();
	}
	
	public Optional<Reservation> getById(Integer id) {
		return this.dao.findById(id);
	}
	
	public Reservation create(Reservation reservation) {
		return this.dao.save(reservation);
	}
	
	public Reservation update(Reservation reservation) {
		return this.dao.save(reservation);
	}
	
	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}
	
	public void delete(Reservation reservation) {
		this.dao.delete(reservation);
	}
	
}
