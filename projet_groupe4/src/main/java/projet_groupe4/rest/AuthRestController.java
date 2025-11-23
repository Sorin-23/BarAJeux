package projet_groupe4.rest;

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

	public AuthRestController(SecurityService service) {
		this.service = service;
	}

	@PostMapping("/auth")
	public AuthResponse auth(@Valid @RequestBody AuthRequest request) {
		return this.service.auth(request);
	}
}
