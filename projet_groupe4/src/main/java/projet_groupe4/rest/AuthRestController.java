package projet_groupe4.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet_groupe4.config.JwtUtils;
import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.AuthRequest;
import projet_groupe4.dto.response.AuthResponse;
import projet_groupe4.model.Personne;
@RestController
@RequestMapping("/api")
public class AuthRestController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	IDAOPersonne daoPersonne;
	@PostMapping("/auth")
	public AuthResponse auth(@RequestBody AuthRequest request) {



		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

		// On demande à Spring Security si le user / password sont OK
		this.authenticationManager.authenticate(auth);

		Personne personne = daoPersonne.findByMail(request.getUsername()).orElseThrow();


		return new AuthResponse(JwtUtils.generate(auth), personne);
	}
}
