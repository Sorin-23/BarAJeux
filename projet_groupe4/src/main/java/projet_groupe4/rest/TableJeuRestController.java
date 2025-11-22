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
import projet_groupe4.dto.request.TableRequest;
import projet_groupe4.dto.response.TableResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.TableJeu;
import projet_groupe4.service.TableJeuService;

@RestController
@RequestMapping("/api/tableJeu")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class TableJeuRestController {

	@Autowired
	private TableJeuService srv;

	@GetMapping
	public List<TableResponse> allTableJeus() {
		return this.srv.getAll().stream().map(TableResponse::convert).toList();
	}

	@GetMapping("/{id}")
	public TableResponse ficheTableJeu(@PathVariable int id) {
		return this.srv.getById(id).map(TableResponse::convert).orElseThrow(IdNotFoundException::new);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('EMPLOYE')")
	public TableResponse ajouterTableJeu(@Valid @RequestBody TableRequest request) {
		TableJeu tableJeu = new TableJeu();
		BeanUtils.copyProperties(request, tableJeu);

		this.srv.create(tableJeu);

		return TableResponse.convert(tableJeu);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('EMPLOYE')")
	public TableResponse modifierTableJeu(@PathVariable int id, @Valid @RequestBody TableRequest request) {
		TableJeu tableJeu = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
		BeanUtils.copyProperties(request, tableJeu);

		this.srv.update(tableJeu);

		return TableResponse.convert(tableJeu);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	public void deleteTableJeu(@PathVariable Integer id) {
		this.srv.deleteById(id);
	}

}