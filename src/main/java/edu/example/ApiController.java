package edu.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    public List<Game> getAll() {
        return repoG.findAll();
    }


    @RequestMapping("/games")
    public List<Object> getALL() {
        return repoG.findAll().stream().map(g -> getGameDto(g) ).collect(Collectors.toList());
    }
    /*//Player
    public Map<String, Object> getDto1() {
        Map<String, Object> MyDto = new LinkedHashMap<>();

        MyDto.put("id", Player.getId());
        MyDto.put("email", Player.getEmail());
        MyDto.put("name", Player.getName());
        return MyDto;
    }*/

    //Game
    public Map<String, Object> getGameDto(Game game) {
        Map<String, Object> myGameDto = new LinkedHashMap<>();

        myGameDto.put("id", game.getId());
        myGameDto.put("creation", game.getCreationDate());
        myGameDto.put("gamePlayers", game.gameplayers.stream().map(gp -> getGamePlayerDto(gp)).collect(toList()));
        return myGameDto;
    }

    //GamePlayer
    public Map<String, Object> getGamePlayerDto(GamePlayer gamePlayer) {
        Map<String, Object> myGamePlayerDto = new LinkedHashMap<>();

        myGamePlayerDto.put("id", gamePlayer.getPlayer().getId());
        myGamePlayerDto.put("player", gamePlayer.getPlayer().getName());
        myGamePlayerDto.put("email", gamePlayer.getPlayer().getEmail());
        return myGamePlayerDto;
    }

//GAMEVIEW

    @RequestMapping("/game_view/{nn}")
    public Map <String, Object> getGame_View(@PathVariable Long nn) {
        Map<String, Object> myViewGame = new LinkedHashMap<>();

        myViewGame.put("id", nn);
        myViewGame.put("creation", repoGP.findOne(nn).getCretionDate());
        myViewGame.put("gamePlayers", repoGP.findAll().stream()
                .filter(gp -> gp.getGame().getId() == nn)
                .map(v -> getViewGamePlayer(v)).collect(toList()));

        return myViewGame;
    }



            public Map<String, Object> getViewGamePlayer (GamePlayer gamePlayer){
                Map<String, Object> myViewGamePlayer = new LinkedHashMap<>();

                myViewGamePlayer.put("id", gamePlayer.getId());

                myViewGamePlayer.put("player", getGamePlayerDto(gamePlayer));
                return myViewGamePlayer;
            }
}



