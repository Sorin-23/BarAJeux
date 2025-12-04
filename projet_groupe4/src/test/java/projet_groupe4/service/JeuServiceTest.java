package projet_groupe4.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import projet_groupe4.dao.IDAOJeu;
import projet_groupe4.dto.request.JeuRequest;
import projet_groupe4.model.CategorieJeu;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.TypeJeu;

@ExtendWith(MockitoExtension.class)
public class JeuServiceTest {
	
	@Mock
    private IDAOJeu dao;

    @InjectMocks
    private JeuService service;
    @Test
    void testGetAll() {
        Jeu j = new Jeu();
        when(dao.findAll()).thenReturn(List.of(j));
        List<Jeu> result = service.getAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testGetById() {
        Jeu j = new Jeu();
        when(dao.findById(1)).thenReturn(Optional.of(j));
        Optional<Jeu> result = service.getById(1);
        assertThat(result).isPresent().contains(j);
    }

    @Test
    void testCreate() {
        JeuRequest req = new JeuRequest();
        req.setNom("Catan");
        when(dao.save(any(Jeu.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Jeu result = service.create(req);
        assertThat(result.getNom()).isEqualTo("Catan");
    }

    @Test
    void testUpdate() {
        Jeu j = new Jeu();
        j.setNom("Old");
        when(dao.findById(1)).thenReturn(Optional.of(j));
        JeuRequest req = new JeuRequest();
        req.setNom("New");
        when(dao.save(j)).thenAnswer(invocation -> invocation.getArgument(0));

        Jeu result = service.update(1, req);
        assertThat(result.getNom()).isEqualTo("New");
    }
    
    @Test
    void testGetByNomContaining() {
    	Jeu jeu1 = new Jeu("Catan",
	            Set.of(TypeJeu.PLATEAU, TypeJeu.COOPERATIF),
	            10,
	            3,
	            4,
	            90,
	            5,
	            4.5,
	            Set.of(CategorieJeu.FAMILIAL, CategorieJeu.STRATEGIE),
	            "",
	            false);
		Jeu jeu2 = new Jeu(
	            "Loup-Garou",
	            Set.of(TypeJeu.CARTES, TypeJeu.NARRATIF),
	            10,
	            6,
	            18,
	            30,
	            3,
	            4.0,
	            Set.of(CategorieJeu.AMBIANCE),
	            "",
	            false
	        );
		

    	
    	
        // Cas nom non vide
        when(dao.findByNomContaining("Catan")).thenReturn(List.of(jeu1));
        List<Jeu> result1 = service.getByNomContaining("Catan");
        assertThat(result1).hasSize(1);

        // Cas nom vide
        when(dao.findAll()).thenReturn(List.of(jeu1, jeu2));
        List<Jeu> result2 = service.getByNomContaining("");
        assertThat(result2).hasSize(2);

        // Cas nom null
        List<Jeu> result3 = service.getByNomContaining(null);
        assertThat(result3).hasSize(2);
    }
    
    

}
