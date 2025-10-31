package barJeux.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import barJeux.model.Badge;

public interface IDAOBadge extends JpaRepository<Badge,Integer> {

}
