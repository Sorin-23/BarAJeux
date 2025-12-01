package projet_groupe4.rest;

import java.util.List;

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

    public TopController(EmpruntService empruntService,
                         ReservationService reservationService,
                         JeuService jeuService) {
        this.empruntService = empruntService;
        this.reservationService = reservationService;
        this.jeuService = jeuService;
    }

    @GetMapping("/emprunts")
    public List<TopJeuResponse> topEmprunts() {
        return empruntService.getTop3Emprunts();
    }

    @GetMapping("/reservations")
    public List<TopJeuResponse> topReservations() {
        return reservationService.getTop3Reservations();
    }

    @GetMapping("/notes")
    public List<TopJeuResponse> topNotes() {
        return jeuService.getTop3Notes();
    }
}
