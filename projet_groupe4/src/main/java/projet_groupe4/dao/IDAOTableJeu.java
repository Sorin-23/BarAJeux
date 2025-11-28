package projet_groupe4.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import projet_groupe4.model.TableJeu;

public interface IDAOTableJeu extends JpaRepository<TableJeu,Integer> {

    @Query("SELECT t FROM TableJeu t LEFT JOIN FETCH t.reservations")
    List<TableJeu> findAllWithReservations();

}
