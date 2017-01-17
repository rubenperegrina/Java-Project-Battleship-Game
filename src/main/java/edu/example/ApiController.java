package edu.example;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * Created by rubenperegrina on 16/12/16.
 */

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    public GameRepository repoG;

    @Autowired
    private GamePlayerRepository repoGP;

    @Autowired
    private PlayerRepository playerrepo;

    public List<Game> getAll() {
        return repoG.findAll();
    }

    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {

        List<Player> players = playerrepo.findByName(authentication.getName());

        Map<String, Object> dtoMap = new LinkedHashMap<>();

        if (isGuest(authentication)) {
            dtoMap.put("player", "Guest");
        } else {
            dtoMap.put("player", players.stream().map(p -> getPlayerAuthenticated(p)).collect(Collectors.toList()));
        }


        dtoMap.put("games", repoG.findAll().stream().map(g -> getGameDto(g)).collect(Collectors.toList()));

        return dtoMap;

    }

    private Map<String, Object> getPlayerAuthenticated(Player player) {

        Map<String, Object> dtoMap = new LinkedHashMap<>();

        dtoMap.put("id", player.getId());
        dtoMap.put("name", player.getName());
        dtoMap.put("email", player.getEmail());

        return dtoMap;

    }


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }




    /*@RequestMapping("/games")
    public List<Object> getALL() {
        return repoG.findAll().stream().map(g -> getGameDto(g) ).collect(Collectors.toList());
    }*/


    //Game
    public Map<String, Object> getGameDto(Game game) {
        Map<String, Object> myGameDto = new LinkedHashMap<>();

        myGameDto.put("id", game.getId());
        myGameDto.put("creation", game.getCreationDate());
        myGameDto.put("gamePlayers", game.getGameplayers().stream().map(gp -> getGamePlayerDto(gp)).collect(toList()));
        return myGameDto;
    }

    //GamePlayer
    public Map<String, Object> getGamePlayerDto(GamePlayer gamePlayer) {
        Map<String, Object> myGamePlayerDto = new LinkedHashMap<>();

        myGamePlayerDto.put("id", gamePlayer.getPlayer().getId());
        myGamePlayerDto.put("player", gamePlayer.getPlayer().getName());
        myGamePlayerDto.put("email", gamePlayer.getPlayer().getEmail());


        if (gamePlayer.getGameScore() != null) {
            myGamePlayerDto.put("score", gamePlayer.getGameScore().getScore());
        }
        return myGamePlayerDto;
    }

////GAMEVIEW

    @RequestMapping("/game_view/{nn}")

    public Map<String, Object> getGame_View(@PathVariable Long nn) {
        Map<String, Object> myViewGame = new LinkedHashMap<>();

        myViewGame.put("id", nn);
        GamePlayer gameplayer = repoGP.findOne(nn);
        myViewGame.put("creation", gameplayer.getCretionDate());
        myViewGame.put("gamePlayers", gameplayer.getGame().getGameplayers().stream()

                .map(v -> getViewGamePlayer(v)).collect(toList()));

        myViewGame.put("ships", gameplayer.ships.stream().map(s -> getShipDto(s)).collect(toList()));
        myViewGame.put("salvoes", gameplayer.getGame().getGameplayers().stream().map(sal -> getSalvoDto(sal.getSalvo())).collect(toList()));

        /*myViewGame.put("salvoes", repoGP.findOne(nn).salvo.stream().map(sal -> getSalvoDto(sal)).collect(toList()));*/

        return myViewGame;
    }


    public Map<String, Object> getViewGamePlayer(GamePlayer gamePlayer) {
        Map<String, Object> myViewGamePlayer = new LinkedHashMap<>();

        myViewGamePlayer.put("id", gamePlayer.getId());

        myViewGamePlayer.put("player", getGamePlayerDto(gamePlayer));

        return myViewGamePlayer;
    }

    public Map<String, Object> getShipDto(Ship ship) {
        Map<String, Object> myShipDto = new LinkedHashMap<>();

        myShipDto.put("type", ship.getShip());
        myShipDto.put("location", ship.getLocations());
        return myShipDto;
    }

    private List<Map<String, Object>> getSalvoDto(Set<Salvo> salvoes) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Salvo salvo : salvoes) {
            Map<String, Object> salvoMap = new LinkedHashMap<>();
            long turn = salvo.getTurn();
            List<String> salvoLocations = salvo.getLocations();
            long id = salvo.getGamePlayer().getPlayer().getId();

            salvoMap.put("turn", turn);
            salvoMap.put("player", id);
            salvoMap.put("locations", salvoLocations);

            result.add(salvoMap);
        }
        return result;
    }


    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/api/login")
    public ResponseEntity<String> createUser(@RequestBody String name) {

        if (name.isEmpty()) {
            return new ResponseEntity<>("No name given", HttpStatus.FORBIDDEN);
        }

        List<Player> user = playerRepository.findByName(name);
        if (user != null) {
            return new ResponseEntity<>("Name already used", HttpStatus.CONFLICT);
        }

        playerRepository.save(new Player(name, "12", "12"));
        return new ResponseEntity<>("Named added", HttpStatus.CREATED);

    }
}



