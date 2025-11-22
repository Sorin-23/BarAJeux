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
import projet_groupe4.dto.request.SubscribeEmployeRequest;
import projet_groupe4.dto.response.EmployeResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Employe;
import projet_groupe4.service.PersonneService;

@RestController
@RequestMapping("/api/employe")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class EmployeRestController {

    @Autowired
    private PersonneService srv;

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
    public EmployeResponse ajouterEmploye(@Valid @RequestBody SubscribeEmployeRequest request) {
        Employe employe = new Employe();
        BeanUtils.copyProperties(request, employe);

        this.srv.create(employe);

        return EmployeResponse.convert(employe);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public EmployeResponse modifierEmploye(@PathVariable Integer id,
            @Valid @RequestBody SubscribeEmployeRequest request) {
        Employe employe = this.srv.getEmployeById(id).orElseThrow(IdNotFoundException::new);
        BeanUtils.copyProperties(request, employe);

        this.srv.update(employe);

        return EmployeResponse.convert(employe);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYE')")
    public void deleteEmploye(@PathVariable Integer id) {
        this.srv.deleteById(id);
    }

}