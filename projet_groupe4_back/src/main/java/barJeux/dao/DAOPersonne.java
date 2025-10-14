package barJeux.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import barJeux.context.Singleton;
import barJeux.model.Personne;
import barJeux.model.Client;
import barJeux.model.Employe;


public class DAOPersonne implements IDAOPersonne {

	@Override
	public List<Personne> findAll() {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Personne> personnes  = em.createQuery("from Personne").getResultList();
		em.close();
		return personnes;
	}

	@Override
	public Personne findById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		Personne personne  = em.find(Personne.class, id);
		em.close();
		return personne;
	}

	@Override
	public Personne save(Personne personne) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		personne=  em.merge(personne);
		em.getTransaction().commit();
		em.close();
		return personne;
	}

	@Override
	public void deleteById(Integer id) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		Personne personne = em.find(Personne.class, id);
		em.remove(personne);
		em.getTransaction().commit();
		em.close();
	
	}

	@Override
	public void delete(Personne personne) {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		em.getTransaction().begin();
		personne = em.merge(personne);
		em.remove(personne);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public List<Client> findAllClient() {
	    EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
	    List<Client> clients = em.createQuery("from Client", Client.class).getResultList();
	    em.close();
	    return clients;
	}
	
	@Override
	public List<Employe> findAllEmploye() {
	    EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
	    List<Employe> employes = em.createQuery("from Employe", Employe.class).getResultList();
	    em.close();
	    return employes;
	}
	
	@Override
	public Client findByIdWithEmprunts(Integer idClient) {
		Client client=null;
		try {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		client  = em.createQuery("SELECT c from Client c LEFT JOIN FETCH c.emprunts where c.id=:id",Client.class).setParameter("id", idClient).getSingleResult();
		em.close();
		}catch(Exception e) {e.printStackTrace();}
		return client;
	}

	@Override
	public Client findByIdWithReservations(Integer idClient) {
		Client client=null;
		try {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		client  = em.createQuery("SELECT c from Client c LEFT JOIN FETCH c.reservations where c.id=:id",Client.class).setParameter("id", idClient).getSingleResult();
		em.close();
		}catch(Exception e) {e.printStackTrace();}
		return client;
	}
	
	@Override
	public List<Personne> findByNomLike(String nom)  {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Personne> personnes  = em.createQuery("SELECT p from Personne p where p.nom like :lib").setParameter("lib", "%"+nom+"%").getResultList();
		em.close();
		return personnes;
	}
	@Override
	public List<Personne> findByPrenomLike(String prenom)  {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		List<Personne> personnes  = em.createQuery("SELECT p from Personne p where p.prenom like :lib").setParameter("lib", "%"+prenom+"%").getResultList();
		em.close();
		return personnes;
	}
	
	@Override
	public Personne findByLoginAndPassword(String mail, String mdp) {
		Personne personne=null;
		try {
		EntityManager em = Singleton.getInstance().getEmf().createEntityManager();
		
		
		Query query = em.createQuery("SELECT p from Personne p where p.mail=:mail and p.mdp=:mdp");
		query.setParameter("mail", mail);
		query.setParameter("mdp", mdp);
		personne = (Personne) query.getSingleResult();
		
		//Compte compte = em.createQuery("SELECT c from Compte c where c.login=:login and c.password=:password",Compte.class).setParameter("login", login).setParameter("password", password).getSingleResult();
		
		em.close();
		}catch(Exception e) {e.printStackTrace();}
		
		return personne;
	}

}
