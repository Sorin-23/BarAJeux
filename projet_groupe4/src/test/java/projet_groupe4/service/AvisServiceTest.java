package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOAvis;
import projet_groupe4.model.Avis;

@ExtendWith(MockitoExtension.class)
public class AvisServiceTest {

	@Mock
    private IDAOAvis dao;

    @InjectMocks
    private AvisService service;
    
    
    @Test
    void testGetByReservationId() {
        // given
        int reservationId = 1;
        Avis avis = new Avis();
        avis.setId(100);
        
        when(dao.findByReservationId(reservationId)).thenReturn(Optional.of(avis));

        // when
        Optional<Avis> result = service.getByReservationId(reservationId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(100);
    }
}
