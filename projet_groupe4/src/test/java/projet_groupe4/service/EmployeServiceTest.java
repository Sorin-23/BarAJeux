package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOEmploye;
import projet_groupe4.model.Employe;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @Mock
    private IDAOEmploye daoEmploye;

    @InjectMocks
    private EmployeService service;

    @Test
    void testGetEmployeByMail() {
        // given
        String mail = "test@mail.com";
        Employe employe = new Employe();
        employe.setId(1);
        employe.setMail(mail);
        
        when(daoEmploye.findByMail(mail)).thenReturn(Optional.of(employe));

        // when
        Employe result = service.getEmployeByMail(mail);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getMail()).isEqualTo(mail);
    }
    @Test
    void testUpdateMyProfile() {
        Employe existing = new Employe();
        existing.setNom("Old");
        existing.setPrenom("Old");
        existing.setMdp("oldPass");

        Employe updates = new Employe();
        updates.setNom("New");
        updates.setPrenom("New");
        updates.setMdp("newPass");

        when(daoEmploye.findByMail("test@mail.com")).thenReturn(Optional.of(existing));
        when(daoEmploye.save(existing)).thenAnswer(i -> i.getArgument(0));

        Employe result = service.updateMyProfile("test@mail.com", updates);

        assertThat(result.getNom()).isEqualTo("New");
        assertThat(result.getPrenom()).isEqualTo("New");
        assertThat(result.getMdp()).isEqualTo("newPass");
    }

}