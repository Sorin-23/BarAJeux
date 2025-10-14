package barJeux.dao;

import java.util.List;

import javax.persistence.EntityManager;

import barJeux.context.Singleton;
import barJeux.model.Reservation;


public class DAOReservation implements IDAOReservation {

	@Override
	public List<Reservation> findAll() {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Reservation> reservations  = em.createQuery("from Reservation").getResultList();
		em.close();
		return reservations;
	}

	@Override
	public Reservation findById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		Reservation reservation  = em.find(Reservation.class, id);
		em.close();
		return reservation;
	}

	@Override
	public Reservation save(Reservation reservation) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		reservation=  em.merge(reservation);
		em.getTransaction().commit();
		em.close();
		return reservation;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		Reservation reservation = em.find(Reservation.class, id);
		em.remove(reservation);
		em.getTransaction().commit();
		em.close();
	
	}

	@Override
	public void delete(Reservation reservation) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		reservation = em.merge(reservation);
		em.remove(reservation);
		em.getTransaction().commit();
		em.close();
	}


}
