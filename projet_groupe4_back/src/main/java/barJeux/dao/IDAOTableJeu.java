package barJeux.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import barJeux.model.TableJeu;

public interface IDAOTableJeu extends JpaRepository<TableJeu,Integer> {

}
