package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.Avis;
import projet_groupe4.model.Client;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutReservation;
import projet_groupe4.model.TableJeu;

@DataJpaTest
public class IDAOAvisTest {

	@Autowired 
	private IDAOAvis daoAvis;
	@Autowired 
	private IDAOClient daoClient;
	@Autowired 
	private IDAOJeu daoJeu;
	@Autowired 
	private IDAOReservation daoReservation;
	@Autowired
    private IDAOTableJeu daoTableJeu;
	
	@Test
	public void testFindByReservationId() {
		 // Créer un client
        Client client = new Client();
        client.setNom("Dupont");
        client.setPrenom("Jean");
        client.setMail("jean.dupont@test.com");
	    client.setMdp("test123");
        daoClient.save(client);

        // Créer un jeu
        Jeu jeu = new Jeu();
        jeu.setNom("Catan");
		daoJeu.save(jeu);
		
		TableJeu tableJeu = new TableJeu();
        tableJeu.setNomTable("Table 1");
        tableJeu.setCapacite(8);
        tableJeu.setImgUrl("img");
        daoTableJeu.save(tableJeu);
        // Créer une réservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setJeu(jeu);
        reservation.setTableJeu(tableJeu);
        reservation.setDatetimeDebut(LocalDateTime.now().plusDays(1));
        reservation.setDatetimeFin(LocalDateTime.now().plusDays(2));
        reservation.setStatutReservation(StatutReservation.confirmée);
        daoReservation.save(reservation);

        // Créer un avis
        Avis avis = new Avis();
        avis.setNote(4);
        avis.setTitre("Super");
        avis.setCommentaire("Très bon jeu !");
        avis.setReservation(reservation);

        // Sauvegarder l'avis
        daoAvis.save(avis);

        // Test de findByReservationId
        Optional<Avis> result = daoAvis.findByReservationId(reservation.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getNote()).isEqualTo(4);
        assertThat(result.get().getTitre()).isEqualTo("Super");
        assertThat(result.get().getCommentaire()).isEqualTo("Très bon jeu !");
	}
}
