package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.exception.ResourceNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Personne;

@Service
public class PersonneService {
	@Autowired
	private IDAOPersonne dao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	public List<Personne> getAll(){
		return this.dao.findAll();
	}
	
	public List<Client> getAllClients(){
		return this.dao.findAllClient();
	}
	public List<Employe> getAllEmployes(){
		return this.dao.findAllEmploye();
	}
	public Personne getById(Integer id) {
		return this.dao.findById(id).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	public Optional<Client> getClientById(Integer clientId) {
        return dao.findById(clientId)
				.filter(p -> p instanceof Client) // garde uniquement si c’est un Client
				.map(p -> (Client) p);
	}
	
	public Optional<Employe> getEmployeById(Integer empId) {
       	return dao.findById(empId)
				.filter(p -> p instanceof Employe) // garde uniquement si c’est un Client
				.map(p -> (Employe) p);
	}
	
	public Optional<Personne> getByLogin(String login)
	{
		return this.dao.findByLogin(login);
	}
	
	public Personne create(Personne personne)
	{
		if(personne.getId()!=null)
		{
			throw new RuntimeException("Comment ca une personne en insert a deja un id ?!");
		}

		personne.setMdp(this.passwordEncoder.encode(personne.getMdp()));
		return this.dao.save(personne);
	}

	public Personne update(Personne personne)
	{
		if(personne.getId()==null)
		{
			throw new RuntimeException("Comment ca une personne en update a sans un id ?!");
		}

		if (personne.getMdp() != null) {
			personne.setMdp(this.passwordEncoder.encode(personne.getMdp()));
		}

		return this.dao.save(personne);
	}

	public Personne updateInfosConnect(Integer id,String login,String password)
	{
		Personne personne = this.dao.findById(id).get();
		personne.setMail(login);
		personne.setMdp(password);
		return this.dao.save(personne);
	}

	public void deleteById(Integer id)
	{
		this.dao.deleteById(id);
	}

	public void delete(Personne personne)
	{
		this.dao.delete(personne);
	}
	
	public List<Personne> getByNomContaining(String nom){
		if (nom == null || nom.trim().isEmpty()) {
	        return getAll();
	    }
		return this.dao.findByNomContaining(nom);
	}
	
	public List<Personne> getByPrenomContaining(String prenom){
		if (prenom == null || prenom.trim().isEmpty()) {
	        return getAll();
	    }
		return this.dao.findByNomContaining(prenom);
	}
	
	
    public Client findByIdWithEmprunts(@Param("id") Integer id) {
    	return this.dao.findByIdWithEmprunts(id);
    }
	
	
    public Client findByIdWithReservations(@Param("id") Integer id) {
    	return this.dao.findByIdWithReservations(id);
    }
	
	
}
