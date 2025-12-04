package projet_groupe4.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import projet_groupe4.config.SecurityService;
import projet_groupe4.dto.request.AuthRequest;
import projet_groupe4.dto.response.AuthResponse;

@RestController
@RequestMapping("/api")
public class AuthRestController {

	private final SecurityService service;
	private final static Logger log = LoggerFactory.getLogger(AuthRestController.class);

	public AuthRestController(SecurityService service) {
		this.service = service;
	}

	@PostMapping("/auth")
	public AuthResponse auth(@Valid @RequestBody AuthRequest request) {
		log.debug("Demande d'authentification !");
		return this.service.auth(request);
	}
}
