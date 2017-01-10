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
	public CommandLineRunner initData(PlayerRepository player, GameRepository game, GamePlayerRepository gamePlayer, ShipRepository ship, SalvoRepository Salvo) {
		return (args) -> {

            Game g1 = new Game();
            Game g2 = new Game();
            Game g3 = new Game();
            Game g4 = new Game();
            Game g5 = new Game();
            Game g6 = new Game();
            Game g7 = new Game();
            Game g8 = new Game();


            g2.addSeconds(3600);
            g3.addSeconds(7200);





            Player p1 = new Player("Jack Bauer", "jackbauer@gmail.com");
            Player p2 = new Player("Chloe O'Brian", "chloeobrian@gmail.com");
            Player p3 = new Player("Kim Bauer", "kimbauer@gmail.com");
            Player p4 = new Player("David Palmer", "davidpalmer@gmail.com");
            Player p5 = new Player("Michelle Dessler", "michelledessler@gmail.com");
            Player p6 = new Player("Jonny Walker", "jonnywalker@gmail.com");
            Player p7 = new Player("Jose Mota", "josemota@gmail.com");
            Player p8 = new Player("Leo Vince", "leovince@gmail.com");


            player.save(p1);
            player.save(p2);
            player.save(p3);
            player.save(p4);
            player.save(p5);
            player.save(p6);
            player.save(p7);
            player.save(p8);



            GamePlayer gp1 = new GamePlayer(p1, g1);
            GamePlayer gp2 = new GamePlayer(p2, g1);

            GamePlayer gp3 = new GamePlayer(p3, g2);
            GamePlayer gp4 = new GamePlayer(p2, g2);

            GamePlayer gp5 = new GamePlayer(p4, g3);
            GamePlayer gp6 = new GamePlayer(p3, g3);

            GamePlayer gp7 = new GamePlayer(p5, g4);
            GamePlayer gp8 = new GamePlayer(p6, g4);

            GamePlayer gp9 = new GamePlayer(p5, g5);
            GamePlayer gp10 = new GamePlayer(p8, g5);

            GamePlayer gp11 = new GamePlayer(p5, g6);

            GamePlayer gp12 = new GamePlayer(p3, g7);

            GamePlayer gp13 = new GamePlayer(p7, g8);
            GamePlayer gp14 = new GamePlayer(p3, g8);





            List<String> loc1 = Arrays.asList("H2", "H3", "H4");
            List<String> loc2 = Arrays.asList("E1", "F1", "G1");
            List<String> loc3 = Arrays.asList("B4", "B5");
            List<String> loc4 = Arrays.asList("B5", "C5", "D5");
            List<String> loc5 = Arrays.asList("F1", "F2");
            List<String> loc6 = Arrays.asList("B5", "C5", "D5");
            List<String> loc7 = Arrays.asList("C6", "C7");
            List<String> loc8 = Arrays.asList("A2", "A3", "A4");
            List<String> loc9 = Arrays.asList("G6", "H6");


            Salvo sal1 = new Salvo(1, loc4);
            Salvo sal2 = new Salvo(2, loc1);
            Salvo sal3 = new Salvo(1, loc5);
            Salvo sal4 = new Salvo(2, loc9);
            Salvo sal5 = new Salvo(1, loc5);
            Salvo sal6 = new Salvo(2, loc6);
            Salvo sal7 = new Salvo(1, loc7);
            Salvo sal8 = new Salvo(2, loc8);
            Salvo sal9 = new Salvo(1, loc9);
            Salvo sal10 = new Salvo(3, loc1);
            Salvo sal11 = new Salvo(1, loc1);
            Salvo sal12 = new Salvo(2, loc2);
            Salvo sal13 = new Salvo(1, loc3);
            Salvo sal14 = new Salvo(2, loc4);
            Salvo sal15 = new Salvo(1, loc5);
            Salvo sal16 = new Salvo(2, loc6);
            Salvo sal17 = new Salvo(1, loc7);
            Salvo sal18 = new Salvo(2, loc8);
            Salvo sal19 = new Salvo(1, loc9);
            Salvo sal20 = new Salvo(3, loc1);




            Ship s1 = new Ship("Destroyer", loc1);
            Ship s2 = new Ship("Submarine", loc2);
            Ship s3 = new Ship("Patrol Boat", loc3);
            Ship s4 = new Ship("Destroyer", loc4);
            Ship s5 = new Ship("Patrol Boat", loc5);
            Ship s6 = new Ship("Destroyer", loc6);
            Ship s7 = new Ship("Patrol Board", loc7);
            Ship s8 = new Ship("Submarine", loc8);
            Ship s9 = new Ship("Patrol Boat", loc7);
            Ship s10 = new Ship("Destroyer", loc6);
            Ship s11 = new Ship("Patrol Boat", loc7);
            Ship s12 = new Ship("Submarine", loc8);
            Ship s13 = new Ship("Patrol Boat", loc9);
            Ship s14 = new Ship("Destroyer", loc6);
            Ship s15 = new Ship("Patrol Boat", loc7);
            Ship s16 = new Ship("Submarine", loc8);
            Ship s17 = new Ship("Patrol Boat", loc7);
            Ship s18 = new Ship("Destroyer", loc6);
            Ship s19 = new Ship("Patrol Boat", loc7);
            Ship s20 = new Ship("Submarine", loc8);
            Ship s21 = new Ship("Patrol Boat", loc9);
            Ship s22 = new Ship("Destroyer", loc6);
            Ship s23 = new Ship("Patrol Boat", loc7);
            Ship s24 = new Ship("Destroyer", loc6);
            Ship s25 = new Ship("Patrol Boat", loc7);
            Ship s26 = new Ship("Submarine", loc8);
            Ship s27 = new Ship("Patrol Board", loc9);


            gp1.addShip(s1);
            gp1.addShip(s2);
            gp1.addShip(s3);

            gp2.addShip(s4);
            gp2.addShip(s5);


            gp3.addShip(s6);
            gp3.addShip(s7);

            gp4.addShip(s8);
            gp4.addShip(s9);


            gp5.addShip(s10);
            gp5.addShip(s11);

            gp6.addShip(s12);
            gp6.addShip(s13);


            gp7.addShip(s14);
            gp7.addShip(s15);

            gp8.addShip(s16);
            gp8.addShip(s17);


            gp9.addShip(s18);
            gp9.addShip(s19);

            gp10.addShip(s20);
            gp10.addShip(s21);


            gp11.addShip(s22);
            gp11.addShip(s23);


            gp13.addShip(s24);
            gp13.addShip(s25);
            gp14.addShip(s26);
            gp14.addShip(s27);


            gp1.addSalvo(sal1);
            gp1.addSalvo(sal2);

            gp2.addSalvo(sal3);
            gp2.addSalvo(sal4);

            gp3.addSalvo(sal5);
            gp3.addSalvo(sal6);

            gp4.addSalvo(sal7);
            gp4.addSalvo(sal8);

            gp5.addSalvo(sal9);
            gp5.addSalvo(sal10);

            gp6.addSalvo(sal11);
            gp6.addSalvo(sal12);

            gp7.addSalvo(sal13);
            gp7.addSalvo(sal14);

            gp8.addSalvo(sal15);
            gp8.addSalvo(sal16);

            gp9.addSalvo(sal17);
            gp9.addSalvo(sal18);

            gp10.addSalvo(sal19);
            gp10.addSalvo(sal20);


            game.save(g1);
            game.save(g2);
            game.save(g3);
            game.save(g4);
            game.save(g5);
            game.save(g6);
            game.save(g7);
            game.save(g8);


            gamePlayer.save(gp1);
            gamePlayer.save(gp2);
            gamePlayer.save(gp3);
            gamePlayer.save(gp4);
            gamePlayer.save(gp5);
            gamePlayer.save(gp6);
            gamePlayer.save(gp7);
            gamePlayer.save(gp8);
            gamePlayer.save(gp9);
            gamePlayer.save(gp10);
            gamePlayer.save(gp11);
            gamePlayer.save(gp12);
            gamePlayer.save(gp13);
            gamePlayer.save(gp14);






            ship.save(s1);
            ship.save(s2);
            ship.save(s3);
            ship.save(s4);
            ship.save(s5);
            ship.save(s6);
            ship.save(s7);
            ship.save(s8);
            ship.save(s9);
            ship.save(s10);
            ship.save(s11);
            ship.save(s12);
            ship.save(s13);
            ship.save(s14);
            ship.save(s15);
            ship.save(s16);
            ship.save(s17);
            ship.save(s18);
            ship.save(s19);
            ship.save(s20);
            ship.save(s21);
            ship.save(s22);
            ship.save(s23);
            ship.save(s24);
            ship.save(s25);
            ship.save(s26);
            ship.save(s27);

            Salvo.save(sal1);
            Salvo.save(sal2);
            Salvo.save(sal3);
            Salvo.save(sal4);
            Salvo.save(sal5);
            Salvo.save(sal6);
            Salvo.save(sal7);
            Salvo.save(sal8);
            Salvo.save(sal9);
            Salvo.save(sal10);
		};
	}
}