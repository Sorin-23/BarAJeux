package projet_groupe4.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import projet_groupe4.model.Emprunt;

public interface IDAOEmprunt extends JpaRepository<Emprunt,Integer> {

}
