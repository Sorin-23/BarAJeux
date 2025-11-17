package projet_groupe4.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import projet_groupe4.model.TableJeu;

public interface IDAOTableJeu extends JpaRepository<TableJeu,Integer> {

}
