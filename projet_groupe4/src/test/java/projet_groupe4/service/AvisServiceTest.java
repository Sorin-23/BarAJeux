package projet_groupe4.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOAvis;

@ExtendWith(MockitoExtension.class)
public class AvisServiceTest {

	@Mock
    private IDAOAvis dao;

    @InjectMocks
    private AvisService service;
}
