package barJeux.dao;

import java.util.List;

import javax.persistence.EntityManager;

import barJeux.context.Singleton;
import barJeux.model.Avis;


public class DAOAvis implements IDAOAvis {

	@Override
	public List<Avis> findAll() {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Avis> aviss  = em.createQuery("from Avis").getResultList();
		em.close();
		return aviss;
	}

	@Override
	public Avis findById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		Avis avis  = em.find(Avis.class, id);
		em.close();
		return avis;
	}

	@Override
	public Avis save(Avis avis) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		avis=  em.merge(avis);
		em.getTransaction().commit();
		em.close();
		return avis;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		Avis avis = em.find(Avis.class, id);
		em.remove(avis);
		em.getTransaction().commit();
		em.close();
	
	}

	@Override
	public void delete(Avis avis) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		avis = em.merge(avis);
		em.remove(avis);
		em.getTransaction().commit();
		em.close();
	}


}
