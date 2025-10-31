package barJeux.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import barJeux.model.Emprunt;

public interface IDAOEmprunt extends JpaRepository<Emprunt,Integer> {

}
