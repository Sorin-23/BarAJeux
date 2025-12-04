package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dao.IDAOReservation;
import projet_groupe4.dao.IDAOTableJeu;
import projet_groupe4.dto.request.ReservationRequest;
import projet_groupe4.model.Client;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutReservation;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
	 @Mock
	    private IDAOReservation dao;
	    @Mock
	    private IDAOJeu jeuDao;
	    @Mock
	    private IDAOTableJeu tableJeuDao;
	    @Mock
	    private IDAOPersonne personneDao;

	    @InjectMocks
	    private ReservationService service;

	    @Test
	    void testGetAll() {
	        Reservation r = new Reservation();
	        when(dao.findAll()).thenReturn(List.of(r));
	        List<Reservation> result = service.getAll();
	        assertThat(result).hasSize(1);
	    }

	    @Test
	    void testGetById() {
	        Reservation r = new Reservation();
	        when(dao.findById(1)).thenReturn(Optional.of(r));
	        Optional<Reservation> result = service.getById(1);
	        assertThat(result).contains(r);
	    }

	    @Test
	    void testCreate() {
	        ReservationRequest req = new ReservationRequest();
	        req.setClientId(1);
	        req.setJeuId(1);
	        req.setTableJeuId(1);
	        req.setDatetimeDebut(LocalDateTime.now());
	        req.setDatetimeFin(LocalDateTime.now().plusHours(2));
	        req.setStatutReservation(StatutReservation.confirmée);

	        Client client = new Client();
	        Jeu jeu = new Jeu();
	        when(personneDao.findById(1)).thenReturn(Optional.of(client));
	        when(jeuDao.getReferenceById(1)).thenReturn(jeu);
	        when(tableJeuDao.getReferenceById(1)).thenReturn(null);
	        when(dao.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

	        Reservation result = service.create(req);
	        assertThat(result.getClient()).isEqualTo(client);
	    }

	    @Test
	    void testUpdate() {
	        Reservation r = new Reservation();
	        r.setId(1);
	        when(dao.findById(1)).thenReturn(Optional.of(r));
	        when(dao.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

	        ReservationRequest req = new ReservationRequest();
	        req.setClientId(1);
	        req.setJeuId(1);
	        req.setTableJeuId(1);
	        req.setDatetimeDebut(LocalDateTime.now());
	        req.setDatetimeFin(LocalDateTime.now().plusHours(2));
	        req.setStatutReservation(StatutReservation.confirmée);

	        Client client = new Client();
	        Jeu jeu = new Jeu();
	        when(personneDao.findById(1)).thenReturn(Optional.of(client));
	        when(jeuDao.getReferenceById(1)).thenReturn(jeu);
	        when(tableJeuDao.getReferenceById(1)).thenReturn(null);

	        Reservation result = service.update(1, req);
	        assertThat(result.getClient()).isEqualTo(client);
	    }

	    @Test
	    void testDeleteById() {
	        doNothing().when(dao).deleteById(1);
	        service.deleteById(1);
	    }

	    @Test
	    void testDelete() {
	        Reservation r = new Reservation();
	        doNothing().when(dao).delete(r);
	        service.delete(r);
	    }

}
