package edu.example;
import javax.persistence.OneToMany;

//OneToMany

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

import static java.util.stream.Collectors.toList;

/**
 * Created by rubenperegrina on 7/12/16.*/


@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER  )
    private Set<GamePlayer> gameplayers;

    private Date creationDate;

    @OneToMany(mappedBy = "game" , fetch = FetchType.EAGER)
    public Set<GameScore> gamescores;


    public Game() {
        creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    //TODO remove when deploy this project to production
    public void addSeconds(long seconds) {
        creationDate = Date.from(creationDate.toInstant().plusSeconds(seconds));
    }

    public long getId() {
        return id;
    }

    public Set<GamePlayer> getGameplayers() {
        return gameplayers;
    }
}