package edu.example;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rubenperegrina on 10/1/17.
 */
@Entity
public class GameScore {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    private double score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_ID")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_ID")
    private Game game;

    public Date finishDate;

    public GameScore() {

    }

    public Date getFinishDate() {
        return finishDate;
    }


    /*public Game() { score = new Score(); }*/

    public double getScore() { return score; }

    public GameScore(Game game, Player player, double score, Date date) {
        this.player = player;
        this.game = game;
        this.score = score;
        this.finishDate = date;
    }

    public Game getGame() {
        return game;
    }
}