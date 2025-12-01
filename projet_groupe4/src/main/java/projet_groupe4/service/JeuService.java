package projet_groupe4.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dto.request.JeuRequest;
import projet_groupe4.dto.response.TopJeuResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Emprunt;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutLocation;
import projet_groupe4.model.StatutReservation;

@Service
public class JeuService {
	private final IDAOJeu dao;
	private final EmpruntService empruntService;
    private final ReservationService reservationService;

	public JeuService(IDAOJeu dao,  EmpruntService empruntService, ReservationService reservationService) {
		this.dao = dao;
		this.empruntService = empruntService;
        this.reservationService = reservationService;
	}

	public List<Jeu> getAll() {
		return this.dao.findAll();
	}

	public Optional<Jeu> getById(Integer id) {
		return this.dao.findById(id);
	}

	public Jeu create(JeuRequest request) {
		return this.save(new Jeu(), request);
	}

	public Jeu update(Integer id, JeuRequest request) {
		Jeu jeu = getById(id).orElseThrow(() -> new IdNotFoundException());
		return this.save(jeu, request);
	}

	public void deleteById(Integer id) {
		this.dao.deleteById(id);
	}

	public void delete(Jeu jeu) {
		this.dao.delete(jeu);
	}

	public List<Jeu> getByNomContaining(String nom) {
		if (nom == null || nom.trim().isEmpty()) {
			return getAll();
		}
		return this.dao.findByNomContaining(nom);
	}

	private Jeu save(Jeu jeu, JeuRequest request) {
		jeu.setNom(request.getNom());
		jeu.setTypesJeux(request.getTypesJeux());
		jeu.setAgeMinimum(request.getAgeMinimum());
		jeu.setNbJoueurMinimum(request.getNbJoueurMinimum());
		jeu.setNbJoueurMaximum(request.getNbJoueurMaximum());
		jeu.setDuree(request.getDuree());
		jeu.setNbExemplaire(request.getNbExemplaire());
		jeu.setNote(request.getNote());
		jeu.setCategoriesJeux(request.getCategoriesJeux());
		jeu.setImgURL(request.getImgURL());
		jeu.setBesoinGameMaster(request.isBesoinGameMaster());

		return this.dao.save(jeu);
	}
	
	public List<Jeu> getDisponibles(LocalDate dateDebut){
		LocalDate dateFin = dateDebut.plusDays(15);
		LocalDateTime debutLDT = dateDebut.atStartOfDay();
	    LocalDateTime finLDT = dateFin.atTime(23, 59);
	    
	    
		List<Jeu> allJeux = dao.findAll();
        List<Emprunt> empruntsActifs = empruntService.getAll();
        List<Reservation> reservationsActives = reservationService.getAll();
        
        return allJeux.stream().filter(jeu -> {
            int enUtilisation = 0;

            // Vérifier les emprunts en conflit
            for (Emprunt e : empruntsActifs) {
                if (e.getJeu().equals(jeu) &&
                    (e.getStatutLocation() == StatutLocation.enCours || e.getStatutLocation() == StatutLocation.enRetard) 
                    &&
                    !e.getDateEmprunt().isAfter(dateFin) &&  // e.getDateEmprunt() <= dateFin
                    !e.getDateRetour().isBefore(dateDebut)) { // e.getDateRetour() >= dateDebut
                    enUtilisation++;
                }
            }

            // Vérifier les réservations confirmées en conflit (LocalDateTime)
            for (Reservation r : reservationsActives) {
                if (r.getJeu().equals(jeu) &&
                    r.getStatutReservation() == StatutReservation.confirmée &&
                    !r.getDatetimeDebut().isAfter(finLDT) &&
                    !r.getDatetimeFin().isBefore(debutLDT)) {
                    enUtilisation++;
                }
            }

            return enUtilisation < jeu.getNbExemplaire();
        }).toList();
    }

	public List<TopJeuResponse> getTop3Notes() {
	    return dao.findTopByNote().stream()
	              .limit(3)
	              .map(j -> new TopJeuResponse(j, j.getNote()))
	              .toList();
	}

}
