package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.Employe;
@DataJpaTest
public class IDAOEmployeTest {
	@Autowired
	private IDAOEmploye daoEmploye;

	@Test
	public void testFindByMail() {
		// Créer et sauvegarder un employe
		Employe employe = new Employe();
		employe.setNom("Dupont");
		employe.setPrenom("Jean");
		employe.setMail("jean.dupont@test.com");
		employe.setMdp("test123");
		daoEmploye.save(employe);

		// Chercher le employe par mail
		Optional<Employe> result = daoEmploye.findByMail("jean.dupont@test.com");

		// Vérifier le résultat
		assertThat(result).isPresent();
		assertThat(result.get().getPrenom()).isEqualTo("Jean");
		assertThat(result.get().getNom()).isEqualTo("Dupont");
	}
}
