package projet_groupe4.rest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import projet_groupe4.dto.request.ReservationRequest;
import projet_groupe4.dto.response.ReservationResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Reservation;
import projet_groupe4.service.ReservationService;

@RestController
@RequestMapping("/api/reservation")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class ReservationRestController {

    @Autowired
    private ReservationService srv;

    @GetMapping
    public List<ReservationResponse> allReservations() {
        return this.srv.getAll().stream().map(ReservationResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public ReservationResponse ficheReservation(@PathVariable int id) {
        return this.srv.getById(id).map(ReservationResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYE')")
    public ReservationResponse ajouterReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(request, reservation);

        this.srv.create(reservation);

        return ReservationResponse.convert(reservation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public ReservationResponse modifierReservation(@PathVariable int id,
            @Valid @RequestBody ReservationRequest request) {
        Reservation reservation = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(request, reservation);

        this.srv.update(reservation);

        return ReservationResponse.convert(reservation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public void deleteReservation(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }

}