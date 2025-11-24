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
import projet_groupe4.dto.request.AvisRequest;
import projet_groupe4.dto.response.AvisResponse;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.service.AvisService;

@RestController
@RequestMapping("/api/avis")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class AvisRestController {
	private final AvisService srv;

	public AvisRestController(AvisService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List<AvisResponse> allAviss() {
		return this.srv.getAll().stream().map(AvisResponse::convert).toList();
	}

	@GetMapping("/{id}")
	public AvisResponse ficheAvis(@PathVariable int id) {
		return this.srv.getById(id).map(AvisResponse::convert).orElseThrow(IdNotFoundException::new);
	}

	@PostMapping
	@PreAuthorize("hasRole('EMPLOYE')")
	@ResponseStatus(HttpStatus.CREATED)
	public EntityCreatedResponse ajouterAvis(@Valid @RequestBody AvisRequest request) {
		return new EntityCreatedResponse(this.srv.create(request).getId());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	public EntityUpdatedResponse modifierAvis(@PathVariable int id, @Valid @RequestBody AvisRequest request) {
		this.srv.update(id, request);

		return new EntityUpdatedResponse(id, true);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAvis(@PathVariable Integer id) {
		this.srv.deleteById(id);
	}

}