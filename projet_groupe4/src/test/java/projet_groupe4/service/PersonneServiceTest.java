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
	    List<Client> result = service.getAllClients();
	    assertThat(result).hasSize(2);
    }
    
    @Test
    void testGetAllEmployes() {
    	
    }
    
    @Test
    void testGetClientById() {
    	
    }
    
    @Test
    void testGetEmployeById() {
    	
    }
    
    @Test
    void testGetByMail() {
    	
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
    	Personne p = new Client();
    	p.setId(1);
        p.setMdp("motDePasse2");
        when(passwordEncoder.encode("motDePasse2")).thenReturn("encoded");
        when(dao.save(any(Personne.class))).thenAnswer(i -> i.getArguments()[0]);
        Personne result = service.update(p);
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
    	
    }
    
    @Test
    void testGetByPrenomContaining() {
    	
    }
    
    @Test
    void testFindByIdWithEmprunts() {
    	
    }
    @Test
    void testFindByIdWithReservations() {
    	
    }

}
