package maze.test;

import static org.junit.Assert.*;

import org.junit.Test;
import maze.logic.*;

public class TestMazeWithStaticDragon {
	char [][] m1 = {{'X', 'X', 'X', 'X', 'X'},
                        {'X', ' ', ' ', 'H', 'S'},
                        {'X', ' ', 'X', ' ', 'X'},
                        {'X', 'E', ' ', 'D', 'X'},
                        {'X', 'X', 'X', 'X', 'X'}};

	// a)
	@Test
	public void testMoveHeroToFreeCell() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;

                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);

		game.updateGameStatus("a");
		assertEquals(new Point(2, 1),game.getBoard().getHero().getPoint());
	}

	// b)
	@Test
	public void testMoveHeroToWall() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;
                
                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);
                
		game.updateGameStatus("w");
		assertEquals(new Point(3, 1),game.getBoard().getHero().getPoint());
	}

	// c)

	@Test
	public void testHeroArmed() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;

                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);
                
		game.getBoard().getHero().getPoint().setX(1);
		game.getBoard().getHero().getPoint().setY(2);
                game.updateGameStatus("s");

		assertEquals(true, game.getBoard().getHero().hasSword());
	}

	// d)

	@Test
	public void testHeroDead() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;
                
                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);

		game.updateGameStatus("s");
		assertEquals(true, game.getBoard().getHero().isDead());
		assertEquals(true, game.gameOver());
	}

	// e)

	@Test
	public void testDragonDead() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;
                
                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);

		game.getBoard().getHero().setHasSword();
		game.updateGameStatus("s");

		assertEquals(true, game.getBoard().getDragon().isDead());
	}

	// f)

	@Test
	public void testHeroWinsGame() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;
                
                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);

		game.getBoard().getHero().getPoint().setX(1);
		game.getBoard().getHero().getPoint().setY(2);
		game.updateGameStatus("s");
		game.updateGameStatus("d");
		game.getBoard().getHero().getPoint().setX(3);
		game.getBoard().getHero().getPoint().setY(1);
		game.updateGameStatus("d");

		assertEquals(true, game.gameOver());
	}

	// g)
	@Test

	public void testHeroExitWithoutSword() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;
                
                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);

		game.getBoard().getHero().getPoint().setX(3);
		game.getBoard().getHero().getPoint().setY(1);
		game.updateGameStatus("d");
                
		assertEquals(false, game.gameOver());
	}

	// h)
	@Test

	public void testHeroExitWithSword() {
		GameStatus game = new GameStatus();
		GameMode mode = GameMode.STILL;
                
                game.initializeBoard(m1);
                game.getBoard().setGameMode(mode);

		game.getBoard().getHero().getPoint().setX(1);
		game.getBoard().getHero().getPoint().setY(2);
		game.updateGameStatus("s");
		game.getBoard().getHero().getPoint().setX(3);
		game.getBoard().getHero().getPoint().setY(1);
		game.updateGameStatus("d");
                
		assertEquals(false, game.gameOver());
	}
	  
}
