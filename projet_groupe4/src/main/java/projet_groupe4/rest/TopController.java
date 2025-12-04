package projet_groupe4.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet_groupe4.dto.response.TopJeuResponse;
import projet_groupe4.service.EmpruntService;
import projet_groupe4.service.JeuService;
import projet_groupe4.service.ReservationService;

@RestController
@RequestMapping("/api/top")
public class TopController {

	private final EmpruntService empruntService;
    private final ReservationService reservationService;
    private final JeuService jeuService;
    private final static Logger log = LoggerFactory.getLogger(TopController.class);

    public TopController(EmpruntService empruntService,
                         ReservationService reservationService,
                         JeuService jeuService) {
        this.empruntService = empruntService;
        this.reservationService = reservationService;
        this.jeuService = jeuService;
    }

    @GetMapping("/emprunts")
    public List<TopJeuResponse> topEmprunts() {
        log.debug("Liste des jeux les plus empruntés");
        return empruntService.getTop3Emprunts();
    }

    @GetMapping("/reservations")
    public List<TopJeuResponse> topReservations() {
        log.debug("Liste des jeux les plus réservés");
        return reservationService.getTop3Reservations();
    }

    @GetMapping("/notes")
    public List<TopJeuResponse> topNotes() {
        log.debug("Liste des jeux les mieux notés");
        return jeuService.getTop3Notes();
    }
}
