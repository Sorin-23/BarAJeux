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
import projet_groupe4.dto.request.EmpruntRequest;
import projet_groupe4.dto.response.EmpruntResponse;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.service.EmpruntService;

@RestController
@RequestMapping("/api/emprunt")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class EmpruntRestController {
	private final EmpruntService srv;

	public EmpruntRestController(EmpruntService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List<EmpruntResponse> allEmprunts() {
		return this.srv.getAll().stream().map(EmpruntResponse::convert).toList();
	}

	@GetMapping("/{id}")
	public EmpruntResponse ficheEmprunt(@PathVariable Integer id) {
		return this.srv.getById(id).map(EmpruntResponse::convert).orElseThrow(IdNotFoundException::new);
	}

	@PostMapping
	@PreAuthorize("hasRole('EMPLOYE')")
	@ResponseStatus(HttpStatus.CREATED)
	public EntityCreatedResponse ajouterEmprunt(@Valid @RequestBody EmpruntRequest request) {
		return new EntityCreatedResponse(this.srv.create(request).getId());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	public EntityUpdatedResponse modifierEmprunt(@PathVariable Integer id, @Valid @RequestBody EmpruntRequest request) {
		this.srv.update(id, request);
		return new EntityUpdatedResponse(id, true);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmprunt(@PathVariable Integer id) {
		this.srv.deleteById(id);
	}

}
