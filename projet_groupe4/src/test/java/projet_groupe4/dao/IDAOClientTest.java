package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.Client;

@DataJpaTest
public class IDAOClientTest {
	@Autowired
	private IDAOClient daoClient;

	@Test
	public void testFindByMail() {
		// Créer et sauvegarder un client
		Client client = new Client();
		client.setNom("Dupont");
		client.setPrenom("Jean");
		client.setMail("jean.dupont@test.com");
		client.setMdp("test123");
		daoClient.save(client);

		// Chercher le client par mail
		Optional<Client> result = daoClient.findByMail("jean.dupont@test.com");

		// Vérifier le résultat
		assertThat(result).isPresent();
		assertThat(result.get().getPrenom()).isEqualTo("Jean");
		assertThat(result.get().getNom()).isEqualTo("Dupont");
	}

}
