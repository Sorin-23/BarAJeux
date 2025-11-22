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
import projet_groupe4.dto.request.BadgeRequest;
import projet_groupe4.dto.response.BadgeResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Badge;
import projet_groupe4.service.BadgeService;

@RestController
@RequestMapping("/api/badge")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class BadgeRestController {

    @Autowired
    private BadgeService srv;

    @GetMapping
    public List<BadgeResponse> allBadges() {
        return this.srv.getAll().stream().map(BadgeResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public BadgeResponse ficheBadge(@PathVariable int id) {
        return this.srv.getById(id).map(BadgeResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYE')")
    public BadgeResponse ajouterBadge(@Valid @RequestBody BadgeRequest request) {
        Badge badge = new Badge();
        BeanUtils.copyProperties(request, badge);

        this.srv.create(badge);

        return BadgeResponse.convert(badge);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYE')")
    public BadgeResponse modifierBadge(@PathVariable int id, @Valid @RequestBody BadgeRequest request) {
        Badge badge = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(request, badge);

        this.srv.update(badge);

        return BadgeResponse.convert(badge);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public void deleteBadge(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }
}