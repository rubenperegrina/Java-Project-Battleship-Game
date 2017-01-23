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

    @Autowired
    private ShipRepository repoS;

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


    //Game
    public Map<String, Object> getGameDto(Game game) {
        Map<String, Object> myGameDto = new LinkedHashMap<>();

        myGameDto.put("id", game.getId());
        myGameDto.put("creation", game.getCreationDate());
        myGameDto.put("Players", game.getGameplayers().stream().map(gp -> getGamePlayerDto(gp)).collect(toList()));
        return myGameDto;
    }

    //GamePlayer
    public Map<String, Object> getGamePlayerDto(GamePlayer gamePlayer) {
        Map<String, Object> myGamePlayerDto = new LinkedHashMap<>();

        myGamePlayerDto.put("gpid", gamePlayer.getId());
        myGamePlayerDto.put("id", gamePlayer.getPlayer().getId());
        myGamePlayerDto.put("name", gamePlayer.getPlayer().getName());


        if (gamePlayer.getGameScore() != null) {
            myGamePlayerDto.put("score", gamePlayer.getGameScore().getScore());
        }
        return myGamePlayerDto;
    }

////GAMEVIEW

    @RequestMapping("/game_view/{nn}")
    public Map<String, Object> getGame_View(@PathVariable Long nn, Authentication authentication) {

        String currentPlayer = repoGP.findOne(nn).getPlayer().getName();

        if (authentication.getName() != currentPlayer) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("error", HttpStatus.UNAUTHORIZED);
            return map;

        } else {
            Map<String, Object> myViewGame = new LinkedHashMap<>();

            myViewGame.put("id", nn);
            GamePlayer gameplayer = repoGP.findOne(nn);
            myViewGame.put("creation", gameplayer.getCretionDate());
            myViewGame.put("gamePlayers", gameplayer.getGame().getGameplayers().stream()

                    .map(v -> getViewGamePlayer(v)).collect(toList()));

            myViewGame.put("ships", gameplayer.ships.stream().map(s -> getShipDto(s)).collect(toList()));
            myViewGame.put("salvoes", gameplayer.getGame().getGameplayers().stream().map(sal -> getSalvoDto(sal.getSalvo())).collect(toList()));

            return myViewGame;
        }
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

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String username, String password) {
        if (username.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No name"), HttpStatus.FORBIDDEN);
        }
        List<Player> players = playerRepository.findByName(username);
        if (!players.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No such user"), HttpStatus.CONFLICT);
        }
        Player player = playerRepository.save(new Player(username, "ee@hhh.com", password));
        return new ResponseEntity<>(makeMap("name", player.getName()), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "No player"), HttpStatus.UNAUTHORIZED);
        }

        Player player = playerrepo.findByName(authentication.getName()).get(0);

        Game game = new Game();
        repoG.save(game);

        GamePlayer gameplayer = new GamePlayer(player, game);
        repoGP.save(gameplayer);

        return new ResponseEntity<>(makeMap("gpid", gameplayer.getId()), HttpStatus.CREATED);
    }


    @RequestMapping(path = "/game/{nn}/players", method = RequestMethod.POST)
    private ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long nn, Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "No player"), HttpStatus.UNAUTHORIZED);
        }

        Player player = playerrepo.findByName(authentication.getName()).get(0);

        Game game = repoG.findOne(nn);

        if (game == null) {
            return new ResponseEntity<>(makeMap("error", "No game"), HttpStatus.FORBIDDEN);
        }

        if(game.getGameplayers().size() == 2) {
            return new ResponseEntity<>(makeMap("error" ,"Game is full"), HttpStatus.FORBIDDEN);
        }else{
            GamePlayer gameplayer = new GamePlayer(player, game);
            repoGP.save(gameplayer);
            return new ResponseEntity<>(makeMap("gpid", gameplayer.getId()), HttpStatus.CREATED);
        }
    }


    @RequestMapping(path = "/games/players/{nn}/ships", method = RequestMethod.POST)
    private ResponseEntity<Map<String, Object>> CreatedShips(@PathVariable Long nn, List <Ship> ships, Authentication authentication) {

        if(authentication == null) {
            return new ResponseEntity<>(makeMap("error", "No current user logged"), HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = repoGP.findOne(nn);

        if(gamePlayer.getId() != nn) {
            return new ResponseEntity<>(makeMap("error", "No game player with the given ID"), HttpStatus.UNAUTHORIZED);
        }

        if(authentication.getName() != gamePlayer.getPlayer().getName()) {
            return new ResponseEntity<>(makeMap("error", "The current user is not the game player the ID"), HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer.getShips().isEmpty()) {
                ships.forEach(ship -> ship.setGamePlayer(gamePlayer));
                ships.forEach(ship -> repoS.save(ship));
            return new ResponseEntity<>(makeMap("sucess", "Ships created!"), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(makeMap("error", "Ships are full"), HttpStatus.FORBIDDEN);
        }
    }



    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}

