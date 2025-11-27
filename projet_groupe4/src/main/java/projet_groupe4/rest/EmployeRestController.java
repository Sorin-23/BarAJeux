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

    public EmployeRestController(PersonneService srv) {
        this.srv = srv;
    }

    @GetMapping
    public List<EmployeResponse> allEmployes() {
        System.out.println("Appel de allEmployes");
        return this.srv.getAllEmployes().stream().map(EmployeResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public EmployeResponse ficheEmploye(@PathVariable Integer id) {
        return this.srv.getEmployeById(id).map(EmployeResponse::convert).orElseThrow(IdNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse ajouterEmploye(@Valid @RequestBody SubscribeEmployeRequest request) {
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public EntityUpdatedResponse modifierEmploye(@PathVariable Integer id,
            @Valid @RequestBody SubscribeEmployeRequest request) {

        this.srv.update(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }
    
    @GetMapping("/username/{email}")
    public EmployeResponse getByUsername(@PathVariable String email) {
        Personne p = srv.getByMail(email)
                        .orElseThrow(IdNotFoundException::new);
        if (!(p instanceof Employe emp)) {
            throw new IdNotFoundException();
        }
        return EmployeResponse.convert(emp);
    }


}