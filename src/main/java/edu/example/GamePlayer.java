package edu.example;
import javax.persistence.*;
import java.util.*;


//ManyToOne
//Field Game and Player

/**
 * Created by rubenperegrina on 13/12/16.
 */
@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_ID")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Game_ID")
    private Game game;

    @OneToMany (mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships;

    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        this.ships.add(ship);
    }

    public GamePlayer() {
    }

    private Date creationDate;

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public GamePlayer (Player player, Game game){
        this.player = player;
        this.game = game;
        this.creationDate = new Date();
        this.ships = new HashSet<>();
    }
    public Date getCretionDate() {
        return creationDate;
    }
    public void setCreationDate(Date date){
        this.creationDate = date;
    }

    /*public Map<String, Object> getDto() {
        Map<String, Object> myDto = new LinkedHashMap<>();

        myDto.put("id", this.id);
        myDto.put("player", this.player.getDto());
        return myDto;
    }*/

}