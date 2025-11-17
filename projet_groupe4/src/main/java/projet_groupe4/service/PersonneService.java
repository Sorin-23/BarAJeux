package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.exception.BadRequestException;
import projet_groupe4.exception.ResourceNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Jeu;
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
	
	public Client getClientById(Integer clientId) {
        Optional personne = this.dao.findById(clientId);
        if(personne.isEmpty()) {return null;}
		else {

			if(personne.get() instanceof Client)
			{
				return (Client) personne.get();
			}
			else
			{
				throw new BadRequestException();
			}
		}
	}
	
	public Employe getEmployeById(Integer empId) {
        Optional personne = this.dao.findById(empId);
        if(personne.isEmpty()) {return null;}
		else {

			if(personne.get() instanceof Employe)
			{
				return (Employe) personne.get();
			}
			else
			{
				throw new BadRequestException();
			}
		}
	}
	
	public Personne getByLoginAndPassword(String login,String password)
	{
		return this.dao.findByLoginAndPassword(login,password);
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
