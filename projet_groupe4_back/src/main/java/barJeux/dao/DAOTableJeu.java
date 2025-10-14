package barJeux.dao;

import java.util.List;

import javax.persistence.EntityManager;

import barJeux.context.Singleton;
import barJeux.model.TableJeu;


public class DAOTableJeu implements IDAOTableJeu {

	@Override
	public List<TableJeu> findAll() {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<TableJeu> tableJeus  = em.createQuery("from TableJeu").getResultList();
		em.close();
		return tableJeus;
	}

	@Override
	public TableJeu findById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		TableJeu tableJeu  = em.find(TableJeu.class, id);
		em.close();
		return tableJeu;
	}

	@Override
	public TableJeu save(TableJeu tableJeu) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		tableJeu=  em.merge(tableJeu);
		em.getTransaction().commit();
		em.close();
		return tableJeu;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		TableJeu tableJeu = em.find(TableJeu.class, id);
		em.remove(tableJeu);
		em.getTransaction().commit();
		em.close();
	
	}

	@Override
	public void delete(TableJeu tableJeu) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		tableJeu = em.merge(tableJeu);
		em.remove(tableJeu);
		em.getTransaction().commit();
		em.close();
	}


}
