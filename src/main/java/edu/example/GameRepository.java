package edu.example;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by rubenperegrina on 7/12/16.*/
@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByCreationDate(String creationDate);
}
