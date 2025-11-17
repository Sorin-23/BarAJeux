package projet_groupe4.rest;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.dao.IDAOUtilisateur;
import fr.formation.dto.request.AuthUserRequest;
import fr.formation.dto.request.SubscribeUserRequest;
import fr.formation.dto.response.UtilisateurProjection2Response;
import fr.formation.dto.response.UtilisateurProjectionResponse;
import fr.formation.dto.response.UtilisateurResponse;
import fr.formation.model.Utilisateur;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurRestController {
    @Autowired
    private IDAOUtilisateur dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager am;

    @GetMapping
    public List<UtilisateurResponse> findAll() {
        // List<Utilisateur> users = this.dao.findAll();

        // System.out.println(users.size());

        // List<Utilisateur> users2 = users.stream()
        //     .filter(user -> user.getId() == 1)
        //     .toList()
        // ;

        // System.out.println(users2.size());
        // System.out.println(users.size());

        return this.dao.findAll().stream()
            .map(user -> UtilisateurResponse.convert(user))
            // .map(UtilisateurResponse::convert)
            .toList()
        ;
    }

    @GetMapping("projected-by")
    public List<UtilisateurProjectionResponse> findAllProjectedBy() {
        return this.dao.findAllProjectedBy(UtilisateurProjectionResponse.class);
        // return this.dao.findAllProjectedBy();
    }

    @GetMapping("projected-by-2")
    public List<UtilisateurProjection2Response> findAllProjectedBy2() {
        return this.dao.findAllProjectedBy(UtilisateurProjection2Response.class);
    }

    @PostMapping
    public int subscribe(@RequestBody SubscribeUserRequest request) {
        Utilisateur utilisateur = new Utilisateur();
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());

        BeanUtils.copyProperties(request, utilisateur);

        // utilisateur.setNom(request.getNom());
        // utilisateur.setUsername(request.getUsername());
        utilisateur.setPassword(encodedPassword);

        this.dao.save(utilisateur);

        return utilisateur.getId();
    }

    @PostMapping("/connexion")
    public String connexion(@RequestBody AuthUserRequest request) {
        Date now = new Date();
        String key = "6E5A7234753778214125442A472D4B6150645367556B58703273357638792F42";
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // On demande à Spring Security si le user / password sont OK
        this.am.authenticate(auth);

        // Si la connexion est OK, on génère un jeton JWT
        return Jwts.builder()
            .subject(request.getUsername()) // Souvent, c'est le username ici
            .issuedAt(now)
            .expiration(new Date(now.getTime() + 300_000)) // Durée de validité = 5 mins
            .signWith(secretKey)
            .compact() // Le jeton JWT sous forme de String
        ;
    }
}
