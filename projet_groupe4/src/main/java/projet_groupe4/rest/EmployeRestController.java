package projet_groupe4.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


import jakarta.validation.Valid;
import projet_groupe4.dto.request.PasswordEmployeRequest;
import projet_groupe4.dto.request.SubscribeEmployeRequest;
import projet_groupe4.dto.response.EmployeResponse;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Personne;
import projet_groupe4.service.PersonneService;

@RestController
@RequestMapping("/api/employe")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class EmployeRestController {

    private final PersonneService srv;
    private final static Logger log = LoggerFactory.getLogger(EmployeRestController.class);

    public EmployeRestController(PersonneService srv) {
        this.srv = srv;
    }

    @GetMapping
    public List<EmployeResponse> allEmployes() {
        log.debug("Appel de allEmployes");
        return this.srv.getAllEmployes().stream().map(EmployeResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public EmployeResponse ficheEmploye(@PathVariable Integer id) {
        log.debug("Récupération de l'employé id {}", id);
        return this.srv.getEmployeById(id).map(EmployeResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse ajouterEmploye(@Valid @RequestBody SubscribeEmployeRequest request) {
        log.debug("Création d'un compte employé");
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public EntityUpdatedResponse modifierEmploye(@PathVariable Integer id,
            @Valid @RequestBody SubscribeEmployeRequest request) {

        log.debug("Mise à jour compte employé id {}", id);
        this.srv.update(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable Integer id) {
        log.debug("Suppression de l'employé id {}", id);
        this.srv.deleteById(id);
    }

    @GetMapping("/username/{email}")
    public EmployeResponse getByUsername(@PathVariable String email) {
        log.debug("Employé par username");
        Personne p = srv.getByMail(email)
                .orElseThrow(IdNotFoundException::new);
        if (!(p instanceof Employe emp)) {
            throw new IdNotFoundException();
        }
        return EmployeResponse.convert(emp);
    }

    @GetMapping("/gamemasters")
    public List<EmployeResponse> allGameMasters() {
        log.debug("Appel de allGameMasters");
        return this.srv.getAllEmployes().stream()
                .filter(Employe::isGameMaster)
                .map(EmployeResponse::convert)
                .toList();
    }

    /*
     * @GetMapping("/gamemaster/{id}")
     * 
     * @PreAuthorize("hasRole('EMPLOYE')")
     * public List<Reservation> getReservationsByGameMaster(@PathVariable int id) {
     * return this.srv.findByGameMasterId(id);
     * }
     */

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('EMPLOYE')")
    public ResponseEntity<?> changePassword(
            @PathVariable Integer id,
            @RequestBody PasswordEmployeRequest req) {
        log.debug("Changer le mdp");
        boolean ok = srv.changePassword(id, req.getOldPassword(), req.getNewPassword());

        if (!ok) {
            return ResponseEntity.status(403)
                    .body("Ancien mot de passe incorrect");
        }

        return ResponseEntity.ok(new EntityUpdatedResponse(id, true));
    }

}


@GetMapping("/me")
    @PreAuthorize("hasAnyRole('EMPLOYE')") // Ensure only employees access this
    public EmployeResponse getMyProfile(Authentication authentication) {
        // 1. Get email from the JWT Token
        String email = authentication.getName();

        // 2. Find the person using the existing Service method
        Personne p = srv.getByMail(email)
                        .orElseThrow(IdNotFoundException::new);

        // 3. Ensure it is actually an Employe (not a Client)
        if (!(p instanceof Employe emp)) {
            throw new IdNotFoundException(); // Or a specific Forbidden exception
        }

        // 4. Convert to DTO
        return EmployeResponse.convert(emp);
    }

    /**
     * PUT /api/employe/me
     * Updates the currently logged-in employee's info.
     */
    @PutMapping("/me")
    @PreAuthorize("hasRole('EMPLOYE')")
    public EntityUpdatedResponse updateMyProfile(
            Authentication authentication, 
            @Valid @RequestBody SubscribeEmployeRequest request) {
        
        // 1. Get email from Token
        String email = authentication.getName();

        // 2. Get the specific ID of this user
        Personne p = srv.getByMail(email)
                        .orElseThrow(IdNotFoundException::new);

        // 3. Reuse the existing update method using the found ID
        this.srv.update(p.getId(), request);

        return new EntityUpdatedResponse(p.getId(), true);
    }

}