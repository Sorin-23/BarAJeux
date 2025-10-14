package barJeux.dao;

import java.util.List;

import javax.persistence.EntityManager;

import barJeux.context.Singleton;
import barJeux.model.Emprunt;


public class DAOEmprunt implements IDAOEmprunt {

	@Override
	public List<Emprunt> findAll() {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Emprunt> emprunts  = em.createQuery("from Emprunt").getResultList();
		em.close();
		return emprunts;
	}

	@Override
	public Emprunt findById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		Emprunt emprunt  = em.find(Emprunt.class, id);
		em.close();
		return emprunt;
	}

	@Override
	public Emprunt save(Emprunt emprunt) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		emprunt=  em.merge(emprunt);
		em.getTransaction().commit();
		em.close();
		return emprunt;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		Emprunt emprunt = em.find(Emprunt.class, id);
		em.remove(emprunt);
		em.getTransaction().commit();
		em.close();
	
	}

	@Override
	public void delete(Emprunt emprunt) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		emprunt = em.merge(emprunt);
		em.remove(emprunt);
		em.getTransaction().commit();
		em.close();
	}


}
