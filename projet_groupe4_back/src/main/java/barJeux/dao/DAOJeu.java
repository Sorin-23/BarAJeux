package barJeux.dao;

import java.util.List;

import javax.persistence.EntityManager;

import barJeux.context.Singleton;
import barJeux.model.Jeu;



public class DAOJeu implements IDAOJeu {

	@Override
	public List<Jeu> findAll() {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Jeu> jeus  = em.createQuery("from Jeu").getResultList();
		em.close();
		return jeus;
	}

	@Override
	public Jeu findById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		Jeu jeu  = em.find(Jeu.class, id);
		em.close();
		return jeu;
	}

	@Override
	public Jeu save(Jeu jeu) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		jeu=  em.merge(jeu);
		em.getTransaction().commit();
		em.close();
		return jeu;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		Jeu jeu = em.find(Jeu.class, id);
		em.remove(jeu);
		em.getTransaction().commit();
		em.close();
	
	}

	@Override
	public void delete(Jeu jeu) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		jeu = em.merge(jeu);
		em.remove(jeu);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public List<Jeu> findByNomLike(String nom)  {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Jeu> jeux  = em.createQuery("SELECT j from Jeu j where j.nom like :lib").setParameter("lib", "%"+nom+"%").getResultList();
		em.close();
		return jeux;
	}
	
	// faire des find by type et catégorie pour faire stats combien de types dés combien de type plateau ??? avec un list d'objet 


}
