package edu.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public List<Game> getAll() {
        return repoG.findAll();
    }


    @RequestMapping("/games")
    public List<Object> getALL() {
        return repoG.findAll().stream().map(g -> getGameDto(g) ).collect(Collectors.toList());
    }

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

        if(gamePlayer.getGameScore() != null) {
            myGamePlayerDto.put("score", gamePlayer.getGameScore().getScore());
        }
        return myGamePlayerDto;
    }

////GAMEVIEW

    @RequestMapping("/game_view/{nn}")

    public Map <String, Object> getGame_View(@PathVariable Long nn) {
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



            public Map<String, Object> getViewGamePlayer (GamePlayer gamePlayer){
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
        /*mySalvoDto.put("turn", salvo.getTurn());
        mySalvoDto.put("player", salvo.gamePlayer.getPlayer().getId());
        mySalvoDto.put("location", salvo.getLocations());

        mySalvoDto.put("salvoes", repoGP.findOne(nn).salvo.stream().map(salgp -> getSalDto(salvo)).collect(toList()));*/

        return result;
    }

    /*public Map<String, Object> getSalDto(Salvo salvo) {
        Map<String, Object> mySalDto = new LinkedHashMap<>();

        mySalDto.put("player", salvo.gamePlayer.getPlayer().getId());
        mySalDto.put("location", salvo.getLocations());

        return mySalDto;
    }*/
}



