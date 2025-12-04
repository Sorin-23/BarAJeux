package projet_groupe4.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projet_groupe4.model.Client;

public interface IDAOClient extends JpaRepository<Client, Integer> {
    
    Optional<Client> findByMail(String mail);
}