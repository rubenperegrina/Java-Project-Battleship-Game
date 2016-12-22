package edu.example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by rubenperegrina on 19/12/16.*/


@RepositoryRestResource
public interface ShipRepository extends JpaRepository<Ship, Long> {
    //List<Ship> findByShipType(String shipType);
}

