package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOEmprunt;
import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.EmpruntRequest;
import projet_groupe4.dto.response.TopJeuResponse;
import projet_groupe4.model.Client;
import projet_groupe4.model.Emprunt;
import projet_groupe4.model.Jeu;

@ExtendWith(MockitoExtension.class)
public class EmpruntServiceTest {
	@Mock
    private IDAOEmprunt dao;

    @Mock
    private IDAOPersonne personneDao;

    @Mock
    private IDAOJeu jeuDao;

    @InjectMocks
    private EmpruntService service;

    @Test
    void testGetAll() {
        when(dao.findAll()).thenReturn(List.of(new Emprunt()));
        List<Emprunt> result = service.getAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testGetById() {
        Emprunt emprunt = new Emprunt();
        when(dao.findById(1)).thenReturn(Optional.of(emprunt));
        Optional<Emprunt> result = service.getById(1);
        assertThat(result).contains(emprunt);
    }

    @Test
    void testCreate() {
        EmpruntRequest request = new EmpruntRequest();
        request.setClientId(1);
        request.setJeuId(1);
        request.setDateEmprunt(LocalDate.now());
        request.setDateRetour(LocalDate.now().plusDays(1));

        Client client = new Client();
        when(personneDao.findById(1)).thenReturn(Optional.of(client));
        when(jeuDao.getReferenceById(1)).thenReturn(new Jeu());
        when(dao.save(any(Emprunt.class))).thenAnswer(i -> i.getArgument(0));
        when(personneDao.save(client)).thenAnswer(i -> i.getArgument(0));

        Emprunt result = service.create(request);
        assertThat(result.getClient()).isEqualTo(client);
    }

    @Test
    void testUpdate() {
        Emprunt existing = new Emprunt();
        when(dao.findById(1)).thenReturn(Optional.of(existing));
        EmpruntRequest request = new EmpruntRequest();
        request.setClientId(1);
        request.setJeuId(1);
        request.setDateEmprunt(LocalDate.now());
        request.setDateRetour(LocalDate.now().plusDays(1));

        Client client = new Client();
        when(personneDao.findById(1)).thenReturn(Optional.of(client));
        when(jeuDao.getReferenceById(1)).thenReturn(new Jeu());
        when(dao.save(any(Emprunt.class))).thenAnswer(i -> i.getArgument(0));
        when(personneDao.save(client)).thenAnswer(i -> i.getArgument(0));

        Emprunt result = service.update(1, request);
        assertThat(result.getClient()).isEqualTo(client);
    }

    @Test
    void testDeleteById() {
        service.deleteById(1);
        verify(dao).deleteById(1);
    }

    @Test
    void testDelete() {
        Emprunt e = new Emprunt();
        service.delete(e);
        verify(dao).delete(e);
    }

    @Test
    void testGetTop3Emprunts() {
    	 Object[] entry = new Object[]{new Jeu(), Long.valueOf(5)};

    	    // Crée une vraie List<Object[]> correctement
    	 	List<Object[]> results = Arrays.<Object[]>asList(entry);


    	    when(dao.findTopEmprunts()).thenReturn(results);

    	    List<TopJeuResponse> top = service.getTop3Emprunts();

    	    assertThat(top).hasSize(1);
    }

}
