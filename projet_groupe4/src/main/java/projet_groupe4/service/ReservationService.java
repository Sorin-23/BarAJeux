package projet_groupe4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dao.IDAOReservation;
import projet_groupe4.dao.IDAOTableJeu;
import projet_groupe4.dto.request.ReservationRequest;
import projet_groupe4.dto.response.TopJeuResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Personne;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutReservation;

@Service
public class ReservationService {
	private final IDAOReservation dao;
	private final IDAOJeu jeuDao;
	private final IDAOTableJeu tableJeuDao;
	private final IDAOPersonne personneDao;

	public ReservationService(IDAOReservation dao, IDAOJeu jeuDao, IDAOTableJeu tableJeuDao, IDAOPersonne personneDao) {
		this.dao = dao;
		this.jeuDao = jeuDao;
		this.tableJeuDao = tableJeuDao;
		this.personneDao = personneDao;
	}

	public List<Reservation> getAll() {
		return this.dao.findAll();
	}

	public Optional<Reservation> getById(Integer id) {
		return this.dao.findById(id);
	}

	public Reservation create(ReservationRequest request) {
		return this.save(new Reservation(), request);
	}

	public Reservation update(Integer id, ReservationRequest request) {
		Reservation reservation = this.getById(id).orElseThrow(IdNotFoundException::new);
		return this.save(reservation, request);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(Reservation reservation) {
		this.dao.delete(reservation);
	}
	
	public List<TopJeuResponse> getTop3Reservations(){
		List<Object[]> results = dao.findTopReservations();
        return results.stream()
                .map(r -> new TopJeuResponse((Jeu) r[0], ((Long) r[1]).doubleValue()))
                .limit(3)
                .toList();
    
	}
	
	public List<Reservation> findByGameMasterId(int id) {
    	return this.dao.findByGameMasterId(id);
	}


private Reservation save(Reservation reservation, ReservationRequest request) {
    StatutReservation oldStatus = reservation.getStatutReservation();

    
    reservation.setDatetimeDebut(request.getDatetimeDebut());
    reservation.setDatetimeFin(request.getDatetimeFin());
    reservation.setNbJoueur(request.getNbJoueur());
    reservation.setTableJeu(this.tableJeuDao.getReferenceById(request.getTableJeuId()));
    reservation.setJeu(this.jeuDao.getReferenceById(request.getJeuId()));

    
    Personne personne = this.personneDao.findById(request.getClientId())
            .orElseThrow(IdNotFoundException::new);

    if (!(personne instanceof Client)) {
        throw new RuntimeException("L'id ne correspond pas à un client");
    }
    Client client = (Client) personne;  
    reservation.setClient(client);

   
    if (request.getGameMasterId() != null) {
        Personne emp = this.personneDao.findById(request.getGameMasterId())
                .orElseThrow(IdNotFoundException::new);

        if (!(emp instanceof Employe employe)) {
            throw new RuntimeException("L'id ne correspond pas à un employé");
        }

        reservation.setGameMaster(employe);
    } else {
        reservation.setGameMaster(null);
    }

   
    StatutReservation newStatus = request.getStatutReservation();
    reservation.setStatutReservation(newStatus);

   
    Reservation saved = this.dao.save(reservation);

    boolean justBecameConfirmed =
            newStatus == StatutReservation.confirmée &&      
            (oldStatus == null || oldStatus != StatutReservation.confirmée);

    if (justBecameConfirmed) {
        Integer currentPoints = client.getPointFidelite();
        if (currentPoints == null) {
            currentPoints = 0;
        }
       
        client.setPointFidelite(currentPoints + 5); //+5 points pour une réservation confirmée
        this.personneDao.save(client);
    }

    return saved;
}
}