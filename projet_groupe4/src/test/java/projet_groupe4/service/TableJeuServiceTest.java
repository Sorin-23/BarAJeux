package projet_groupe4.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOTableJeu;

@ExtendWith(MockitoExtension.class)
public class TableJeuServiceTest {
	@Mock
    private IDAOTableJeu dao;

    @InjectMocks
    private TableJeuService service;

}
