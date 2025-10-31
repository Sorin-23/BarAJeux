package barJeux.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import barJeux.model.Client;
import barJeux.model.Personne;

public interface IDAOPersonne extends JpaRepository<Personne,Integer> {

	//public List<Client> findAllClient(); Plus besoin directement obtenu avec List<Client> clients = daoPersonne.findAll();

	//public List<Employe> findAllEmploye(); pareil

	//public Client findByIdWithEmprunts(Integer idClient);
	@Query("SELECT c FROM Client c LEFT JOIN FETCH c.emprunts WHERE c.id = :id")
    public Client findByIdWithEmprunts(@Param("id") Integer id);
	
	//public Client findByIdWithReservations(Integer idClient);
	@Query("SELECT c from Client c LEFT JOIN FETCH c.reservations where c.id=:id")
    public Client findByIdWithReservations(@Param("id") Integer id);

	//public List<Personne> findByNomLike(String nom); 
	public List<Personne> findByNomContaining(String nom);
	

	//public List<Personne> findByPrenomLike(String prenom);
	public List<Personne> findByPrenomContaining(String prenom);

	// public Personne findByLoginAndPassword(String mail, String mdp); 
	public Personne findByLoginAndPassword(String mail, String mdp);

}
