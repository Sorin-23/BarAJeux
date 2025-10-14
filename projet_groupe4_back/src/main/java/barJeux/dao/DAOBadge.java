package barJeux.dao;

import java.util.List;

import javax.persistence.EntityManager;

import barJeux.context.Singleton;
import barJeux.model.Badge;


public class DAOBadge implements IDAOBadge {

	@Override
	public List<Badge> findAll() {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Badge> badges  = em.createQuery("from Badge").getResultList();
		em.close();
		return badges;
	}

	@Override
	public Badge findById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		Badge badge  = em.find(Badge.class, id);
		em.close();
		return badge;
	}

	@Override
	public Badge save(Badge badge) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		badge=  em.merge(badge);
		em.getTransaction().commit();
		em.close();
		return badge;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		Badge badge = em.find(Badge.class, id);
		em.remove(badge);
		em.getTransaction().commit();
		em.close();
	
	}

	@Override
	public void delete(Badge badge) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		badge = em.merge(badge);
		em.remove(badge);
		em.getTransaction().commit();
		em.close();
	}


}
