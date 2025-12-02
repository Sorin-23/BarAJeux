package projet_groupe4.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.SubscribeClientRequest;
import projet_groupe4.dto.request.SubscribeEmployeRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.exception.ResourceNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Personne;
import projet_groupe4.dto.request.UpdateClientRequest;

@Service
public class PersonneService {
	private final IDAOPersonne dao;
	private final PasswordEncoder passwordEncoder;

	public PersonneService(IDAOPersonne dao, PasswordEncoder passwordEncoder) {
		this.dao = dao;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Personne> getAll() {
		return this.dao.findAll();
	}

	public List<Client> getAllClients() {
		return this.dao.findAllClient();
	}

	public List<Employe> getAllEmployes() {
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

	public Optional<Personne> getByMail(String mail) {
		return this.dao.findByMail(mail);
	}

	public Personne create(Object request) {
		if (request instanceof SubscribeClientRequest clientRequest) {
			return saveClient(new Client(), clientRequest);
		} else if (request instanceof SubscribeEmployeRequest employeRequest) {
			return saveEmploye(new Employe(), employeRequest);
		} else {
			throw new IllegalArgumentException("Type de requête inconnu pour la création d'une personne");
		}
	}
	/* *
	public Personne update(Integer id, Object request) {
		if (request instanceof SubscribeClientRequest clientRequest) {
			Client client = this.getClientById(id).orElseThrow(IdNotFoundException::new);
			return saveClient(client, clientRequest);
		}

		else if (request instanceof SubscribeEmployeRequest employeRequest) {
			Employe employe = this.getEmployeById(id).orElseThrow(IdNotFoundException::new);
			return saveEmploye(employe, employeRequest);
		} else {

			throw new IllegalArgumentException("Type de requête non supporté pour l'update");
		}
	}*/
	private Client updateClient(Client client, UpdateClientRequest request) {
    // On met à jour uniquement les infos de profil modifiables depuis la page client
    client.setNom(request.getNom());
    client.setPrenom(request.getPrenom());
    client.setMail(request.getMail());
    client.setTelephone(request.getTelephone());
    client.setVille(request.getVille());
    client.setCodePostale(request.getCodePostale());
    client.setAdresse(request.getAdresse());

    // On NE touche PAS au mot de passe, ni aux dates ici
    return this.dao.save(client);
}


	public Personne update(Integer id, Object request) {
    // 1. Nouveau cas : mise à jour du profil client avec UpdateClientRequest
    if (request instanceof UpdateClientRequest updateReq) {
        Client client = this.getClientById(id).orElseThrow(IdNotFoundException::new);
        return updateClient(client, updateReq);
    }

    // 2. Ancien cas : inscription / mise à jour complète avec SubscribeClientRequest
    if (request instanceof SubscribeClientRequest clientRequest) {
        Client client = this.getClientById(id).orElseThrow(IdNotFoundException::new);
        return saveClient(client, clientRequest);
    }

    // 3. Cas employé inchangé
    if (request instanceof SubscribeEmployeRequest employeRequest) {
        Employe employe = this.getEmployeById(id).orElseThrow(IdNotFoundException::new);
        return saveEmploye(employe, employeRequest);
    }

    // 4. Fallback
    throw new IllegalArgumentException("Type de requête non supporté pour l'update");
}


	public Personne updateInfosConnect(Integer id, String login, String password) {
		Personne personne = this.dao.findById(id).orElseThrow(IdNotFoundException::new);
		personne.setMail(login);
		if (password != null) {
			personne.setMdp(this.passwordEncoder.encode(password));
		}
		return this.dao.save(personne);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(Personne personne) {
		this.dao.delete(personne);
	}

	public List<Personne> getByNomContaining(String nom) {
		if (nom == null || nom.trim().isEmpty()) {
			return getAll();
		}
		return this.dao.findByNomContaining(nom);
	}

	public List<Personne> getByPrenomContaining(String prenom) {
		if (prenom == null || prenom.trim().isEmpty()) {
			return getAll();
		}
		return this.dao.findByPrenomContaining(prenom);
	}

	public Optional<Client> getByIdWithEmprunts(@Param("id") Integer id) {
		return this.dao.findByIdWithEmprunts(id);
	}

	public Optional<Client> getByIdWithReservations(@Param("id") Integer id) {
		return this.dao.findByIdWithReservations(id);
	}

	private Client saveClient(Client client, SubscribeClientRequest request) {
		client.setNom(request.getNom());
		client.setPrenom(request.getPrenom());
		client.setMail(request.getMail());
		client.setMdp(this.passwordEncoder.encode(request.getMdp()));
		client.setTelephone(request.getTelephone());
		client.setVille(request.getVille());
		client.setCodePostale(request.getCodePostale());
		client.setAdresse(request.getAdresse());
		client.setDateCreation(LocalDate.now());
		client.setDateLastConnexion(LocalDate.now());

		return this.dao.save(client);
	}

	private Employe saveEmploye(Employe employe, SubscribeEmployeRequest request) {
		employe.setNom(request.getNom());
		employe.setPrenom(request.getPrenom());
		employe.setMail(request.getMail());
		employe.setMdp(this.passwordEncoder.encode(request.getMdp()));
		employe.setTelephone(request.getTelephone());
		employe.setJob(request.getJob());
		employe.setGameMaster(request.isGameMaster());

		return this.dao.save(employe);
	}

}
