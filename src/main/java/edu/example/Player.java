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


    public Player() { }

    public Player(String name, String email) {

        this.email = email;
        this.name = name;
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

    /*public Map<String, Object> getDto() {
        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("id", this.id);
        dto.put("email", this.email);
        dto.put("name", this.name);
        return dto;
    }*/
}
