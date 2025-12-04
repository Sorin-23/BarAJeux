package projet_groupe4.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import projet_groupe4.model.Employe;

public interface IDAOEmploye extends JpaRepository<Employe, Integer> {
    
    Optional<Employe> findByMail(String mail);
}