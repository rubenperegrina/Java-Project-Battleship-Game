package edu.example;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rubenperegrina on 19/12/16.*/


@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String ship;

    @ElementCollection
    private List<String> locations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameplayer_id")
    private GamePlayer gamePlayer;

    public Ship() {
    }

    public Ship(String ship, List locations) {
        this.locations = locations;
        this.ship = ship;
    }

    public List<String> getLocations() {
        return locations;
    }

    public String getShip() {
        return ship;
    }

    public void setShip() {
        this.ship = ship;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public long getId() {
        return id;
    }
}
