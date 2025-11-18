package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Emprunt;
import projet_groupe4.model.Personne;
import projet_groupe4.model.Reservation;

@DataJpaTest
public class IDAOPersonneTest {

	@Autowired
	private IDAOPersonne daoPersonne;
	
	@Test
	void testFindAllClient() {
		Client client1 = new Client();
	    client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	    daoPersonne.save(client1);
	    
	    Client client2 = new Client();
	    client2.setNom("Dutoit");
	    client2.setPrenom("Toto");
	    client2.setMail("totodutoit@test.com");
	    client2.setMdp("123456");
	    daoPersonne.save(client2);
	    
	    List<Client> found = daoPersonne.findAllClient();
	    
	    assertThat(found).isNotNull();
	    assertThat(found).hasSize(2);
	}
	
	@Test
	void testFindAllEmploye() {
		Employe employe1 = new Employe();
	    employe1.setNom("Dupont");
	    employe1.setPrenom("Jean");
	    employe1.setMail("jean.dupont@test.com");
	    employe1.setMdp("test123");
	    daoPersonne.save(employe1);
	    
	    Employe employe2 = new Employe();
	    employe2.setNom("Dutoit");
	    employe2.setPrenom("Toto");
	    employe2.setMail("totodutoit@test.com");
	    employe2.setMdp("123456");
	    daoPersonne.save(employe2);
	    
	    List<Employe> found = daoPersonne.findAllEmploye();
	    
	    assertThat(found).isNotNull();
	    assertThat(found).hasSize(2);
	}
	
	@Test 
	void testFindByIdWithEmprunts() {
		Client client1 = new Client();
	    client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	   

        Emprunt emprunt = new Emprunt();
        client1.setEmprunts(List.of(emprunt));

        daoPersonne.save(client1);

        Optional<Client> found = daoPersonne.findByIdWithEmprunts(client1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEmprunts()).isNotEmpty();
	}
	
	@Test 
	void testFindByIdWithReservations() {
		Client client1 = new Client();
	    client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	   

	    Reservation res = new Reservation();
	    client1.setReservations(List.of(res));

        daoPersonne.save(client1);

        Optional<Client> found = daoPersonne.findByIdWithReservations(client1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getReservations()).isNotEmpty();

	}
	
	@Test
	void testFindByNomContaining() {
		Client client1 = new Client();
	    client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	    daoPersonne.save(client1);
	    Employe employe2 = new Employe();
	    employe2.setNom("Dutoit");
	    employe2.setPrenom("Toto");
	    employe2.setMail("totodutoit@test.com");
	    employe2.setMdp("123456");
	    daoPersonne.save(employe2);
	    
	    List<Personne> found = daoPersonne.findByNomContaining("Dupont");
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getNom()).isEqualTo("Dupont");
	    
	}
	@Test
	void testFindByPrenomContaining() {
		Client client1 = new Client();
	    client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	    daoPersonne.save(client1);
	    Employe employe2 = new Employe();
	    employe2.setNom("Dutoit");
	    employe2.setPrenom("Toto");
	    employe2.setMail("totodutoit@test.com");
	    employe2.setMdp("123456");
	    daoPersonne.save(employe2);
	    
	    List<Personne> found = daoPersonne.findByPrenomContaining("Toto");
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getPrenom()).isEqualTo("Toto");
	    
	    
	}
	
	@Test
	void testFindByMail() {
		
		Client client1 = new Client();
	    client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	    daoPersonne.save(client1);
	    
	    Employe employe2 = new Employe();
	    employe2.setNom("Dutoit");
	    employe2.setPrenom("Toto");
	    employe2.setMail("totodutoit@test.com");
	    employe2.setMdp("123456");
	    daoPersonne.save(employe2);
	    
	    Optional<Personne> found = daoPersonne.findByMail("jean.dupont@test.com");
	    assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("Dupont");
        assertThat(found.get().getMail()).isEqualTo("jean.dupont@test.com");
		
	}
	
	
}
