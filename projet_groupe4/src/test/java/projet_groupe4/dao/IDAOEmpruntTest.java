package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.Client;
import projet_groupe4.model.Emprunt;
import projet_groupe4.model.Jeu;

@DataJpaTest
public class IDAOEmpruntTest {

	@Autowired
	private IDAOEmprunt daoEmprunt;
	@Autowired
	private IDAOClient daoClient;
	@Autowired
	private IDAOJeu daoJeu;
	
	@Test
    public void testFindTopEmprunts() {
        // Créer clients
		Client client1 = new Client();
	    client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	    daoClient.save(client1);
	    
	    Client client2 = new Client();
	    client2.setNom("Dutoit");
	    client2.setPrenom("Toto");
	    client2.setMail("totodutoit@test.com");
	    client2.setMdp("123456");
	    daoClient.save(client2);

        // Créer jeux
        Jeu jeu1 = new Jeu();
        jeu1.setNom("Catan");
        daoJeu.save(jeu1);

        Jeu jeu2 = new Jeu();
        jeu2.setNom("Carcassonne");
        daoJeu.save(jeu2);

        // Créer emprunts
        daoEmprunt.save(new Emprunt(client1, jeu1));
        daoEmprunt.save(new Emprunt(client1, jeu2));
        daoEmprunt.save(new Emprunt(client2, jeu1));

        // Vérifier top emprunts
        List<Object[]> top = daoEmprunt.findTopEmprunts();
        assertThat(top).isNotEmpty();

        // Le jeu avec le plus d’emprunts devrait être "Catan"
        Object[] premier = top.get(0);
        Jeu topJeu = (Jeu) premier[0];
        Long nbEmprunts = (Long) premier[1];

        assertThat(topJeu.getNom()).isEqualTo("Catan");
        assertThat(nbEmprunts).isEqualTo(2L);
    }
}
