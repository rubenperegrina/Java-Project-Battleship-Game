package edu.example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by rubenperegrina on 5/1/17.*/


@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo, Long> {

}
