package projet_groupe4.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.SubscribeClientRequest;
import projet_groupe4.dto.request.SubscribeEmployeRequest;
import projet_groupe4.dto.request.UpdateClientRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.exception.ResourceNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Personne;

@Service
public class PersonneService {
	private final IDAOPersonne dao;
	private final PasswordEncoder passwordEncoder;
	private final static Logger log = LoggerFactory.getLogger(PersonneService.class);



	public PersonneService(IDAOPersonne dao, PasswordEncoder passwordEncoder) {
		this.dao = dao;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Personne> getAll() {
		log.debug("Liste des comptes");
		return this.dao.findAll();
	}

	public List<Client> getAllClients() {
		log.debug("Liste des clients");
		return this.dao.findAllClient();
	}

	public List<Employe> getAllEmployes() {
		log.debug("Liste des employés");
		return this.dao.findAllEmploye();
	}

	public Personne getById(Integer id) {
		log.debug("Récupération du compte id {}",id);
		return this.dao.findById(id).orElseThrow(() -> new ResourceNotFoundException());
	}

	public Optional<Client> getClientById(Integer clientId) {
		log.debug("Récupération du client id {}",clientId);
		return dao.findById(clientId)
				.filter(p -> p instanceof Client) // garde uniquement si c’est un Client
				.map(p -> (Client) p);
	}

	public Optional<Employe> getEmployeById(Integer empId) {
		log.debug("Récupération de l'employé id {}",empId);
		return dao.findById(empId)
				.filter(p -> p instanceof Employe) // garde uniquement si c’est un Client
				.map(p -> (Employe) p);
	}

	public Optional<Personne> getByMail(String mail) {
		log.debug("Récupération du compte avec mail {}",mail);
		return this.dao.findByMail(mail);
	}

	public Personne create(Object request) {
		log.debug("Création d'un nouveau compte");
		if (request instanceof SubscribeClientRequest clientRequest) {
			return saveClient(new Client(), clientRequest);
		} else if (request instanceof SubscribeEmployeRequest employeRequest) {
			return saveEmploye(new Employe(), employeRequest);
		} else {
			throw new IllegalArgumentException("Type de requête inconnu pour la création d'une personne");
		}
	}

	/*
	 * *
	 * public Personne update(Integer id, Object request) {
	 * if (request instanceof SubscribeClientRequest clientRequest) {
	 * Client client = this.getClientById(id).orElseThrow(IdNotFoundException::new);
	 * return saveClient(client, clientRequest);
	 * }
	 * 
	 * else if (request instanceof SubscribeEmployeRequest employeRequest) {
	 * Employe employe =
	 * this.getEmployeById(id).orElseThrow(IdNotFoundException::new);
	 * return saveEmploye(employe, employeRequest);
	 * } else {
	 * 
	 * throw new
	 * IllegalArgumentException("Type de requête non supporté pour l'update");
	 * }
	 * }
	 */
	private Client updateClient(Client client, UpdateClientRequest request) {
		// On met à jour uniquement les infos de profil modifiables depuis la page
		// client
		log.debug("Mise à jour du client {}",client.getId());
		client.setNom(request.getNom());
		client.setPrenom(request.getPrenom());
		client.setMail(request.getMail());
		client.setTelephone(request.getTelephone());
		client.setVille(request.getVille());
		client.setCodePostale(request.getCodePostale());
		client.setAdresse(request.getAdresse());

		 // Si un nouveau mot de passe est fourni, on l’encode et on le sauvegarde
    if (request.getMdp() != null && !request.getMdp().isBlank()) {
        client.setMdp(passwordEncoder.encode(request.getMdp()));
    }

		// On NE touche PAS au mot de passe, ni aux dates ici
		return this.dao.save(client);
	}



	public Personne update(Integer id, Object request) {
		log.debug("Update du compte id {}",id);
		// 1. Nouveau cas : mise à jour du profil client avec UpdateClientRequest
		if (request instanceof UpdateClientRequest updateReq) {
			log.debug("Mise à jour du client");
			Client client = this.getClientById(id).orElseThrow(IdNotFoundException::new);
			return updateClient(client, updateReq);
		}

		// 2. Ancien cas : inscription / mise à jour complète avec
		// SubscribeClientRequest
		if (request instanceof SubscribeClientRequest clientRequest) {
			log.debug("inscription/mise à jour complète d'un client");
			Client client = this.getClientById(id).orElseThrow(IdNotFoundException::new);
			return saveClient(client, clientRequest);
		}

		// 3. Cas employé inchangé
		if (request instanceof SubscribeEmployeRequest employeRequest) {
			log.debug("Mise à jour/inscription complète d'un employé");
			Employe employe = this.getEmployeById(id).orElseThrow(IdNotFoundException::new);
			return saveEmploye(employe, employeRequest);
		}

		// 4. Fallback
		throw new IllegalArgumentException("Type de requête non supporté pour l'update");
	}

	public Personne updateInfosConnect(Integer id, String login, String password) {
		log.debug("Update info connexion compte {}",login);
		Personne personne = this.dao.findById(id).orElseThrow(IdNotFoundException::new);
		personne.setMail(login);
		if (password != null) {
			personne.setMdp(this.passwordEncoder.encode(password));
		}
		return this.dao.save(personne);
	}

	public void deleteById(Integer id) {
		log.debug("Suppression du compte par id {}",id);
		this.dao.deleteById(id);
	}

	public void delete(Personne personne) {
		log.debug("Suppression du compte");
		this.dao.delete(personne);
	}

	public List<Personne> getByNomContaining(String nom) {
		log.debug("Liste des comptes dont le nom contient {}",nom);
		if (nom == null || nom.trim().isEmpty()) {
			return getAll();
		}
		return this.dao.findByNomContaining(nom);
	}

	public List<Personne> getByPrenomContaining(String prenom) {
		log.debug("Liste des comptes dont le prenom contient {}",prenom);
		if (prenom == null || prenom.trim().isEmpty()) {
			return getAll();
		}
		return this.dao.findByPrenomContaining(prenom);
	}

	public Optional<Client> getByIdWithEmprunts(@Param("id") Integer id) {
		log.debug("Client avec les emprunts {}",id);
		return this.dao.findByIdWithEmprunts(id);
	}

	public Optional<Client> getByIdWithReservations(@Param("id") Integer id) {
		log.debug("Client avec réservations {}",id);
		return this.dao.findByIdWithReservations(id);
	}

	private Client saveClient(Client client, SubscribeClientRequest request) {
		log.debug("Mise à jour/Inscription compte client");
		client.setNom(request.getNom());
		client.setPrenom(request.getPrenom());
		client.setMail(request.getMail());
		client.setMdp(this.passwordEncoder.encode(request.getMdp()));
		client.setTelephone(request.getTelephone());
		client.setVille(request.getVille());
		client.setCodePostale(request.getCodePostale());
		client.setAdresse(request.getAdresse());
		client.setDateCreation(LocalDateTime.now());
		client.setDateLastConnexion(LocalDateTime.now());

		return this.dao.save(client);
	}

	private Employe saveEmploye(Employe employe, SubscribeEmployeRequest request) {
		log.debug("Mise à jour/Inscription compte employé");
		employe.setNom(request.getNom());
		employe.setPrenom(request.getPrenom());
		employe.setMail(request.getMail());
		employe.setMdp(this.passwordEncoder.encode(request.getMdp()));
		employe.setTelephone(request.getTelephone());
		employe.setJob(request.getJob());
		employe.setGameMaster(request.isGameMaster());

		return this.dao.save(employe);
	}

	public void updateLastConnexion(String mail) {
		log.debug("Update de la dernière connexion du client");
		this.dao.findByMail(mail)
		.filter(p -> p instanceof Client) // optionnel si tu veux seulement les clients
		.map(p -> (Client) p)
		.ifPresent(client -> {
			client.setDateLastConnexion(LocalDateTime.now());
			this.dao.save(client);
		});
	}


	public void updateDerniereReservation(String mail) {
		log.debug("Update de la dernière réservation du client");
		this.dao.findByMail(mail)
		.filter(p -> p instanceof Client)
		.map(p -> (Client) p)
		.ifPresent(client -> {
			client.setDateDerniereReservation(LocalDateTime.now());
			this.dao.save(client);
		});
	}

	
/* 
	public boolean changePassword(int id, String oldPassword, String newPassword) {
		Employe emp = this.dao.findEmployeById(id)
				.orElseThrow(() -> new RuntimeException("Employé introuvable"));

		// Vérifier l’ancien mot de passe
		if (!passwordEncoder.matches(oldPassword, emp.getMdp())) {
			
			return false; // ancien mot de passe incorrect
		}

		// Encoder le nouveau
		emp.setMdp(passwordEncoder.encode(newPassword));
		dao.save(emp);

		return true;

	}

*/

public boolean changePassword(int id, String oldPassword, String newPassword) {
	log.debug("Changement du mdp");
    
    Object entity = this.dao.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));

   
    if (entity instanceof Employe) {
		log.debug("Changement mdp employé");
        Employe emp = (Employe) entity;
        
        if (!passwordEncoder.matches(oldPassword, emp.getMdp())) {
			log.debug("ancien mdp incorrect");
            return false; 
        }
       
        emp.setMdp(passwordEncoder.encode(newPassword));
        dao.save(emp);
        return true;
    }

  
    if (entity instanceof Client) {
		log.debug("Changement mdp client");
        Client client = (Client) entity;
       
        if (!passwordEncoder.matches(oldPassword, client.getMdp())) {
            log.debug("ancien mdp incorrect");
			return false; 
        }
       
        client.setMdp(passwordEncoder.encode(newPassword));
        dao.save(client);
        return true;
    }

   
    return false;
}

}
