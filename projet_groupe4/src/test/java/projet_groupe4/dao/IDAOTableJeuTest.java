package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.Client;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.TableJeu;

@DataJpaTest
public class IDAOTableJeuTest {

    @Autowired
    private IDAOTableJeu daoTableJeu;

    @Autowired
    private IDAOJeu daoJeu;

    @Autowired
    private IDAOPersonne daoPersonne;

    @Test
    void testFindAllWithReservations(){
        TableJeu table1 = new TableJeu();
        table1.setCapacite(4);
        table1.setImgUrl("imageTable");
        table1.setNomTable("Table Test");

        TableJeu table2 = new TableJeu();
        table2.setCapacite(5);
        table2.setImgUrl("imageTable2");
        table2.setNomTable("Table Test 2");

        Reservation res1 = new Reservation();
        Reservation res2 = new Reservation();
        Reservation res3 = new Reservation();

        Client client1 = new Client();
        client1.setNom("Dupont");
	    client1.setPrenom("Jean");
	    client1.setMail("jean.dupont@test.com");
	    client1.setMdp("test123");
	    daoPersonne.save(client1);

        res1.setClient(client1);
        res2.setClient(client1);
        res3.setClient(client1);

        Jeu jeu1 = new Jeu();
        jeu1.setNom("Jeu 1");
        Jeu jeu2 = new Jeu();
        jeu2.setNom("Jeu 2");

        res1.setJeu(jeu2);
        res2.setJeu(jeu1);
        res2.setJeu(jeu1);

        res1.setTableJeu(table1);
        res2.setTableJeu(table1);
        res3.setTableJeu(table2);

        table1.getReservations().add(res1);
        table1.getReservations().add(res2);

        

        table2.getReservations().add(res3);
        
        

        

        daoJeu.save(jeu1);
        daoJeu.save(jeu2);

        daoTableJeu.save(table1);
        daoTableJeu.save(table2);

        



        List<TableJeu> found = daoTableJeu.findAllWithReservations();

        assertThat(found).isNotNull();
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getReservations()).hasSize(2);
        assertThat(found.get(1).getReservations()).hasSize(1);
    }

    

}
