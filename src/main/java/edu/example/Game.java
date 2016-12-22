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
    public Set<GamePlayer> gameplayers;

    private Date creationDate;



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

    /*public Map<String, Object> getDto() {
        Map<String, Object> myDto = new LinkedHashMap<>();

        myDto.put("id", this.id);
        myDto.put("creation", this.creationDate);
        myDto.put("gamePlayers", this.gameplayers.stream().map(gp -> gp.getDto()).collect(toList()));
        return myDto;
    }*/
}