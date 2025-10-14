package barJeux.dao;

import java.util.List;

import barJeux.model.Client;
import barJeux.model.Employe;
import barJeux.model.Personne;

public interface IDAOPersonne extends IDAO<Personne,Integer> {

	public List<Client> findAllClient();

	public List<Employe> findAllEmploye();

	public Client findByIdWithEmprunts(Integer idClient);

	public Client findByIdWithReservations(Integer idClient);

	public List<Personne> findByNomLike(String nom);

	public List<Personne> findByPrenomLike(String prenom);

	public Personne findByLoginAndPassword(String mail, String mdp);

}
