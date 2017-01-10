package edu.example;
import javax.persistence.*;
import java.util.*;

/**
 * Created by rubenperegrina on 5/1/17.
 */

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long turn;

    public long getId() {
        return id;
    }

    @ElementCollection
    private List<String> locations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer")
    public GamePlayer gamePlayer;


    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Salvo() {

    }

    public Salvo(long turn, List locations) {
        this.turn = turn;
        this.locations = locations;
    }

    public long getTurn() {
        return turn;
    }

    public List<String> getLocations() {
        return locations;
    }
}