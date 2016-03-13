package maze.test;

import static org.junit.Assert.*;

import org.junit.Test;
import maze.logic.*;

public class TestMazeWithSleepingDragon {

    char[][] m1 = {{'X', 'X', 'X', 'X', 'X'},
    {'X', ' ', ' ', 'H', 'S'},
    {'X', ' ', 'X', ' ', 'X'},
    {'X', 'E', ' ', 'D', 'X'},
    {'X', 'X', 'X', 'X', 'X'}};

    // a)
    @Test
    public void testMoveHeroToFreeCell() {

        GameStatus game = new GameStatus();
        GameMode mode = GameMode.MOVINGANDSLEEPING;

        game.initializeBoard(m1);
        game.getBoard().setGameMode(mode);

        game.updateGameStatus("a");
        assertEquals(new Point(2, 1), game.getBoard().getHero().getPoint());
    }

    // b)
    @Test
    public void testMoveHeroToWall() {
        GameStatus game = new GameStatus();
        GameMode mode = GameMode.MOVINGANDSLEEPING;

        game.initializeBoard(m1);
        game.getBoard().setGameMode(mode);

        game.updateGameStatus("w");

        if (game.getBoard().getDragon().getPoint().equals(new Point(3, 2))) {
            assertEquals(true, game.getBoard().getHero().isDead());
            assertEquals(true, game.gameOver());
        } else {
            assertEquals(game.getBoard().getHero().getPoint(), new Point(3, 1));
            assertEquals(false, game.gameOver());
        }
    }

    // c)
    @Test
    public void testHeroArmed() {
        GameStatus game = new GameStatus();
        GameMode mode = GameMode.MOVINGANDSLEEPING;

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
        GameMode mode = GameMode.MOVINGANDSLEEPING;

        game.initializeBoard(m1);
        game.getBoard().setGameMode(mode);

        game.updateGameStatus("s");

        if (game.getBoard().getDragon().getPoint().equals(new Point(3, 3))) {
            if (game.getBoard().getDragon().isSleeping()) {
                assertEquals(false, game.getBoard().getHero().isDead());
                assertEquals(false, game.gameOver());
            } else {
                assertEquals(true, game.getBoard().getHero().isDead());
                assertEquals(true, game.gameOver());
            }
        } else {
            assertEquals(false, game.getBoard().getHero().isDead());
            assertEquals(false, game.gameOver());
        }
    }

    // e)
    @Test
    public void testDragonDead() {
        GameStatus game = new GameStatus();
        GameMode mode = GameMode.MOVINGANDSLEEPING;

        game.initializeBoard(m1);
        game.getBoard().setGameMode(mode);

        game.getBoard().getHero().setHasSword();
        game.updateGameStatus("s");

        if (game.getBoard().getDragon().getPoint().equals(new Point(3, 3))) {
            assertEquals(true, game.getBoard().getDragon().isDead());
            assertEquals(false, game.gameOver());
        } else {
            assertEquals(false, game.getBoard().getDragon().isDead());
            assertEquals(false, game.gameOver());
        }
    }

    // f)
    @Test
    public void testHeroWinsGame() {
        GameStatus game = new GameStatus();
        GameMode mode = GameMode.MOVINGANDSLEEPING;

        game.initializeBoard(m1);
        game.getBoard().setGameMode(mode);

        game.getBoard().getHero().getPoint().setX(1);
        game.getBoard().getHero().getPoint().setY(2);
        game.updateGameStatus("s");

        if (game.getBoard().getDragon().getPoint().equals(new Point(2, 3))) {
            assertEquals(true, game.getBoard().getDragon().isDead());

            game.getBoard().getHero().getPoint().setX(3);
            game.getBoard().getHero().getPoint().setY(1);
            game.updateGameStatus("d");
            assertEquals(true, game.gameOver());

        } else {
            assertEquals(false, game.getBoard().getDragon().isDead());

            game.getBoard().getDragon().getPoint().setX(2);
            game.getBoard().getDragon().getPoint().setY(3);
            game.updateGameStatus("s");

            if (game.getBoard().getDragon().isSleeping()) {
                assertEquals(true, game.getBoard().getDragon().isDead());
                game.getBoard().getHero().getPoint().setX(3);
                game.getBoard().getHero().getPoint().setY(1);
                game.updateGameStatus("d");

                assertEquals(true, game.gameOver());
            } else {
                if(game.getBoard().getDragon().getPoint().equals(new Point(2, 3))) {
                    assertEquals(true, game.getBoard().getDragon().isDead());
                    game.getBoard().getHero().getPoint().setX(3);
                    game.getBoard().getHero().getPoint().setY(1);
                    game.updateGameStatus("d");

                    assertEquals(true, game.gameOver());
                } else {
                    game.getBoard().getHero().getPoint().setX(3);
                    game.getBoard().getHero().getPoint().setY(1);
                    game.updateGameStatus("d");

                    assertEquals(false, game.gameOver());
                }
            }
        }
    }

    // g)
    @Test

    public void testHeroExitWithoutSword() {
        GameStatus game = new GameStatus();
        GameMode mode = GameMode.MOVING;

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
        GameMode mode = GameMode.MOVING;

        game.initializeBoard(m1);
        game.getBoard().setGameMode(mode);

        game.getBoard().getHero().getPoint().setX(1);
        game.getBoard().getHero().getPoint().setY(2);
        game.updateGameStatus("s");

        if (game.getBoard().getDragon().getPoint().equals(new Point(2, 3))) {
            assertEquals(true, game.getBoard().getDragon().isDead());

            game.getBoard().getHero().getPoint().setX(3);
            game.getBoard().getHero().getPoint().setY(1);
            game.updateGameStatus("d");

            assertEquals(true, game.gameOver());
        } else {
            game.getBoard().getHero().getPoint().setX(3);
            game.getBoard().getHero().getPoint().setY(1);
            game.updateGameStatus("d");

            if (game.getBoard().getDragon().isDead()) {
                assertEquals(true, game.gameOver());
            } else {
                assertEquals(false, game.gameOver());
            }
        }
    }
}
