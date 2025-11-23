package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.SubscribeClientRequest;
import projet_groupe4.exception.EmailAlreadyUsedException;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Personne;

@ExtendWith(MockitoExtension.class)
public class PersonneServiceTest {

    @Mock
    private IDAOPersonne dao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonneService service;

    @Test
    void testGetAllClients() {
        Client client1 = new Client();
        client1.setNom("Dupont");
        client1.setPrenom("Jean");
        client1.setMail("jean.dupont@test.com");
        client1.setMdp("test123");
        Client client2 = new Client();
        client2.setNom("Dutoit");
        client2.setPrenom("Toto");
        client2.setMail("totodutoit@test.com");
        client2.setMdp("123456");
        Employe employe1 = new Employe();
        employe1.setNom("Dupont22");
        employe1.setPrenom("Jean22");
        employe1.setMail("jean22.dupont22@test.com");
        employe1.setMdp("test12322");

        when(dao.findAllClient()).thenReturn(List.of(client1, client2));

        List<Client> result = service.getAllClients();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(client1, client2);
    }

    @Test
    void testGetAllEmployes() {
        Client client1 = new Client();
        client1.setNom("Dupont");
        client1.setPrenom("Jean");
        client1.setMail("jean.dupont@test.com");
        client1.setMdp("test123");
        Client client2 = new Client();
        client2.setNom("Dutoit");
        client2.setPrenom("Toto");
        client2.setMail("totodutoit@test.com");
        client2.setMdp("123456");
        Employe employe1 = new Employe();
        employe1.setNom("Dupont22");
        employe1.setPrenom("Jean22");
        employe1.setMail("jean22.dupont22@test.com");
        employe1.setMdp("test12322");

        when(dao.findAllEmploye()).thenReturn(List.of(employe1));

        List<Employe> result = service.getAllEmployes();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(employe1);
    }

    @Test
    void testGetClientById() {

        Client c = new Client();
        c.setId(1);
        when(dao.findById(1)).thenReturn(Optional.of(c));

        Optional<Client> result = service.getClientById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(c);

    }

    @Test
    void testGetEmployeById() {
        Employe e = new Employe();
        e.setId(2);
        when(dao.findById(2)).thenReturn(Optional.of(e));

        Optional<Employe> result = service.getEmployeById(2);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(e);
    }

    @Test
    void testGetByMail() {
        Personne p = new Client();
        when(dao.findByMail("mail@test.com")).thenReturn(Optional.of(p));

        Personne result = service.getByMail("mail@test.com").orElseThrow(EmailAlreadyUsedException::new);

        assertThat(result).isEqualTo(p);
    }

    @Test
    void testCreatePersonne() {
        Personne p = new Client();
        p.setMdp("motDePasse");
        when(passwordEncoder.encode("motDePasse")).thenReturn("encoded");
        when(dao.save(any(Personne.class))).thenAnswer(i -> i.getArguments()[0]);
        Personne result = service.create(p);
        assertThat(result.getMdp()).isEqualTo("encoded");
    }

    @Test
    void testUpdatePersonne() {
        int id = 1;

        // Création de la "request" concrète
        SubscribeClientRequest request = new SubscribeClientRequest();
        request.setMdp("motDePasse2");

        // Client existant
        Client existingClient = new Client();
        existingClient.setId(id);
        existingClient.setMdp("oldPassword");

        // Mocks
        when(service.getClientById(id)).thenReturn(Optional.of(existingClient));
        when(passwordEncoder.encode("motDePasse2")).thenReturn("encoded");
        // Ici on peut mocker saveClient si c'est une méthode interne, sinon on laisse
        // la vraie exécution
        when(service.update(existingClient.getId(), request)).thenAnswer(invocation -> invocation.getArgument(0));

        // Appel du service
        Personne result = service.update(id, request);

        // Vérifications
        assertThat(result.getMdp()).isEqualTo("encoded");
    }

    @Test
    void testUpdateInfosConnect() {
        Personne p = new Client();
        p.setId(1);
        when(dao.findById(1)).thenReturn(Optional.of(p));
        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(dao.save(any(Personne.class))).thenAnswer(i -> i.getArguments()[0]);

        Personne result = service.updateInfosConnect(1, "new@mail.com", "pass");

        assertThat(result.getMail()).isEqualTo("new@mail.com");
        assertThat(result.getMdp()).isEqualTo("encoded");
    }

    @Test
    void testGetByNomContaining() {
        Personne p1 = new Client();
        Personne p2 = new Client();
        when(dao.findAll()).thenReturn(List.of(p1, p2));

        List<Personne> resultNull = service.getByNomContaining(null);
        List<Personne> resultEmpty = service.getByNomContaining(" ");

        assertThat(resultNull).hasSize(2);
        assertThat(resultEmpty).hasSize(2);
    }

    @Test
    void testGetByPrenomContaining() {
        Personne p1 = new Client();
        Personne p2 = new Client();
        when(dao.findAll()).thenReturn(List.of(p1, p2));

        List<Personne> resultNull = service.getByPrenomContaining(null);
        List<Personne> resultEmpty = service.getByPrenomContaining("");

        assertThat(resultNull).hasSize(2);
        assertThat(resultEmpty).hasSize(2);
    }

    @Test
    void testFindByIdWithEmprunts() {
        Client c = new Client();
        when(dao.findByIdWithEmprunts(1)).thenReturn(Optional.of(c));

        Optional<Client> result = service.getByIdWithEmprunts(1);

        assertThat(result).contains(c);
    }

    @Test
    void testFindByIdWithReservations() {
        Client c = new Client();
        when(dao.findByIdWithReservations(1)).thenReturn(Optional.of(c));

        Optional<Client> result = service.getByIdWithReservations(1);

        assertThat(result).contains(c);
    }

}
