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

import jakarta.validation.Valid;
import projet_groupe4.dto.request.ChangePasswordRequest;
import projet_groupe4.dto.request.SubscribeClientRequest;
import projet_groupe4.dto.request.UpdateClientRequest;
import projet_groupe4.dto.response.ClientResponse;
import projet_groupe4.dto.response.ClientWithEmpruntResponse;
import projet_groupe4.dto.response.ClientWithReservationResponse;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Personne;
import projet_groupe4.service.PersonneService;

@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class ClientRestController {
    private final PersonneService srv;
    private final static Logger log = LoggerFactory.getLogger(ClientRestController.class);

    public ClientRestController(PersonneService srv) {
        this.srv = srv;
    }

    @GetMapping
    public List<ClientResponse> allClients() {
        log.debug("Liste des réservations");

        return this.srv.getAllClients().stream().map(ClientResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public ClientResponse ficheClient(@PathVariable int id) {
        log.debug("Fiche client id {}",id);
        return this.srv.getClientById(id).map(ClientResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @GetMapping("/reservations/{id}")
    public ClientWithReservationResponse clientWithResa(@PathVariable int id) {
        log.debug("Récupération du client {} avec les réservations",id);
        Client client = this.srv.getClientById(id).orElseThrow(IdNotFoundException::new);
        return ClientWithReservationResponse.convert(client);
    }

    @GetMapping("/emprunts/{id}")
    public ClientWithEmpruntResponse clientWithEmprunts(@PathVariable int id) {
        log.debug("Récupération du client {} avec les emprunts",id);
        Client client = this.srv.getClientById(id).orElseThrow(IdNotFoundException::new);
        return ClientWithEmpruntResponse.convert(client);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse ajouterClient(@Valid @RequestBody SubscribeClientRequest request) {
        log.debug("Création d'un nouveau client");
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYE','CLIENT')")
    public EntityUpdatedResponse modifierClient(@PathVariable int id,
        @Valid @RequestBody UpdateClientRequest request) {
        log.debug("Modification du client id {}",id);
        this.srv.update(id, request); 
    return new EntityUpdatedResponse(id, true);
}

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Integer id) {
        log.debug("Suppression du client id {}",id);
        this.srv.deleteById(id);
    }
    
    @GetMapping("/username/{email}")
    public ClientResponse getByUsername(@PathVariable String email) {
        log.debug("Recherche client par username");
        Personne p = srv.getByMail(email)
                        .orElseThrow(IdNotFoundException::new);
        if (!(p instanceof Client client)) {
            throw new IdNotFoundException(); // si ce n'est pas un client
        }
        return ClientResponse.convert(client);
    }

@PutMapping("/{id}/password")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public ResponseEntity<String> changePassword(@PathVariable int id, 
                                            @Valid @RequestBody ChangePasswordRequest request) {
    log.debug("Modification du mdp");
    boolean isPasswordChanged = this.srv.changePassword(id, request.getOldPassword(), request.getNewPassword());
    
    if (isPasswordChanged) {
        return ResponseEntity.ok("Password updated successfully");
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Old password is incorrect");
    }
}
    

}