package projet_groupe4.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import projet_groupe4.model.CategorieJeu;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.TypeJeu;

@DataJpaTest
public class IDAOJeuTest {
	
	@Autowired 
	private IDAOJeu daoJeu;
	
	@Test
	void testFindByNomContaining() {
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
		
		daoJeu.save(jeu1);
		daoJeu.save(jeu2);
		
		List<Jeu> result = daoJeu.findByNomContaining("Catan");
		assertThat(result).isNotEmpty();
		assertThat(result.get(0).getNom()).isEqualTo("Catan");
		
	}

}
