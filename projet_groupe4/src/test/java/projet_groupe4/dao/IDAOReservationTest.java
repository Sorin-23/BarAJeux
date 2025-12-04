package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutReservation;
import projet_groupe4.model.TableJeu;

@DataJpaTest
public class IDAOReservationTest {

	@Autowired
	private IDAOReservation daoReservation;
	@Autowired
	private IDAOClient daoClient;
	@Autowired
	private IDAOJeu daoJeu;
	@Autowired
	private IDAOTableJeu daoTableJeu;
	@Autowired
	private IDAOEmploye daoEmploye;


	@Test
	public void testFindByGameMasterId() {
		Client client = new Client();
		client.setNom("Martin");
		client.setPrenom("Alice");
		client.setMail("alice.martin@test.com");
		client.setMdp("secret");
		daoClient.save(client);

		Jeu jeu = new Jeu();
		jeu.setNom("Monopoly");
		daoJeu.save(jeu);

		TableJeu table = new TableJeu("Table 2", 6);
		daoTableJeu.save(table);

		Employe employe2 = new Employe();
	    employe2.setNom("Dutoit");
	    employe2.setPrenom("Toto");
	    employe2.setMail("totodutoit@test.com");
	    employe2.setMdp("123456");
	    daoEmploye.save(employe2);

		Reservation reservation = new Reservation();
		reservation.setClient(client);
		reservation.setJeu(jeu);
		reservation.setTableJeu(table);
		reservation.setGameMaster(employe2);
		reservation.setDatetimeDebut(LocalDateTime.now().plusDays(1));
		reservation.setDatetimeFin(LocalDateTime.now().plusDays(2));
		reservation.setNbJoueur(4);
		reservation.setStatutReservation(StatutReservation.confirmée);
		daoReservation.save(reservation);

		List<Reservation> result = daoReservation.findByGameMasterId(employe2.getId());
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getJeu().getNom()).isEqualTo("Monopoly");
	}

	@Test
	public void testFindTopReservations() {
		Client client = new Client();
		client.setNom("Durand");
		client.setPrenom("Luc");
		client.setMail("luc.durand@test.com");
		client.setMdp("secret");
		daoClient.save(client);

		Jeu jeu1 = new Jeu();
		jeu1.setNom("Catan");
		daoJeu.save(jeu1);

		Jeu jeu2 = new Jeu();
		jeu2.setNom("Carcassonne");
		daoJeu.save(jeu2);

		TableJeu table = new TableJeu("Table 3", 4);
		daoTableJeu.save(table);

		// Plusieurs réservations pour tester le top
		Reservation r1 = new Reservation();
		r1.setClient(client);
		r1.setJeu(jeu1);
		r1.setTableJeu(table);
		r1.setDatetimeDebut(LocalDateTime.now());
		r1.setDatetimeFin(LocalDateTime.now().plusDays(1));
		r1.setStatutReservation(StatutReservation.confirmée);
		daoReservation.save(r1);

		Reservation r2 = new Reservation();
		r2.setClient(client);
		r2.setJeu(jeu1);
		r2.setTableJeu(table);
		r2.setDatetimeDebut(LocalDateTime.now());
		r2.setDatetimeFin(LocalDateTime.now().plusDays(1));
		r2.setStatutReservation(StatutReservation.confirmée);
		daoReservation.save(r2);

		Reservation r3 = new Reservation();
		r3.setClient(client);
		r3.setJeu(jeu2);
		r3.setTableJeu(table);
		r3.setDatetimeDebut(LocalDateTime.now());
		r3.setDatetimeFin(LocalDateTime.now().plusDays(1));
		r3.setStatutReservation(StatutReservation.confirmée);
		daoReservation.save(r3);

		List<Object[]> top = daoReservation.findTopReservations();
		assertThat(top).isNotEmpty();
		// Le premier doit être Catan car 2 réservations
		assertThat(top.get(0)[0]).isEqualTo(jeu1);
	}
}
