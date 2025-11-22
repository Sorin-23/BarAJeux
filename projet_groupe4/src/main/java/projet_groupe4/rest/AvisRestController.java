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
import projet_groupe4.dto.request.AvisRequest;
import projet_groupe4.dto.response.AvisResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Avis;
import projet_groupe4.service.AvisService;

@RestController
@RequestMapping("/api/avis")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class AvisRestController {

	@Autowired
	private AvisService srv;

	@GetMapping
	public List<AvisResponse> allAviss() {
		return this.srv.getAll().stream().map(AvisResponse::convert).toList();
	}

	@GetMapping("/{id}")
	public AvisResponse ficheAvis(@PathVariable int id) {
		return this.srv.getById(id).map(AvisResponse::convert).orElseThrow(IdNotFoundException::new);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('EMPLOYE')")
	public AvisResponse ajouterAvis(@Valid @RequestBody AvisRequest request) {
		Avis avis = new Avis();
		BeanUtils.copyProperties(request, avis);

		this.srv.create(avis);

		return AvisResponse.convert(avis);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('EMPLOYE')")
	public AvisResponse modifierAvis(@PathVariable int id, @Valid @RequestBody AvisRequest request) {
		Avis avis = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
		BeanUtils.copyProperties(request, avis);

		this.srv.update(avis);

		return AvisResponse.convert(avis);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	public void deleteAvis(@PathVariable Integer id) {
		this.srv.deleteById(id);
	}

}