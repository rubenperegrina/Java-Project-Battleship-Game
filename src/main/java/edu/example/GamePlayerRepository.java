

//ManyToOne


package edu.example;
        import java.util.List;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by rubenperegrina on 14/12/16.
 */

@RepositoryRestResource
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {

        //Player findByPlayer(Player player);
}