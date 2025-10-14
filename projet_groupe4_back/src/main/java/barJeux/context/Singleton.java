package barJeux.context;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import barJeux.dao.*;




public class Singleton {


	
	private static Singleton instance=null;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("configJPA");
	private IDAOAvis daoAvis = new DAOAvis();
	private IDAOBadge daoBadge = new DAOBadge();
	private IDAOEmprunt daoEmprunt = new DAOEmprunt();
	private IDAOJeu daoJeu = new DAOJeu();
	private IDAOPersonne daoPersonne = new DAOPersonne();
	private IDAOReservation daoReservation = new DAOReservation();
	private IDAOTableJeu daoTableJeu = new DAOTableJeu();
	
	


	private Singleton() {}
	
	public static Singleton getInstance() {
		if(instance==null) {instance = new Singleton();}
		return instance;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public IDAOAvis getDaoAvis() {
		return daoAvis;
	}
	public IDAOBadge getDaoBadge() {
		return daoBadge;
	}
	public IDAOEmprunt getDaoEmprunt() {
		return daoEmprunt;
	}
	public IDAOJeu getDaoJeu() {
		return daoJeu;
	}
	public IDAOPersonne getDaoPersonne() {
		return daoPersonne;
	}
	public IDAOReservation getDaoReservation() {
		return daoReservation;
	}
	public IDAOTableJeu getDaoTableJeu() {
		return daoTableJeu;
	}

	
	
	
	
	
}
