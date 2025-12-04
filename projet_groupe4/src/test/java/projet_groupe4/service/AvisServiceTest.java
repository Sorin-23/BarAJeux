package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOAvis;
import projet_groupe4.dao.IDAOReservation;
import projet_groupe4.dto.request.AvisRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Avis;
import projet_groupe4.model.Reservation;

@ExtendWith(MockitoExtension.class)
public class AvisServiceTest {

	@Mock
    private IDAOAvis dao;

    @Mock
    private IDAOReservation reservationDao;

    @InjectMocks
    private AvisService service;

    @Test
    void testGetAll() {
        Avis avis1 = new Avis();
        Avis avis2 = new Avis();
        when(dao.findAll()).thenReturn(List.of(avis1, avis2));

        List<Avis> result = service.getAll();

        assertThat(result).hasSize(2).containsExactly(avis1, avis2);
        verify(dao).findAll();
    }

    @Test
    void testGetByIdFound() {
        Avis avis = new Avis();
        when(dao.findById(1)).thenReturn(Optional.of(avis));

        Optional<Avis> result = service.getById(1);

        assertThat(result).isPresent().contains(avis);
        verify(dao).findById(1);
    }

    @Test
    void testGetByReservationId() {
        Avis avis = new Avis();
        when(dao.findByReservationId(5)).thenReturn(Optional.of(avis));

        Optional<Avis> result = service.getByReservationId(5);

        assertThat(result).isPresent().contains(avis);
        verify(dao).findByReservationId(5);
    }

    @Test
    void testCreate() {
        AvisRequest request = new AvisRequest();
        request.setNote(4);
        request.setTitre("Super");
        request.setCommentaire("Top");
        request.setReservationId(10);

        Reservation res = new Reservation();
        when(reservationDao.getReferenceById(10)).thenReturn(res);
        when(dao.save(any(Avis.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Avis result = service.create(request);

        assertThat(result.getNote()).isEqualTo(4);
        assertThat(result.getTitre()).isEqualTo("Super");
        assertThat(result.getCommentaire()).isEqualTo("Top");
        assertThat(result.getReservation()).isEqualTo(res);
        verify(dao).save(any(Avis.class));
        verify(reservationDao).getReferenceById(10);
    }

    @Test
    void testUpdateFound() {
        Avis existing = new Avis();
        when(dao.findById(1)).thenReturn(Optional.of(existing));
        Reservation res = new Reservation();
        when(reservationDao.getReferenceById(10)).thenReturn(res);
        when(dao.save(any(Avis.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AvisRequest request = new AvisRequest();
        request.setNote(5);
        request.setTitre("Update");
        request.setCommentaire("Changed");
        request.setReservationId(10);

        Avis result = service.update(1, request);

        assertThat(result.getNote()).isEqualTo(5);
        assertThat(result.getTitre()).isEqualTo("Update");
        assertThat(result.getCommentaire()).isEqualTo("Changed");
        assertThat(result.getReservation()).isEqualTo(res);
        verify(dao).save(existing);
    }

    @Test
    void testUpdateNotFound() {
        when(dao.findById(99)).thenReturn(Optional.empty());

        AvisRequest request = new AvisRequest();
        request.setReservationId(1);

        try {
            service.update(99, request);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IdNotFoundException.class);
        }
    }

    @Test
    void testDeleteById() {
        service.deleteById(1);
        verify(dao).deleteById(1);
    }

    @Test
    void testDelete() {
        Avis avis = new Avis();
        avis.setId(1);
        service.delete(avis);
        verify(dao).delete(avis);
    }
}
