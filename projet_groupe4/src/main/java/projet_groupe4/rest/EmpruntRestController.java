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
import projet_groupe4.dto.request.EmpruntRequest;
import projet_groupe4.dto.response.EmpruntResponse;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Emprunt;
import projet_groupe4.service.EmpruntService;

@RestController
@RequestMapping("/api/emprunt")
@PreAuthorize("hasAnyRole('EMPLOYE', 'CLIENT')")
public class EmpruntRestController {

	@Autowired
	private EmpruntService srv;
	
	@GetMapping
	public List<EmpruntResponse> allEmprunts(){
		return this.srv.getAll().stream().map(EmpruntResponse::convert).toList();
	}
	
	@GetMapping("/{id}")
	public EmpruntResponse ficheEmprunt(@PathVariable Integer id) {
		return this.srv.getById(id).map(EmpruntResponse::convert).orElseThrow(IdNotFoundException::new);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('EMPLOYE')")
	public EmpruntResponse ajouterEmprunt(@Valid @RequestBody EmpruntRequest request)
	{
		Emprunt emprunt = new Emprunt();
	    BeanUtils.copyProperties(request, emprunt);

	    this.srv.create(emprunt);

	    return EmpruntResponse.convert(emprunt);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	public EmpruntResponse modifierEmprunt(@PathVariable Integer id,@Valid @RequestBody EmpruntRequest request)
	{
		Emprunt emprunt = this.srv.getById(id).orElseThrow(IdNotFoundException::new);
	    BeanUtils.copyProperties(request, emprunt);

	    this.srv.update(emprunt);

	    return EmpruntResponse.convert(emprunt);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	public void deleteEmprunt(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
	}
	
}
