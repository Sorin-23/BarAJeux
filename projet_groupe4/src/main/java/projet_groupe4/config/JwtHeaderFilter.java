package projet_groupe4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.model.Personne;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;

@Component
public class JwtHeaderFilter extends OncePerRequestFilter{
    @Autowired
    private IDAOPersonne daoPersonne;
    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null) {
            String token = header.substring(7); // On enlève "Bearer " pour garder que le jeton

            // On vérifie le jeton, et si tout est OK, on récupère l'utilisateur associé à ce jeton
            Optional<String> optUsername = JwtUtils.validateAndGetSubjet(token);

            if (optUsername.isPresent()) {
                Personne personne = this.daoPersonne.findByMail(optUsername.get()).orElseThrow();

                // On refabrique une liste de rôles pour l'utilisateur
                List<GrantedAuthority> autorities = new ArrayList<>();

                if (personne instanceof Client) {
                    autorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
                }

                else if (personne instanceof Employe) {
                    autorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

                    /*if (e.isAdmin()) {
                        autorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    }*/
                }

                // Créer, pour Spring Security, un nouvel User, avec le nom d'utilisateur, pas de mdp, et la liste des autorités
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(optUsername.get(), null, autorities);

                // Injecter notre nouvel authentication dans le contexte de Spring Security
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Important pour chainer sur le filtre suivant
        filterChain.doFilter(request, response);

    }

    }
