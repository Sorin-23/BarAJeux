package projet_groupe4.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import projet_groupe4.dto.request.AuthRequest;
import projet_groupe4.dto.response.AuthResponse;
import projet_groupe4.model.Personne;
import projet_groupe4.service.PersonneService;

@Service
public class SecurityService {
    private final static Logger log = LoggerFactory.getLogger(SecurityService.class);
    private final AuthenticationManager authenticationManager;
    private final PersonneService srv;

    public SecurityService(AuthenticationManager authenticationManager, PersonneService srv) {
        this.authenticationManager = authenticationManager;
        this.srv = srv;
    }

    public AuthResponse auth(AuthRequest authRequest) {
        try {
            log.debug("Trying to authenticate ...");

            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Tentative d'auth avec username : '{}'", authRequest.getUsername());
            this.srv.getByMail(authRequest.getUsername())
                    .ifPresentOrElse(
                            p -> log.info("Utilisateur trouvé : {}", p.getMail()),
                            () -> log.warn("Utilisateur introuvable : {}", authRequest.getUsername()));

            log.debug("Searching user by mail: {}", authRequest.getUsername());
            Personne personne = this.srv.getByMail(authRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable après authentification"));

            log.debug("Successfuly authenticated!");

            String token = JwtUtils.generate(authentication);
            return new AuthResponse(token, personne);
        }

        catch (BadCredentialsException ex) {
            log.error("Can't authenticate : bad credentials.");
        }

        catch (Exception ex) {
            log.error("Can't authenticate : unknown error ({}).", ex.getClass().getSimpleName());
        }

        return new AuthResponse("", null);
    }
}