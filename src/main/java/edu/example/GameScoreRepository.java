package edu.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by rubenperegrina on 10/1/17.
 */

@RepositoryRestResource
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
}
