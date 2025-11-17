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
import projet_groupe4.dto.request.JeuRequest;
import projet_groupe4.dto.response.JeuResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Jeu;
import projet_groupe4.service.JeuService;
import projet_groupe4.view.Views;

@RestController
@RequestMapping("/api/jeu")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class JeuRestController {

    @Autowired
    private JeuService srv;

    @GetMapping
    @JsonView(Views.Jeu.class)
    public List<JeuResponse> allJeus() {
        return this.srv.getAll().stream().map(JeuResponse::convert).toList();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Jeu.class)
    public JeuResponse ficheJeu(@PathVariable int id) {
        return this.srv.getById(id).map(JeuResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @JsonView(Views.Jeu.class)
    @PreAuthorize("hasAnyRole('EMPLOYE')")
    public JeuResponse ajouterJeu(@Valid @RequestBody JeuRequest request) {
        Jeu jeu = new Jeu();
        BeanUtils.copyProperties(request, jeu);

        this.srv.create(jeu);

        return JeuResponse.convert(jeu);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Jeu.class)
    @PreAuthorize("hasAnyRole('EMPLOYE')")
    public JeuResponse modifierJeu(@PathVariable int id, @Valid @RequestBody JeuRequest request) {
        Jeu jeu = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(request, jeu);

        this.srv.update(jeu);

        return JeuResponse.convert(jeu);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public void deleteJeu(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }

}