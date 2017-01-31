package edu.example;
import javax.persistence.*;
import java.util.*;


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
    public Set<Ship> ships;

    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        this.ships.add(ship);
    }

    @OneToMany (mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    public Set<Salvo> salvo;

    public void addSalvo(Salvo salvo) {
        salvo.setGamePlayer(this);
        this.salvo.add(salvo);
    }

    public Set<Salvo> getSalvo() {
        return salvo;
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
        this.salvo = new HashSet<>();
    }
    public Date getCretionDate() {
        return creationDate;
    }
    public void setCreationDate(Date date){
        this.creationDate = date;
    }

    public GameScore getGameScore() {
    return player.getGamescores(game);
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public long getLastTurn() {
        if(!this.getSalvo().isEmpty()) {
            return this.getSalvo()
                    .stream()
                    .map(s-> s.getTurn())
                    .max((x, y) -> Long.compare(x, y)).get();
        }else {
            return 0;
        }
    }
}