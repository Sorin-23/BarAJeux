package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOTableJeu;
import projet_groupe4.dto.request.TableRequest;
import projet_groupe4.model.TableJeu;

@ExtendWith(MockitoExtension.class)
public class TableJeuServiceTest {
    @Mock
    private IDAOTableJeu dao;

    @InjectMocks
    private TableJeuService service;

    @Test
    void testGetAll() {
        TableJeu t = new TableJeu();
        when(dao.findAllWithReservations()).thenReturn(List.of(t));
        List<TableJeu> result = service.getAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testGetById() {
        TableJeu t = new TableJeu();
        when(dao.findById(1)).thenReturn(Optional.of(t));
        Optional<TableJeu> result = service.getById(1);
        assertThat(result).contains(t);
    }

    @Test
    void testCreate() {
        TableRequest req = new TableRequest();
        req.setNomTable("Table A");
        req.setCapacite(4);
        req.setImgUrl("img.png");

        when(dao.save(any(TableJeu.class))).thenAnswer(i -> i.getArgument(0));
        TableJeu result = service.create(req);

        assertThat(result.getNomTable()).isEqualTo("Table A");
        assertThat(result.getCapacite()).isEqualTo(4);
        assertThat(result.getImgUrl()).isEqualTo("img.png");
    }

    @Test
    void testUpdate() {
        TableJeu t = new TableJeu();
        t.setNomTable("Ancienne Table");
        when(dao.findById(1)).thenReturn(Optional.of(t));
        when(dao.save(any(TableJeu.class))).thenAnswer(i -> i.getArgument(0));

        TableRequest req = new TableRequest();
        req.setNomTable("Nouvelle Table");
        req.setCapacite(6);
        req.setImgUrl("img2.png");

        TableJeu result = service.update(1, req);
        assertThat(result.getNomTable()).isEqualTo("Nouvelle Table");
        assertThat(result.getCapacite()).isEqualTo(6);
        assertThat(result.getImgUrl()).isEqualTo("img2.png");
    }

    @Test
    void testDeleteById() {
        doNothing().when(dao).deleteById(1);
        service.deleteById(1);
    }

    @Test
    void testDelete() {
        TableJeu t = new TableJeu();
        doNothing().when(dao).delete(t);
        service.delete(t);
    }

}
