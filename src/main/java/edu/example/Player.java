package edu.example;
import javax.persistence.OneToMany;

//OneTOMany

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by rubenperegrina on 7/12/16.
 */

@Entity
public class Player {



    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER )
    public Set<GamePlayer> gameplayers;

    public String email;
    public String name;

    @OneToMany(mappedBy = "player" , fetch = FetchType.EAGER)
    public Set<GameScore> gamescores;


    public Player() { }

    public Player(String name, String email, String password) {

        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String emailName) {
        email = emailName;
    }

    public GameScore getGamescores(Game game) {
        return gamescores.stream().filter(gameScore -> gameScore.getGame().equals(game)).findFirst().orElse(null);
    }
}
