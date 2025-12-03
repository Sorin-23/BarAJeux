package projet_groupe4.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Personne;

public interface IDAOPersonne extends JpaRepository<Personne, Integer> {

	@Query("from Client")
	public List<Client> findAllClient();

	@Query("from Employe")
	public List<Employe> findAllEmploye();

	@Query("Select c from Client c where c.id =:id")
	public Optional<Client> findClientById(@Param("id") Integer id);

	// public Client findByIdWithEmprunts(Integer idClient);
	@Query("SELECT c FROM Client c LEFT JOIN FETCH c.emprunts WHERE c.id = :id")
	public Optional<Client> findByIdWithEmprunts(@Param("id") Integer id);

	// public Client findByIdWithReservations(Integer idClient);
	@Query("SELECT c from Client c LEFT JOIN FETCH c.reservations where c.id=:id")
	public Optional<Client> findByIdWithReservations(@Param("id") Integer id);

	// public List<Personne> findByNomLike(String nom);
	public List<Personne> findByNomContaining(String nom);

	// public List<Personne> findByPrenomLike(String prenom);
	public List<Personne> findByPrenomContaining(String prenom);

	// public Personne findByLoginAndPassword(String mail, String mdp);
	public Optional<Personne> findByMail(String mail);

	/*public List<Reservation> findByGameMasterId(int id);*/

	@Query("SELECT e FROM Employe e WHERE e.id = :id")
	public Optional<Employe> findEmployeById(int id);

}
