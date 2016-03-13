/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.logic;

/**
 *
 * @author Tiago
 */
public class GameStatus {
    
    private Board board;
    private boolean gameOver;
    
    public GameStatus() {
        gameOver = false;
    }
    
    public void initializeBoard() {
        board = new Board();
    }
    
    public void initializeBoard(char[][] maze) {
        board = new Board(maze);
    }
    
    public void setGameMode(GameMode mode) {
        board.setGameMode(mode);
    }
    
    public boolean gameOver() {
        return gameOver;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public String toString(Object object) {
        return board.toString();
    }
    
    public void updateGameStatus(String direction) {
        boolean heroOnExit = false;
        
        board.updateBoard(direction);
        
        if(board.getHero().getPoint().equals(board.getExit().getPoint()))
            heroOnExit = true;
        
        if(board.getHero().isDead() || 
                (board.getDragon().isDead() && heroOnExit)) gameOver = true;
    }
}
