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

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;
import projet_groupe4.dto.request.SubcribeClientRequest;
import projet_groupe4.dto.response.ClientResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Client;
import projet_groupe4.service.PersonneService;
import projet_groupe4.view.Views;

@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class ClientRestController {

    @Autowired
    private PersonneService srv;

    @GetMapping
    @JsonView(Views.Client.class)
    public List<ClientResponse> allClients() {

        return this.srv.getAllClients().stream().map(ClientResponse::convert).toList();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Client.class)
    public ClientResponse ficheClient(@PathVariable int id) {
        return this.srv.getClientById(id).map(ClientResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @JsonView(Views.Client.class)
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ClientResponse ajouterClient(@Valid @RequestBody SubcribeClientRequest request) {
        Client client = new Client();
        BeanUtils.copyProperties(request, client);

        this.srv.create(client);

        return ClientResponse.convert(client);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Client.class)
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ClientResponse modifierClient(@PathVariable int id, @Valid @RequestBody SubcribeClientRequest request) {
        Client client = this.srv.getClientById(id).orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(request, client);

        this.srv.update(client);

        return ClientResponse.convert(client);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public void deleteClient(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }

}