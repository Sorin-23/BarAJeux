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

import projet_groupe4.dao.IDAOBadge;
import projet_groupe4.dto.request.BadgeRequest;
import projet_groupe4.exception.IdNotFoundException;
import projet_groupe4.model.Badge;

@ExtendWith(MockitoExtension.class)
public class BadgeServiceTest {

	@Mock
    private IDAOBadge dao;

    @InjectMocks
    private BadgeService service;
    
    @Test
    void testGetAll() {
        Badge b1 = new Badge();
        Badge b2 = new Badge();
        when(dao.findAll()).thenReturn(List.of(b1, b2));

        List<Badge> result = service.getAll();

        assertThat(result).hasSize(2).containsExactly(b1, b2);
        verify(dao).findAll();
    }

    @Test
    void testGetByIdFound() {
        Badge badge = new Badge();
        when(dao.findById(1)).thenReturn(Optional.of(badge));

        Optional<Badge> result = service.getById(1);

        assertThat(result).isPresent().contains(badge);
        verify(dao).findById(1);
    }

    @Test
    void testCreate() {
        BadgeRequest request = new BadgeRequest();
        request.setNomBadge("Gold");
        request.setPointMin(100);
        request.setImgURL("url.png");

        when(dao.save(any(Badge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Badge result = service.create(request);

        assertThat(result.getNomBadge()).isEqualTo("Gold");
        assertThat(result.getPointMin()).isEqualTo(100);
        assertThat(result.getImgURL()).isEqualTo("url.png");
        verify(dao).save(any(Badge.class));
    }

    @Test
    void testUpdateFound() {
        Badge existing = new Badge();
        when(dao.findById(1)).thenReturn(Optional.of(existing));
        when(dao.save(any(Badge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BadgeRequest request = new BadgeRequest();
        request.setNomBadge("Silver");
        request.setPointMin(50);
        request.setImgURL("silver.png");

        Badge result = service.update(1, request);

        assertThat(result.getNomBadge()).isEqualTo("Silver");
        assertThat(result.getPointMin()).isEqualTo(50);
        assertThat(result.getImgURL()).isEqualTo("silver.png");
        verify(dao).save(existing);
    }

    @Test
    void testUpdateNotFound() {
        when(dao.findById(99)).thenReturn(Optional.empty());

        BadgeRequest request = new BadgeRequest();
        request.setNomBadge("Bronze");

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
        Badge badge = new Badge();
        badge.setId(1);
        service.delete(badge);
        verify(dao).delete(badge);
    }
}
