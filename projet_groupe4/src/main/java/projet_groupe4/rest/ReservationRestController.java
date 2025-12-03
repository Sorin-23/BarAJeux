package projet_groupe4.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import projet_groupe4.dto.request.ReservationRequest;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.dto.response.ReservationResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.service.PersonneService;
import projet_groupe4.service.ReservationService;

@RestController
@RequestMapping("/api/reservation")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class ReservationRestController {
    private final ReservationService srv;
    private final PersonneService personneService;

    public ReservationRestController(ReservationService srv, PersonneService personneService) {
        this.srv = srv;
        this.personneService = personneService;
    }

    @GetMapping
    public List<ReservationResponse> allReservations() {
        return this.srv.getAll().stream().map(ReservationResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public ReservationResponse ficheReservation(@PathVariable int id) {
        return this.srv.getById(id).map(ReservationResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse ajouterReservation(@Valid @RequestBody ReservationRequest request) {
        String clientMail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // ⏰ Mise à jour date dernière réservation
        this.personneService.updateDerniereReservation(clientMail);
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYE','CLIENT')")
    public EntityUpdatedResponse modifierReservation(@PathVariable int id,
            @Valid @RequestBody ReservationRequest request) {
        this.srv.update(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }

}