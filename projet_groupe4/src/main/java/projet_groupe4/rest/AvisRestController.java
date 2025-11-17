package projet_groupe4.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import projet_groupe4.dao.IDAOAvis;

@RestController
public class AvisRestController {
	@Autowired
	private IDAOAvis dao;
	
	@GetMapping("/api/avis")
	
	
	

}
