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
import projet_groupe4.dto.request.BadgeRequest;
import projet_groupe4.dto.response.BadgeResponse;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.service.BadgeService;

@RestController
@RequestMapping("/api/badge")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class BadgeRestController {
    private final BadgeService srv;

    public BadgeRestController(BadgeService srv) {
        this.srv = srv;
    }

    @GetMapping
    public List<BadgeResponse> allBadges() {
        return this.srv.getAll().stream().map(BadgeResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public BadgeResponse ficheBadge(@PathVariable int id) {
        return this.srv.getById(id).map(BadgeResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse ajouterBadge(@Valid @RequestBody BadgeRequest request) {
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public EntityUpdatedResponse modifierBadge(@PathVariable int id, @Valid @RequestBody BadgeRequest request) {
        this.srv.update(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBadge(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }
}