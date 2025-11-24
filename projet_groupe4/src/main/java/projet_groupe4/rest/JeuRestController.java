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
import projet_groupe4.dto.request.JeuRequest;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.dto.response.JeuResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.service.JeuService;

@RestController
@RequestMapping("/api/jeu")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class JeuRestController {

    private final JeuService srv;

    public JeuRestController(JeuService srv) {
        this.srv = srv;
    }

    @GetMapping
    public List<JeuResponse> allJeus() {
        return this.srv.getAll().stream().map(JeuResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public JeuResponse ficheJeu(@PathVariable int id) {
        return this.srv.getById(id).map(JeuResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse ajouterJeu(@Valid @RequestBody JeuRequest request) {
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public EntityUpdatedResponse modifierJeu(@PathVariable int id, @Valid @RequestBody JeuRequest request) {
        this.srv.update(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJeu(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }

}