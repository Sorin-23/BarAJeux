package barJeux.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import barJeux.model.Avis;

public interface IDAOAvis extends JpaRepository<Avis,Integer> {

}
