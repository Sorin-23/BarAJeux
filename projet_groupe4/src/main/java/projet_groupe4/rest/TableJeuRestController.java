package projet_groupe4.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import projet_groupe4.dto.request.TableRequest;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.dto.response.EntityUpdatedResponse;
import projet_groupe4.dto.response.TableResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.service.TableJeuService;

@RestController
@RequestMapping("/api/tableJeu")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class TableJeuRestController {
	private final TableJeuService srv;
	private final static Logger log = LoggerFactory.getLogger(TableJeuRestController.class);

	public TableJeuRestController(TableJeuService srv) {
		this.srv = srv;
	}

	@GetMapping
	public List<TableResponse> allTableJeus() {
		log.debug("Liste des tables de jeu");
		return this.srv.getAll().stream().map(TableResponse::convert).toList();
	}

	@GetMapping("/{id}")
	public TableResponse ficheTableJeu(@PathVariable int id) {
		log.debug("Fiche table id {}", id);
		return this.srv.getById(id).map(TableResponse::convert).orElseThrow(IdNotFoundException::new);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('EMPLOYE')")
	@ResponseStatus(HttpStatus.CREATED)
	public EntityCreatedResponse ajouterTableJeu(@Valid @RequestBody TableRequest request) {
		log.debug("Création de la table {}", request.getNomTable());
		return new EntityCreatedResponse(this.srv.create(request).getId());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('EMPLOYE')")
	public EntityUpdatedResponse modifierTableJeu(@PathVariable int id, @Valid @RequestBody TableRequest request) {
		log.debug("Modifcation de la table {}", id);
		this.srv.update(id, request);

		return new EntityUpdatedResponse(id, true);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTableJeu(@PathVariable Integer id) {
		log.debug("Suppression de la table id {}", id);
		this.srv.deleteById(id);
	}

}