/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.logic;

import java.util.Random;
import java.util.Stack;
import static maze.logic.Direction.*;
import static maze.logic.GameMode.*;

/**
 *
 * @author Tiago
 */
public class Board {
    
    private Dragon dragon;
    private Sword sword;
    private Hero hero;
    private Exit exit;
    private GameMode gameMode;
    private final Random sleepingRandom = new Random();
    private final Random moveRandom = new Random();
    private char[][] board = {{'X','X','X','X','X','X','X','X','X','X'},
                              {'X','H',' ',' ',' ',' ',' ',' ',' ','X'},
                              {'X',' ','X','X',' ','X',' ','X',' ', 'X'},
                              {'X','D','X','X',' ','X',' ','X',' ','X'},
                              {'X',' ','X','X',' ','X',' ','X',' ','X'},
                              {'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
                              {'X',' ','X','X',' ','X',' ','X',' ','X'},
                              {'X',' ','X','X',' ','X',' ','X',' ','X'},
                              {'X','E','X','X',' ',' ',' ',' ',' ','X'},
                              {'X','X','X','X','X','X','X','X','X','X'}};
    
    public Board() {
        dragon = new Dragon(1,3);
        sword = new Sword(1,8);
        hero = new Hero(1,1);
    }
    
    public Board(char[][] maze) {
        board = maze;
        
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[i].length; j++) {
                
                if(maze[i][j] == 'H') hero = new Hero(j,i);
                if(maze[i][j] == 'D') dragon = new Dragon(j,i);
                if(maze[i][j] == 'E') sword = new Sword(j,i);
                if(maze[i][j] == 'S') exit = new Exit(j,i);
            }
        }
    }
    
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    
    public Hero getHero() {
        return hero;
    }
    
    public Dragon getDragon() {
        return dragon;
    }
    
    public Exit getExit() {
        return exit;
    }
    
    @Override
    public String toString() {
        String str = "";
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++)
                str += board[i][j] + " ";
            str += "\n";
        }
        
        return str;
    }
    
    public void updateBoard(String direction) {
        
        switch(gameMode) {
            case STILL:
                updateBoardStill(direction);
                break;
            case MOVING:
                updateBoardMoving(direction);
                break;
            case MOVINGANDSLEEPING:
                updateBoardMovingAndSleeping(direction);
                break;
            default:
                break;
        }
            
    }
    
    
    private void updateBoardStill(String direction) {
        
        
        board[hero.getPoint().getY()][hero.getPoint().getX()] = ' ';

        if(direction.equals("w") && isPossibleToMove(UP, hero)) {
            hero.updatePosition(direction);
        }
        if(direction.equals("s") && isPossibleToMove(DOWN, hero)) {
            hero.updatePosition(direction);

        }
        if(direction.equals("d") && isPossibleToMove(RIGHT, hero)) {
            hero.updatePosition(direction);

        }
        if(direction.equals("a") && isPossibleToMove(LEFT, hero)) {
            hero.updatePosition(direction);
        }        
        
        if(hero.getPoint().getX() == sword.getPoint().getX() && hero.getPoint().getY() == sword.getPoint().getY())
            hero.setHasSword();            

        
        if(dragonNear()) {
            if(hero.hasSword()) {
                dragon.kill();
                board[dragon.getPoint().getY()][dragon.getPoint().getX()] = ' ';
            } else {
                hero.kill();
            }
        }
        
        if(!hero.isDead()) {
            if(hero.hasSword())
                board[hero.getPoint().getY()][hero.getPoint().getX()] = 'A';
            else
                board[hero.getPoint().getY()][hero.getPoint().getX()] = 'H';
        }
    }
    
    private void updateBoardMoving(String direction) {
        Direction dragonMove;

        board[hero.getPoint().getY()][hero.getPoint().getX()] = ' ';
        board[dragon.getPoint().getY()][dragon.getPoint().getX()] = ' ';

        
        if(direction.equals("w") && isPossibleToMove(UP, hero)) {
            hero.updatePosition(direction);
        }
        if(direction.equals("s") && isPossibleToMove(DOWN, hero)) {
            hero.updatePosition(direction);

        }
        if(direction.equals("d") && isPossibleToMove(RIGHT, hero)) {
            hero.updatePosition(direction);

        }
        if(direction.equals("a") && isPossibleToMove(LEFT, hero)) {
            hero.updatePosition(direction);
        }        

        if(hero.getPoint().getX() == sword.getPoint().getX() && hero.getPoint().getY() == sword.getPoint().getY())
            hero.setHasSword();
        
        
        if(hero.hasSword())
            board[hero.getPoint().getY()][hero.getPoint().getX()] = 'A';
        else
            board[hero.getPoint().getY()][hero.getPoint().getX()] = 'H';
        
        dragonMove = randomDragonMove();
        
        if(!dragon.isDead()) {
                        
            dragon.updatePosition(dragonMove);
            
            if (dragonNear()) {
                if (hero.hasSword()) {
                    dragon.kill();
                    board[dragon.getPoint().getY()][dragon.getPoint().getX()] = ' ';
                } else {
                    hero.kill();
                }
            }
        }
        
        if(hero.isDead()) 
            board[hero.getPoint().getY()][hero.getPoint().getX()] = ' ';
        
        if(!dragon.isDead()) {
            
            if(dragon.getPoint().getX() == sword.getPoint().getX() &&
                    dragon.getPoint().getY() == sword.getPoint().getY())
                board[dragon.getPoint().getY()][dragon.getPoint().getX()] = 'F';
            else {
                board[dragon.getPoint().getY()][dragon.getPoint().getX()] = 'D';
                if(!hero.hasSword()) board[sword.getPoint().getY()][sword.getPoint().getX()] = 'E';
            }
        }
    }
    
    private void updateBoardMovingAndSleeping(String direction) {
        int sleeping = sleepingRandom.nextInt(8);
        Direction dragonMove;
        
        board[hero.getPoint().getY()][hero.getPoint().getX()] = ' ';
        board[dragon.getPoint().getY()][dragon.getPoint().getX()] = ' ';

        
        if(direction.equals("w") && isPossibleToMove(UP, hero)) {
            hero.updatePosition(direction);
        }
        if(direction.equals("s") && isPossibleToMove(DOWN, hero)) {
            hero.updatePosition(direction);

        }
        if(direction.equals("d") && isPossibleToMove(RIGHT, hero)) {
            hero.updatePosition(direction);

        }
        if(direction.equals("a") && isPossibleToMove(LEFT, hero)) {
            hero.updatePosition(direction);
        }        
        
        if(!dragon.isDead() && sleeping == 0 && !dragon.isSleeping()) {
            board[dragon.getPoint().getY()][dragon.getPoint().getX()] = 'd';
            dragon.setSleeping();
        }
        
        if(hero.getPoint().getX() == sword.getPoint().getX() && hero.getPoint().getY() == sword.getPoint().getY())
            hero.setHasSword();    
        
        if(hero.hasSword())
            board[hero.getPoint().getY()][hero.getPoint().getX()] = 'A';
        else
            board[hero.getPoint().getY()][hero.getPoint().getX()] = 'H';
        
        dragonMove = randomDragonMove();
        
        if(!dragon.isDead() && !dragon.isSleeping()) {
            dragon.updatePosition(dragonMove);
        }

        if(dragonNear()) {
            if(hero.hasSword()) {
                dragon.kill();
                board[dragon.getPoint().getY()][dragon.getPoint().getX()] = ' ';
            }
            else if(!dragon.isSleeping())
                hero.kill();
        }
        
        if(hero.isDead())
            board[hero.getPoint().getY()][hero.getPoint().getX()] = ' ';
        
        if(!dragon.isDead()) {
            if(!dragon.isSleeping())
                board[dragon.getPoint().getY()][dragon.getPoint().getX()] = 'D';
            else
                board[dragon.getPoint().getY()][dragon.getPoint().getX()] = 'd';
        }
        
        if(!dragon.isDead() && sleeping != 0 && dragon.isSleeping()){
            board[dragon.getPoint().getY()][dragon.getPoint().getX()] = 'D';
            dragon.setNotSleeping();
        }
    }
    
    private boolean isPossibleToMove(Direction direction, Actor actor) {
        
        switch(direction) {
            case UP:
                return (board[actor.getPoint().getY() - 1][actor.getPoint().getX()] == ' ' ||
                            board[actor.getPoint().getY() - 1][actor.getPoint().getX()] == 'S' ||
                            board[actor.getPoint().getY() - 1][actor.getPoint().getX()] == 'E');
            case DOWN:
                return (board[actor.getPoint().getY() + 1][actor.getPoint().getX()] == ' ' ||
                            board[actor.getPoint().getY() + 1][actor.getPoint().getX()] == 'S' ||
                            board[actor.getPoint().getY() + 1][actor.getPoint().getX()] == 'E');
            case LEFT:
                return (board[actor.getPoint().getY()][actor.getPoint().getX() - 1] == ' ' ||
                            board[actor.getPoint().getY()][actor.getPoint().getX() - 1] == 'S' ||
                            board[actor.getPoint().getY()][actor.getPoint().getX() - 1] == 'E');
            case RIGHT:
                return (board[actor.getPoint().getY()][actor.getPoint().getX() + 1] == ' ' ||
                            board[actor.getPoint().getY()][actor.getPoint().getX() + 1] == 'S' ||
                            board[actor.getPoint().getY()][actor.getPoint().getX() + 1] == 'E');
            default:
                return false;
        }
    }
    
    private Direction randomDragonMove() {
        Stack<Direction> possibleDragonMoves = new Stack<>();
        Random random = new Random();
        
        if(isPossibleToMove(UP, dragon))
            possibleDragonMoves.push(UP);
        
        if(isPossibleToMove(DOWN, dragon))
            possibleDragonMoves.push(DOWN);
        
        if(isPossibleToMove(LEFT, dragon))
            possibleDragonMoves.push(LEFT);
        
        if(isPossibleToMove(RIGHT, dragon))
            possibleDragonMoves.push(RIGHT);
        
        possibleDragonMoves.push(STAY);
        
        int randomMove = random.nextInt(possibleDragonMoves.size());
        
        for(int i = 0; i < randomMove; i++)
            possibleDragonMoves.pop();
        
        return possibleDragonMoves.lastElement();
        
    }
    
    public boolean dragonNear() {
        
        if(dragon.getPoint().getX() == hero.getPoint().getX())
            if(dragon.getPoint().getY() >= (hero.getPoint().getY() - 1) && dragon.getPoint().getY() <= (hero.getPoint().getY() + 1))
                return true;
        
        if(dragon.getPoint().getY() == hero.getPoint().getY())
            if(dragon.getPoint().getX() >= (hero.getPoint().getX() - 1) && dragon.getPoint().getX() <= (hero.getPoint().getX() + 1))
                return true;
        
        return false;
    }
    
}
