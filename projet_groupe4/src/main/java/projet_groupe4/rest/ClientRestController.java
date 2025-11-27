package projet_groupe4.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import projet_groupe4.dto.request.SubscribeClientRequest;
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

    public ClientRestController(PersonneService srv) {
        this.srv = srv;
    }

    @GetMapping
    public List<ClientResponse> allClients() {

        return this.srv.getAllClients().stream().map(ClientResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public ClientResponse ficheClient(@PathVariable int id) {
        return this.srv.getClientById(id).map(ClientResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @GetMapping("/reservations/{id}")
    public ClientWithReservationResponse clientWithResa(@PathVariable int id) {
        Client client = this.srv.getClientById(id).orElseThrow(IdNotFoundException::new);
        return ClientWithReservationResponse.convert(client);
    }

    @GetMapping("/emprunts/{id}")
    public ClientWithEmpruntResponse clientWithEmprunts(@PathVariable int id) {
        Client client = this.srv.getClientById(id).orElseThrow(IdNotFoundException::new);
        return ClientWithEmpruntResponse.convert(client);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse ajouterClient(@Valid @RequestBody SubscribeClientRequest request) {
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public EntityUpdatedResponse modifierClient(@PathVariable int id,
            @Valid @RequestBody SubscribeClientRequest request) {
        this.srv.update(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }
    
    @GetMapping("/username/{email}")
    public ClientResponse getByUsername(@PathVariable String email) {
        Personne p = srv.getByMail(email)
                        .orElseThrow(IdNotFoundException::new);
        if (!(p instanceof Client client)) {
            throw new IdNotFoundException(); // si ce n'est pas un client
        }
        return ClientResponse.convert(client);
    }
    

}