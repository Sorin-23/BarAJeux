package projet_groupe4.config;

import java.util.function.Function;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projet_groupe4.model.Personne;
import projet_groupe4.service.PersonneService;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final PersonneService srv;

    public JpaUserDetailsService(PersonneService srv) {
        this.srv = srv;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Function<Personne, String> roleMapper = (person) -> {
            return switch (person) {
                case Client c -> "CLIENT";
                case Employe e -> "EMPLOYE";
                default -> "NONE";
            };
        };

        return this.srv.getByMail(username)
                .map(person -> User
                        .withUsername(username)
                        .password(person.getMdp())
                        .roles(roleMapper.apply(person))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
