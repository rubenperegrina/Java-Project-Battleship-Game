package edu.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository player, GameRepository game, GamePlayerRepository gamePlayer, ShipRepository ship) {
		return (args) -> {

            Game g1 = new Game();
            Game g2 = new Game();
            Game g3 = new Game();

            g2.addSeconds(3600);
            g3.addSeconds(7200);

            game.save(g1);
            game.save(g2);
            game.save(g3);


            Player p1 = new Player("Jack Bauer", "jackbauer@gmail.com");
            Player p2 = new Player("Chloe O'Brian", "chloeobrian@gmail.com");
            Player p3 = new Player("Kim Bauer", "kimbauer@gmail.com");
            Player p4 = new Player("David Palmer", "davidpalmer@gmail.com");
            Player p5 = new Player("Michelle Dessler", "michelledessler@gmail.com");
            Player p6 = new Player("Jonny Walker", "jonnywalker@gmail.com");


            player.save(p1);
            player.save(p2);
            player.save(p3);
            player.save(p4);
            player.save(p5);
            player.save(p6);
            player.save(p6);

			//jshdjshd

            GamePlayer gp1 = new GamePlayer(p1, g1);
            GamePlayer gp2 = new GamePlayer(p5, g1);
            GamePlayer gp3 = new GamePlayer(p3, g2);
            GamePlayer gp4 = new GamePlayer(p4, g2);
            GamePlayer gp5 = new GamePlayer(p5, g3);
            GamePlayer gp6 = new GamePlayer(p6, g3);

            gamePlayer.save(gp1);
            gamePlayer.save(gp2);
            gamePlayer.save(gp3);
            gamePlayer.save(gp4);
            gamePlayer.save(gp5);
            gamePlayer.save(gp6);

            List<String> loc1 = Arrays.asList("A1", "A2", "A3");
            List<String> loc2 = Arrays.asList("B1", "B2", "B3");
            List<String> loc3 = Arrays.asList("C1", "C2", "C3");
            List<String> loc4 = Arrays.asList("D1", "D2", "D3");
            List<String> loc5 = Arrays.asList("E1", "E2", "E3");
            List<String> loc6 = Arrays.asList("F1", "F2", "F3");

            Ship s1 = new Ship("Cruiser", loc1);
            Ship s2 = new Ship("Pesado", loc2);
            Ship s3 = new Ship("Ligero", loc3);
            Ship s4 = new Ship("Patrullero", loc4);
            Ship s5 = new Ship("Fragata", loc5);
            Ship s6 = new Ship("Apache", loc6);


            gp1.addShip(s1);
            gp2.addShip(s2);
            gp3.addShip(s3);
            gp4.addShip(s4);
            gp5.addShip(s5);
            gp6.addShip(s6);


            ship.save(s1);
            ship.save(s2);
            ship.save(s3);
            ship.save(s4);
            ship.save(s5);
            ship.save(s6);
		};
	}
}
